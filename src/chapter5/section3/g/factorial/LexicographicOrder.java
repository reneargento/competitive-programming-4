package chapter5.section3.g.factorial;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/08/25.
 */
public class LexicographicOrder {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long[] factorials = computeFactorials();

        int tests = FastReader.nextInt();
        for (int i = 1; i <= tests; i++) {
            String alphabet = FastReader.next();
            long k = FastReader.nextLong();

            String originalOrder = computeOriginalOrder(factorials, alphabet, k);
            outputWriter.printLine(String.format("Case %d: %s", i, originalOrder));
        }
        outputWriter.flush();
    }

    private static String computeOriginalOrder(long[] factorials, String alphabet, long k) {
        char[] originalOrder = new char[alphabet.length()];
        Arrays.fill(originalOrder, '*');
        computeOriginalOrder(factorials, alphabet, originalOrder, k, 0);
        return new String(originalOrder);
    }

    private static void computeOriginalOrder(long[] factorials, String alphabet, char[] originalOrder, long k,
                                             int startIndex) {
        if (startIndex == alphabet.length()) {
            return;
        }
        int size = alphabet.length() - startIndex;
        long factorial = factorials[size];

        long sectionSize = factorial / size;
        int index = (int) ((k - 1) / sectionSize);
        int finalIndex = getFinalIndex(originalOrder, index);
        originalOrder[finalIndex] = alphabet.charAt(startIndex);

        long nextK = k % sectionSize;
        if (nextK == 0) {
            nextK = sectionSize;
        }
        computeOriginalOrder(factorials, alphabet, originalOrder, nextK, startIndex + 1);
    }

    private static int getFinalIndex(char[] originalOrder, int index) {
        int finalIndex = 0;
        int emptySlots = 0;

        for (int i = 0; i < originalOrder.length && emptySlots <= index; i++) {
            char symbol = originalOrder[i];
            if (symbol != '*') {
                finalIndex++;
            } else {
                emptySlots++;
            }
        }
        return finalIndex + index;
    }

    private static long[] computeFactorials() {
        long[] factorials = new long[21];
        factorials[0] = 1;
        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        return factorials;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
