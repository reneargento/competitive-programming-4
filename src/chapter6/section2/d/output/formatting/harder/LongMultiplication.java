package chapter6.section2.d.output.formatting.harder;

import java.io.*;

/**
 * Created by Rene Argento on 21/06/2026.
 */
public class LongMultiplication {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("0")) {
            String[] data = line.trim().split("\\s+");
            multiply(data[0], data[1], outputWriter);
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void multiply(String number1, String number2, OutputWriter outputWriter) {
        String[] intermediateResults = multiplyDigits(number1, number2);

        if (shouldSkipBottomSection(number2, intermediateResults)) {
            int lastIntermediateIndex = intermediateResults.length - 1;
            int totalLength = getTotalLength(number1, number2, intermediateResults[lastIntermediateIndex]);
            String format = "%" + totalLength + "s";
            outputWriter.printLine(String.format(format, number1));
            outputWriter.printLine(String.format(format, number2));

            int hyphensLine1Length = Math.max(number1.length(), number2.length());
            printHyphens(hyphensLine1Length, totalLength, outputWriter);
            String formattedValue = getFormattedValue(intermediateResults[lastIntermediateIndex]);
            outputWriter.printLine(String.format(format, formattedValue));
            return;
        }
        sumResults(number1, number2, intermediateResults, outputWriter);
    }

    private static String[] multiplyDigits(String number1, String number2) {
        String[] intermediateResults = new String[number2.length()];
        int intermediateIndex = 0;

        for (int i = number2.length() - 1; i >= 0; i--) {
            int value1 = Character.getNumericValue(number2.charAt(i));
            int carry = 0;
            StringBuilder result = new StringBuilder();
            for (int z = 0; z < intermediateIndex; z++) {
                result.append(" ");
            }

            for (int j = number1.length() - 1; j >= 0; j--) {
                int value2 = Character.getNumericValue(number1.charAt(j));
                int nextValue = value1 * value2 + carry;
                int digit = nextValue % 10;
                carry = nextValue / 10;

                result.append(digit);
            }
            if (carry != 0) {
                result.append(carry);
            }
            intermediateResults[intermediateIndex++] = result.reverse().toString();
        }
        return intermediateResults;
    }

    private static void sumResults(String number1, String number2, String[] intermediateResults,
                                   OutputWriter outputWriter) {
        StringBuilder sumResult = new StringBuilder();
        int lastIndex = intermediateResults.length - 1;
        int carry = 0;
        for (int i = 0; i < intermediateResults[lastIndex].length(); i++) {
            int digitsSum = 0;

            for (String intermediateResult : intermediateResults) {
                int index = intermediateResult.length() - 1 - i;
                if (index >= 0) {
                    char digitValue = intermediateResult.charAt(index);
                    if (digitValue != ' ') {
                        digitsSum += Character.getNumericValue(digitValue);
                    }
                }
            }

            int nextValue = digitsSum + carry;
            int digit = nextValue % 10;
            carry = nextValue / 10;
            sumResult.append(digit);
        }
        if (carry != 0) {
            sumResult.append(carry);
        }
        sumResult.reverse();

        int totalLength = getTotalLength(number1, number2, sumResult.toString());
        String format = "%" + totalLength + "s";
        outputWriter.printLine(String.format(format, number1));
        outputWriter.printLine(String.format(format, number2));

        int hyphensLine1Length = Math.max(number1.length(), number2.length());
        printHyphens(hyphensLine1Length, totalLength, outputWriter);
        for (String intermediateResult : intermediateResults) {
            if (isNonZeroNumber(intermediateResult)) {
                int lengthWithoutSpaces = intermediateResult.trim().length();
                int intermediateLength = totalLength - (intermediateResult.length() - lengthWithoutSpaces);
                String intermediateFormat = "%" + intermediateLength + "s";
                outputWriter.printLine(String.format(intermediateFormat, intermediateResult.trim()));
            }
        }
        printHyphens(sumResult.length(), totalLength, outputWriter);
        outputWriter.printLine(sumResult.toString());
    }

    private static void printHyphens(int length, int totalLength, OutputWriter outputWriter) {
        StringBuilder hyphens = new StringBuilder();
        String format = "%" + totalLength + "s";

        for (int i = 0; i < length; i++) {
            hyphens.append("-");
        }
        outputWriter.printLine(String.format(format, hyphens));
    }

    private static int getTotalLength(String number1, String number2, String result) {
        int totalLength = Math.max(number1.length(), number2.length());
        return Math.max(totalLength, result.length());
    }

    private static boolean shouldSkipBottomSection(String number2, String[] intermediateResults) {
        int valuesFound = 0;
        for (String intermediateResult : intermediateResults) {
            if (isNonZeroNumber(intermediateResult)) {
                valuesFound++;
            }
        }
        return number2.length() == 1 || valuesFound <= 1;
    }

    private static boolean isNonZeroNumber(String number) {
        for (char character : number.toCharArray()) {
            if (character != ' ' && character != '0') {
                return true;
            }
        }
        return false;
    }

    private static String getFormattedValue(String number) {
        number = number.replace(' ', '0');
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) != '0') {
                return number.substring(i);
            }
        }
        return "0";
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