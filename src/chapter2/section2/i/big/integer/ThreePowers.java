package chapter2.section2.i.big.integer;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rene Argento on 15/03/21.
 */
public class ThreePowers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        BigInteger[] powersOf3 = computePowers(3);
        BigInteger[] powersOf2 = computePowers(2);
        String numberLine = FastReader.getLine();

        while (!numberLine.equals("0")) {
            BigDecimal number = new BigDecimal(numberLine);
            if (number.compareTo(BigDecimal.ONE) == 0) {
                outputWriter.printLine("{ }");
            } else {
                printSequence(number, powersOf3, powersOf2, outputWriter);
            }

            numberLine = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printSequence(BigDecimal number, BigInteger[] powersOf3, BigInteger[] powersOf2,
                                      OutputWriter outputWriter) {
        LinkedList<String> numberList = new LinkedList<>();
        BigInteger bigInteger2 = BigInteger.valueOf(2);

        int power = logBase2(number) - 1;
        BigInteger value = powersOf3[power];

        BigInteger numberInteger = number.toBigInteger();
        numberList.addFirst(value.toString());

        BigInteger start = powersOf2[power].add(BigInteger.ONE);
        BigInteger end = powersOf2[power + 1];

        while (start.compareTo(end) <= 0) {
            power--;
            // middle = start + (end - start) / 2
            BigInteger middle = start.add(end.subtract(start).divide(bigInteger2));

            if (numberInteger.compareTo(middle) > 0) {
                BigInteger nextValue = powersOf3[power];
                numberList.addFirst(nextValue.toString());
                start = middle.add(BigInteger.ONE);
            } else {
                end = middle.subtract(BigInteger.ONE);
            }
        }
        printSequence(numberList, outputWriter);
    }

    private static void printSequence(List<String> numberList, OutputWriter outputWriter) {
        outputWriter.print("{ ");

        for (int i = 0; i < numberList.size(); i++) {
            outputWriter.print(numberList.get(i));

            if (i != numberList.size() - 1) {
                outputWriter.print(", ");
            }
        }
        outputWriter.printLine(" }");
    }

    private static int logBase2(BigDecimal number) {
        int log = 0;

        while (number.compareTo(BigDecimal.ONE) > 0) {
            log++;
            number = number.divide(BigDecimal.valueOf(2), RoundingMode.CEILING);
        }
        return log;
    }

    private static BigInteger[] computePowers(int value) {
        BigInteger[] powers = new BigInteger[65];
        powers[0] = BigInteger.ONE;
        BigInteger bigIntegerMultiplier = BigInteger.valueOf(value);

        for (int i = 1; i < powers.length; i++) {
            powers[i] = powers[i - 1].multiply(bigIntegerMultiplier);
        }
        return powers;
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
