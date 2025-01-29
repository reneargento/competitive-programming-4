package chapter5.section2.c.base.number.conversion;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 25/01/25.
 */
public class PalindromssmordnilaP {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String number = FastReader.getLine();
        while (number != null) {
            countTotalSteps(number, outputWriter);
            number = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void countTotalSteps(String number, OutputWriter outputWriter) {
        for (int base = 15; base >= 2; base--) {
            String steps = countSteps(number, base);
            if (base != 15) {
                outputWriter.print(" ");
            }
            outputWriter.print(steps);
        }
        outputWriter.printLine();
    }

    private static String countSteps(String number, int base) {
        int steps = 0;
        try {
            new BigInteger(number, base);
        } catch (NumberFormatException exception) {
            return "?";
        }

        while (!isPalindrome(number)) {
            steps++;
            String reverseNumber = getReverseNumber(number);

            BigInteger numberInBase;
            try {
                numberInBase = new BigInteger(number, base);
            } catch (NumberFormatException exception) {
                return "?";
            }
            BigInteger reverseNumberInBase = new BigInteger(reverseNumber, base);
            BigInteger sum = numberInBase.add(reverseNumberInBase);
            number = sum.toString(base);
        }
        return String.valueOf(steps);
    }

    private static boolean isPalindrome(String number) {
        for (int i = 0; i < number.length() / 2; i++) {
            int reverseIndex = number.length() - 1 - i;
            if (number.charAt(i) != number.charAt(reverseIndex)) {
                return false;
            }
        }
        return true;
    }

    private static String getReverseNumber(String number) {
        StringBuilder stringBuilder = new StringBuilder(number);
        return stringBuilder.reverse().toString();
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