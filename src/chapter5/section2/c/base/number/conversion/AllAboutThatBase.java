package chapter5.section2.c.base.number.conversion;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/01/25.
 */
public class AllAboutThatBase {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String number1 = FastReader.next();
            char operator = FastReader.next().charAt(0);
            String number2 = FastReader.next();
            FastReader.next(); // Ignore equals sign
            String result = FastReader.next();

            String validBases = getValidBases(number1, operator, number2, result);
            outputWriter.printLine(validBases);
        }
        outputWriter.flush();
    }

    private static String getValidBases(String number1, char operator, String number2, String result) {
        StringBuilder validBases = new StringBuilder();
        if (isValidInBase1(number1, operator, number2, result)) {
            validBases.append("1");
        }

        for (int base = 2; base <= 36; base++) {
            try {
                int number1Decimal = Integer.parseInt(number1, base);
                int number2Decimal = Integer.parseInt(number2, base);
                int resultDecimal = Integer.parseInt(result, base);
                boolean isValid;

                switch (operator) {
                    case '+': isValid = (number1Decimal + number2Decimal == resultDecimal); break;
                    case '-': isValid = (number1Decimal - number2Decimal == resultDecimal); break;
                    case '*': isValid = (number1Decimal * number2Decimal == resultDecimal); break;
                    default: isValid = (number1Decimal / (double) number2Decimal == resultDecimal); break;
                }

                if (isValid) {
                    char baseRepresentation;
                    if (base <= 9) {
                        baseRepresentation = String.valueOf(base).charAt(0);
                    } else if (base <= 35) {
                        baseRepresentation = (char) ((base - 10) + 'a');
                    } else {
                        baseRepresentation = '0';
                    }
                    validBases.append(baseRepresentation);
                }
            } catch (NumberFormatException exception) {}
        }

        if (validBases.length() == 0) {
            return "invalid";
        }
        return validBases.toString();
    }

    private static boolean isValidInBase1(String number1, char operator, String number2, String result) {
        if (!containsOnlyDigits1(number1) || !containsOnlyDigits1(number2)) {
            return false;
        }
        int digitsInNumber1 = number1.length();
        int digitsInNumber2 = number2.length();
        int digitsInResult = result.length();

        switch (operator) {
            case '+': return digitsInNumber1 + digitsInNumber2 == digitsInResult;
            case '-': return digitsInNumber1 - digitsInNumber2 == digitsInResult;
            case '*': return digitsInNumber1 * digitsInNumber2 == digitsInResult;
            default: return digitsInNumber1 / (double) digitsInNumber2 == digitsInResult;
        }
    }

    private static boolean containsOnlyDigits1(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) != '1') {
                return false;
            }
        }
        return true;
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
