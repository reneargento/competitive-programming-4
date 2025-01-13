package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 28/12/24.
 */
public class NumericCenter {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        BigInteger[] numericCenters = computeAllNumericCenters();
        while (!line.equals("0")) {
            BigInteger number = new BigInteger(line);
            int numericCentersUntil = computeNumericCentersUntil(number, numericCenters);
            outputWriter.printLine(numericCentersUntil);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    // Based on https://oeis.org/A001108
    private static BigInteger[] computeAllNumericCenters() {
        BigInteger[] numericCenters = new BigInteger[25];
        numericCenters[0] = BigInteger.valueOf(8);
        numericCenters[1] = BigInteger.valueOf(49);

        BigInteger maxNumber = new BigInteger("1000000000000000");
        BigInteger bigInteger6 = BigInteger.valueOf(6);
        BigInteger bigInteger2 = BigInteger.valueOf(2);
        BigInteger next = getNext(numericCenters[0], numericCenters[1], bigInteger6, bigInteger2);
        int index = 2;

        while (next.compareTo(maxNumber) <= 0) {
            numericCenters[index] = next;
            index++;
            next = getNext(numericCenters[index - 2], numericCenters[index - 1], bigInteger6, bigInteger2);
        }
        return numericCenters;
    }

    private static BigInteger getNext(BigInteger number1, BigInteger number2, BigInteger bigInteger6,
                                      BigInteger bigInteger2) {
        return bigInteger6.multiply(number2).subtract(number1).add(bigInteger2);
    }

    private static int computeNumericCentersUntil(BigInteger value, BigInteger[] numericCenters) {
        for (int i = 0; i < numericCenters.length; i++) {
            if (numericCenters[i].compareTo(value) > 0) {
                return i;
            }
        }
        return 0;
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

        public void flush() {
            writer.flush();
        }
    }
}
