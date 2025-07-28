package chapter5.section3.e.modified.sieve;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/07/25.
 */
public class CountTheFactors {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();
        int[] uniquePFs = eratosthenesSieveCountUniquePFs();

        while (number != 0) {
            int uniquePFsCount = uniquePFs[number];
            outputWriter.printLine(String.format("%d : %d", number, uniquePFsCount));
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[] eratosthenesSieveCountUniquePFs() {
        int number = 1000000;
        int[] uniquePFs = new int[number + 1];

        for (int i = 2; i < uniquePFs.length; i++) {
            if (uniquePFs[i] == 0) {
                for (int j = i; j < uniquePFs.length; j += i) {
                    uniquePFs[j]++;
                }
            }
        }
        return uniquePFs;
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
