package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/11/21.
 */
public class MaximumProduct {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int caseNumber = 1;

        while (line != null) {
            int elementsNumber = Integer.parseInt(line);
            int[] elements = new int[elementsNumber];

            for (int i = 0; i < elements.length; i++) {
                elements[i] = FastReader.nextInt();
            }

            BigInteger highestProduct = getHighestProduct(elements);
            outputWriter.printLine(String.format("Case #%d: The maximum product is %s.",
                    caseNumber, highestProduct));
            outputWriter.printLine();

            caseNumber++;
            FastReader.getLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigInteger getHighestProduct(int[] elements) {
        BigInteger highestProduct = BigInteger.ZERO;
        BigInteger maxPositive = null;
        BigInteger maxNegative = null;

        for (int i = 0; i < elements.length; i++) {
            BigInteger element = BigInteger.valueOf(elements[i]);

            if (elements[i] > 0) {
                if (maxPositive == null) {
                    maxPositive = element;
                } else {
                    maxPositive = maxPositive.multiply(element);
                }
                if (maxPositive.compareTo(highestProduct) > 0) {
                    highestProduct = maxPositive;
                }

                if (maxNegative != null) {
                    maxNegative = maxNegative.multiply(element);
                }
            } else if (elements[i] < 0) {
                BigInteger previousMaxNegative = maxNegative;

                if (maxPositive != null) {
                    maxNegative = maxPositive.multiply(element);
                } else {
                    maxNegative = element;
                }

                if (previousMaxNegative != null) {
                    maxPositive = previousMaxNegative.multiply(element);
                    if (maxPositive.compareTo(highestProduct) > 0) {
                        highestProduct = maxPositive;
                    }
                } else {
                    maxPositive = null;
                }
            } else {
                maxNegative = null;
                maxPositive = null;
            }
        }
        return highestProduct;
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
