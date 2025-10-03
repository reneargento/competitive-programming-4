package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 30/09/25.
 */
public class PersistentNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String numberString = FastReader.getLine();
        while (!numberString.equals("-1")) {
            String parentNumber = computeParentNumber(numberString);
            outputWriter.printLine(parentNumber);
            numberString = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computeParentNumber(String numberString) {
        if (numberString.length() == 1) {
            return "1" + numberString;
        }

        BigInteger number = new BigInteger(numberString);
        StringBuilder parentNumber = new StringBuilder();

        BigInteger bigInteger7 = new BigInteger("7");
        BigInteger bigInteger5 = new BigInteger("5");
        BigInteger bigInteger3 = new BigInteger("3");
        BigInteger bigInteger2 = new BigInteger("2");

        int[] primeCounts = new int[4];
        BigInteger[] bigIntegers = { bigInteger7, bigInteger5, bigInteger3, bigInteger2 };

        for (int i = 0; i < primeCounts.length; i++) {
            while (number.mod(bigIntegers[i]).equals(BigInteger.ZERO)) {
                primeCounts[i]++;
                number = number.divide(bigIntegers[i]);
            }
        }
        if (number.compareTo(BigInteger.ONE) > 0) {
            return "There is no such number.";
        }

        addNumbers(parentNumber, primeCounts, 2, 2, "9");
        addNumbers(parentNumber, primeCounts, 3, 3, "8");
        addNumbers(parentNumber, primeCounts, 0, 1, "7");
        addNumbersCombined(parentNumber, primeCounts, 2, 3, 1, "6");
        addNumbers(parentNumber, primeCounts, 1, 1, "5");
        addNumbers(parentNumber, primeCounts, 3, 2, "4");
        addNumbers(parentNumber, primeCounts, 2, 1, "3");
        addNumbers(parentNumber, primeCounts, 3, 1, "2");
        return parentNumber.reverse().toString();
    }

    private static void addNumbers(StringBuilder parentNumber, int[] primeCounts, int index, int quantity,
                                   String number) {
        while (primeCounts[index] >= quantity) {
            parentNumber.append(number);
            primeCounts[index] -= quantity;
        }
    }

    private static void addNumbersCombined(StringBuilder parentNumber, int[] primeCounts, int index1, int index2,
                                           int quantity, String number) {
        while (primeCounts[index1] >= quantity
                && primeCounts[index2] >= quantity ) {
            parentNumber.append(number);
            primeCounts[index1] -= quantity;
            primeCounts[index2] -= quantity;
        }
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
