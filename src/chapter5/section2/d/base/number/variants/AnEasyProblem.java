package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 11/02/25.
 */
public class AnEasyProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String number = FastReader.getLine();
        while (number != null) {
            String smallestBase = findBase(number.trim());
            if (smallestBase == null) {
                outputWriter.printLine("such number is impossible!");
            } else {
                outputWriter.printLine(smallestBase);
            }
            number = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String findBase(String number) {
        if (number.charAt(0) == '+' || number.charAt(0) == '-') {
            number = number.substring(1);
        }

        for (int base = 2; base <= 62; base++) {
            try {
                BigInteger convertedNumber = convertNumber(number, base);
                BigInteger baseMinus1BI = BigInteger.valueOf(base - 1);
                if (convertedNumber.mod(baseMinus1BI).equals(BigInteger.ZERO)) {
                    return String.valueOf(base);
                }
            } catch (NumberFormatException exception) {
                // Ignore invalid bases
            }
        }
        return null;
    }

    private static BigInteger convertNumber(String number, int radix) {
        BigInteger convertedNumber = BigInteger.ZERO;
        BigInteger multiplier = BigInteger.ONE;
        BigInteger radixBI = BigInteger.valueOf(radix);

        for (int i = number.length() - 1; i >= 0; i--) {
            int digitSymbol = getDigitSymbol(number.charAt(i));
            if (digitSymbol >= radix) {
                throw new NumberFormatException();
            }
            BigInteger digitSymbolBI = BigInteger.valueOf(digitSymbol);
            convertedNumber = convertedNumber.add(digitSymbolBI.multiply(multiplier));
            multiplier = multiplier.multiply(radixBI);
        }
        return convertedNumber;
    }

    private static int getDigitSymbol(char symbol) {
        if (Character.isDigit(symbol)) {
            return Character.getNumericValue(symbol);
        } else if (Character.isUpperCase(symbol)) {
            return 10 + (symbol - 'A');
        } else {
            return 36 + (symbol - 'a');
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