package chapter3.section2.a.precalculateable;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 09/09/21.
 */
public class TwoThreeFourDSqrRectsCubesBoxes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String lengthLine = FastReader.getLine();

        long[] squareNumbers = computeSquareNumbers();
        long[] rectangleNumbers = computeRectangleNumbers();
        long[] cubeNumbers = computeCubeNumbers();
        BigInteger[] hypercubeNumbers = computeHypercubeNumbers();
        long[] boxNumbers = computeBoxNumbers();
        BigInteger[] hyperboxNumbers = computeHyperboxNumbers();

        while (lengthLine != null) {
            int length = Integer.parseInt(lengthLine);
            outputWriter.printLine(String.format("%d %d %d %s %d %s", squareNumbers[length],
                    rectangleNumbers[length], cubeNumbers[length], hypercubeNumbers[length],
                    boxNumbers[length], hyperboxNumbers[length]));

            lengthLine = FastReader.getLine();
        }
        outputWriter.flush();
    }

    // Based on https://en.wikipedia.org/wiki/Square_pyramidal_number
    // Square pyramidal number
    private static long[] computeSquareNumbers() {
        long[] squareNumbers = new long[101];

        for (int i = 1; i <= 100; i++) {
            squareNumbers[i] = (i * (i + 1) * (2 * i + 1)) / 6;
        }
        return squareNumbers;
    }

    // Based on https://oeis.org/A052149
    private static long[] computeRectangleNumbers() {
        long[] rectangleNumbers = new long[101];

        for (int i = 1; i <= 100; i++) {
            rectangleNumbers[i] = (i * (i - 1) * (i + 1) * (3 * i + 2)) / 12;
        }
        return rectangleNumbers;
    }

    // Based on https://www.quora.com/What-is-the-next-term-in-the-sequence-1-9-36-100-225
    private static long[] computeCubeNumbers() {
        long[] cubeNumbers = new long[101];
        long base = 1;
        int increment = 2;

        for (int i = 1; i <= 100; i++) {
            cubeNumbers[i] = base * base;
            base += increment;
            increment++;
        }
        return cubeNumbers;
    }

    // Based on https://oeis.org/A071910
    private static BigInteger[] computeHypercubeNumbers() {
        BigInteger[] hypercubeNumbers = new BigInteger[101];
        hypercubeNumbers[0] = BigInteger.ZERO;

        for (int i = 1; i <= 100; i++) {
            hypercubeNumbers[i] = getTriangleNumber(i - 1)
                    .multiply(getTriangleNumber(i))
                    .multiply(getTriangleNumber(i + 1));
        }
        return hypercubeNumbers;
    }

    private static BigInteger getTriangleNumber(long number) {
        return BigInteger.valueOf(number + 1)
                .multiply(BigInteger.valueOf(number))
                .divide(BigInteger.valueOf(2));
    }

    // Based on https://www.geeksforgeeks.org/program-find-n-th-term-series-11798354/
    // Sum of the 4th power of the first n natural numbers
    private static long[] computeBoxNumbers() {
        long[] boxes = new long[101];
        long sum = 0;

        for (int i = 1; i <= 100; i++) {
            sum += (i * i * i * i);
            boxes[i] = sum;
        }
        return boxes;
    }

    private static BigInteger[] computeHyperboxNumbers() {
        BigInteger[] hyperboxNumbers = new BigInteger[101];
        hyperboxNumbers[0] = BigInteger.ZERO;

        for (int i = 1; i <= 100; i++) {
            BigInteger number = BigInteger.valueOf(i);
            BigInteger numberPlusOne = number.add(BigInteger.ONE);
            BigInteger numberTimes3 = BigInteger.valueOf(3).multiply(number);

            BigInteger firstTerm = number
                    .multiply(numberPlusOne)
                    .divide(BigInteger.valueOf(2))
                    .pow(4);
            BigInteger secondTerm = number
                    .multiply(numberPlusOne)
                    .multiply(BigInteger.valueOf(2).multiply(number).add(BigInteger.ONE));
            BigInteger thirdTerm = numberTimes3
                    .multiply(number)
                    .add(numberTimes3)
                    .subtract(BigInteger.ONE);
            hyperboxNumbers[i] = firstTerm.subtract(
                    secondTerm.multiply(thirdTerm)
                            .divide(BigInteger.valueOf(30)));
        }
        return hyperboxNumbers;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
