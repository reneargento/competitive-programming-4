package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 04/02/25.
 */
public class MixedBaseArithmetic {

    private static final int DIGITS = 0;
    private static final int UPPER_CASE_LETTERS = 1;
    private static final int LOWER_CASE_LETTERS = 2;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            String counter = data[0];
            int increment = Integer.parseInt(data[1]);

            String incrementedCounter = incrementCounter(counter, increment);
            outputWriter.printLine(incrementedCounter);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String incrementCounter(String counter, int incrementValue) {
        int[] baseTypes = getBaseTypes(counter);
        BigInteger increment = BigInteger.valueOf(incrementValue);
        BigInteger index = getCounterIndex(counter).add(increment);
        return getCounter(counter, index, baseTypes);
    }

    private static int[] getBaseTypes(String counter) {
        int[] baseTypes = new int[counter.length() + 1];

        for (int i = counter.length() - 1; i >= 0; i--) {
            char symbol = counter.charAt(i);
            int baseType = getBaseType(symbol);
            int inverseIndex = counter.length() - 1 - i;
            baseTypes[inverseIndex] = baseType;
        }
        baseTypes[baseTypes.length - 1] = baseTypes[baseTypes.length - 2];
        return baseTypes;
    }

    private static BigInteger getCounterIndex(String counter) {
        BigInteger index = BigInteger.ZERO;
        BigInteger multiplier = BigInteger.ONE;

        for (int i = counter.length() - 1; i >= 0; i--) {
            char symbol = counter.charAt(i);
            int symbolIndexValue = getSymbolIndex(symbol);
            BigInteger symbolIndex = BigInteger.valueOf(symbolIndexValue);
            int alphabetSizeValue = getAlphabetSize(symbol);
            BigInteger alphabetSize = BigInteger.valueOf(alphabetSizeValue);

            index = index.add(symbolIndex.multiply(multiplier));
            multiplier = multiplier.multiply(alphabetSize);
        }
        return index;
    }

    private static int getAlphabetSize(char symbol) {
        if (Character.isDigit(symbol)) {
            return 10;
        }
        return 26;
    }

    private static int getAlphabetSize(int baseType) {
        if (baseType == DIGITS) {
            return 10;
        }
        return 26;
    }

    private static int getBaseType(char symbol) {
        if (Character.isDigit(symbol)) {
            return DIGITS;
        } if (Character.isUpperCase(symbol)) {
            return UPPER_CASE_LETTERS;
        }
        return LOWER_CASE_LETTERS;
    }

    private static int getSymbolIndex(char symbol) {
        if (Character.isDigit(symbol)) {
            return Character.getNumericValue(symbol);
        } if (Character.isUpperCase(symbol)) {
            return (symbol - 'A');
        }
        return  (symbol - 'a');
    }

    private static char getSymbol(long digitIndex, int baseType) {
        if (baseType == DIGITS) {
            return String.valueOf(digitIndex).charAt(0);
        } else if (baseType == UPPER_CASE_LETTERS) {
            return (char) ('A' + digitIndex);
        }
        return (char) ('a' + digitIndex);
    }

    private static String getCounter(String originalCounter, BigInteger index, int[] baseTypes) {
        StringBuilder incrementedCounter = new StringBuilder();
        int previousBaseType = -1;
        int baseTypeIndex = 0;

        while (index.compareTo(BigInteger.ZERO) > 0) {
            int baseType;
            if (baseTypeIndex < baseTypes.length) {
                baseType = baseTypes[baseTypeIndex];
                previousBaseType = baseType;
            } else {
                baseType = previousBaseType;
            }

            int alphabetSizeValue = getAlphabetSize(baseType);
            BigInteger alphabetSize = BigInteger.valueOf(alphabetSizeValue);

            if (incrementedCounter.length() >= originalCounter.length()
                    && (baseType == UPPER_CASE_LETTERS || baseType == LOWER_CASE_LETTERS)) {
                // On letters the carry repeat the previous value
                index = index.subtract(BigInteger.ONE);
            }
            long digitIndex = index.mod(alphabetSize).longValue();
            char symbol = getSymbol(digitIndex, baseType);

            incrementedCounter.append(symbol);
            index = index.divide(alphabetSize);
            baseTypeIndex++;
        }

        if (incrementedCounter.length() < originalCounter.length()) {
            incrementedCounter.append(originalCounter.charAt(0));
        }
        return incrementedCounter.reverse().toString();
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
