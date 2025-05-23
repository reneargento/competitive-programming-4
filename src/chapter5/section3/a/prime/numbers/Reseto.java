package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/05/25.
 */
public class Reseto {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int n = FastReader.nextInt();
        int k = FastReader.nextInt();

        long kThNumberCrossed = computeKThNumberCrossed(n, k);
        outputWriter.printLine(kThNumberCrossed);
        outputWriter.flush();
    }

    private static long computeKThNumberCrossed(int n, int k) {
        int numbersCrossed = 0;
        boolean[] isPrime = new boolean[n + 1];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i <= n; i++) {
            if (isPrime[(int) i]) {
                numbersCrossed++;
                if (numbersCrossed == k) {
                    return i;
                }

                for (long j = i * i; j < isPrime.length; j += i) {
                    if (isPrime[(int) j]) {
                        numbersCrossed++;
                        if (numbersCrossed == k) {
                            return j;
                        }
                        isPrime[(int) j] = false;
                    }
                }
            }
        }
        return -1;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
