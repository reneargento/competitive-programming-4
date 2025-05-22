package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/05/25.
 */
public class EnlargingHashTables {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tableSize = FastReader.nextInt();

        while (tableSize != 0) {
            boolean isNotPrime = !isPrime(tableSize);
            int nextTableSize = computeNextTableSize(tableSize);

            outputWriter.print(nextTableSize);
            if (isNotPrime) {
                outputWriter.print(String.format(" (%d is not prime)", tableSize));
            }
            outputWriter.printLine();
            tableSize = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean isPrime(int number) {
        int maxValue = (int) Math.ceil(Math.sqrt(number));
        for (int i = 2; i <= maxValue; i++) {
            if (i != number && number % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static int computeNextTableSize(int tableSize) {
        int currentSize = tableSize * 2 + 1;

        while (true) {
            if (isPrime(currentSize)) {
                return currentSize;
            }
            currentSize++;
        }
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
