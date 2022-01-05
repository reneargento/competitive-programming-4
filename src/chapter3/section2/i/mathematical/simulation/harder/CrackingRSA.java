package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/01/22.
 */
public class CrackingRSA {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            long n = FastReader.nextInt();
            long e = FastReader.nextInt();
            long d = computeD(n, e);
            outputWriter.printLine(d);
        }
        outputWriter.flush();
    }

    private static long computeD(long n, long e) {
        List<Long> primeFactors = primeFactors(n);
        long p = primeFactors.get(0);
        long q = primeFactors.get(1);
        long eulerTotient = (p - 1) * (q - 1);

        for (int d = 2; d <= eulerTotient; d++) {
            long divisor = d * e - 1;

            if (divisor % eulerTotient == 0) {
                return d;
            }
        }
        return -1;
    }

    private static List<Long> primeFactors(long number) {
        List<Long> primeFactors = new ArrayList<>();

        for (long i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                primeFactors.add(i);
                number /= i;
                break;
            }
        }
        primeFactors.add(number);
        return primeFactors;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
