package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 21/08/22.
 */
public class MaximumSubSequenceProduct {

    private static final int END_SEQUENCE = -999999;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            List<BigInteger> values = new ArrayList<>();
            boolean sequenceEnded = false;

            for (String valueString : data) {
                int value = Integer.parseInt(valueString);
                if (value == END_SEQUENCE) {
                    sequenceEnded = true;
                    break;
                }
                values.add(BigInteger.valueOf(value));
            }

            if (!sequenceEnded) {
                int value = FastReader.nextInt();
                while (value != END_SEQUENCE) {
                    values.add(BigInteger.valueOf(value));
                    value = FastReader.nextInt();
                }
            }

            BigInteger maxProduct = computeMaxProduct(values);
            outputWriter.printLine(maxProduct);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigInteger computeMaxProduct(List<BigInteger> values) {
        BigInteger maxProduct = values.get(0);
        BigInteger currentProductPositive = BigInteger.ZERO;
        BigInteger currentProductNegative = BigInteger.ZERO;
        boolean positiveProductIsSet;
        boolean negativeProductIsSet = false;

        for (BigInteger value : values) {
            int compareResult = value.compareTo(BigInteger.ZERO);

            if (compareResult == 0) {
                positiveProductIsSet = true;
                negativeProductIsSet = true;
                currentProductPositive = BigInteger.ZERO;
                currentProductNegative = BigInteger.ZERO;
            } else {
                if (compareResult < 0) {
                    BigInteger previousProductPositive = currentProductPositive;
                    if (!currentProductNegative.equals(BigInteger.ZERO)) {
                        currentProductPositive = currentProductNegative.multiply(value);
                        positiveProductIsSet = true;
                    } else {
                        currentProductPositive = BigInteger.ZERO;
                        positiveProductIsSet = false;
                    }

                    if (!previousProductPositive.equals(BigInteger.ZERO)) {
                        currentProductNegative = previousProductPositive.multiply(value);
                    } else {
                        currentProductNegative = value;
                    }
                    negativeProductIsSet = true;
                } else {
                    if (!currentProductPositive.equals(BigInteger.ZERO)) {
                        currentProductPositive = currentProductPositive.multiply(value);
                    } else {
                        currentProductPositive = value;
                    }
                    positiveProductIsSet = true;

                    if (!currentProductNegative.equals(BigInteger.ZERO)) {
                        currentProductNegative = currentProductNegative.multiply(value);
                        negativeProductIsSet = true;
                    }
                }
            }

            if (positiveProductIsSet && currentProductPositive.compareTo(maxProduct) > 0) {
                maxProduct = currentProductPositive;
            }
            if (negativeProductIsSet && currentProductNegative.compareTo(maxProduct) > 0) {
                maxProduct = currentProductNegative;
            }
        }
        return maxProduct;
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
