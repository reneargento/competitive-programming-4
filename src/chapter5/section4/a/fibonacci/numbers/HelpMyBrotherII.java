package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/11/25.
 */
public class HelpMyBrotherII {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        BigInteger[] prefixNumberOfElementsSum = new BigInteger[1700];
        BigInteger[] fibonacci = computeFibonacci(prefixNumberOfElementsSum);

        int lineNumber = FastReader.nextInt();
        int setNumber = 1;
        while (lineNumber != 0) {
            outputWriter.printLine(String.format("Set %d:", setNumber));
            BigInteger median = getMedian(fibonacci, prefixNumberOfElementsSum, lineNumber);
            outputWriter.printLine(median.toString());

            setNumber++;
            lineNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static BigInteger getMedian(BigInteger[] fibonacci, BigInteger[] prefixNumberOfElementsSum, int lineNumber) {
        BigInteger minElement = prefixNumberOfElementsSum[lineNumber];
        BigInteger maxElement = minElement.add(fibonacci[lineNumber]).subtract(BigInteger.ONE);
        BigInteger bigInteger2 = new BigInteger("2");

        BigInteger numbersSum = minElement.add(maxElement);
        return numbersSum.divide(bigInteger2);
    }

    private static BigInteger[] computeFibonacci(BigInteger[] prefixNumberOfElementsSum) {
        BigInteger[] fibonacci = new BigInteger[prefixNumberOfElementsSum.length];
        fibonacci[1] = BigInteger.ONE;
        fibonacci[2] = BigInteger.ONE;
        prefixNumberOfElementsSum[1] = BigInteger.ZERO;
        prefixNumberOfElementsSum[2] = BigInteger.ONE;

        for (int i = 3; i < fibonacci.length; i++) {
            fibonacci[i] = fibonacci[i - 1].add(fibonacci[i - 2]);
            prefixNumberOfElementsSum[i] = prefixNumberOfElementsSum[i - 1].add(fibonacci[i - 1]);
        }
        return fibonacci;
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
