package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 03/05/25.
 */
public class EgyptianMultiplication {

    private static final int MOD = 100000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String egyptianNumber1 = FastReader.getLine();
        while (!egyptianNumber1.isEmpty()) {
            String egyptianNumber2 = FastReader.getLine();
            List<String> multiplicationSteps = computeMultiplicationSteps(egyptianNumber1, egyptianNumber2);
            for (String multiplicationStep : multiplicationSteps) {
                outputWriter.printLine(multiplicationStep);
            }
            egyptianNumber1 = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> computeMultiplicationSteps(String egyptianNumber1, String egyptianNumber2) {
        List<String> multiplicationSteps = new ArrayList<>();
        int sum = 0;
        int decimal2 = egyptianNumberToDecimal(egyptianNumber2);
        int decimalLeftColumn = 1;

        Set<Integer> asteriskRows = computeAsteriskRows(decimal2);

        String leftColumn = "| ";
        String rightColumn = egyptianNumber1;
        int rowIndex = 1;

        while (decimalLeftColumn <= decimal2) {
            String leftColumnOutput = leftColumn;
            int rightColumnDecimalValue = egyptianNumberToDecimal(rightColumn);
            if (asteriskRows.contains(rowIndex)) {
                leftColumnOutput += "*";
                sum += rightColumnDecimalValue;
            }

            String step = String.format("%-34s%s", leftColumnOutput, rightColumn);
            multiplicationSteps.add(step);

            decimalLeftColumn = egyptianNumberToDecimal(leftColumn) * 2;
            leftColumn = decimalToEgyptianNumber(decimalLeftColumn);

            rightColumn = decimalToEgyptianNumber(rightColumnDecimalValue * 2);
            rowIndex++;
        }

        sum %= MOD;
        String result = decimalToEgyptianNumber(sum);
        if (result.equals(" ")) {
            result = "";
        }
        multiplicationSteps.add("The solution is: " + result);
        return multiplicationSteps;
    }

    private static Set<Integer> computeAsteriskRows(int decimal) {
        Set<Integer> asteriskRows = new HashSet<>();
        String bits = Integer.toBinaryString(decimal);
        int rowIndex = 1;

        for (int i = bits.length() - 1; i >= 0; i--, rowIndex++) {
            if (bits.charAt(i) == '1') {
                asteriskRows.add(rowIndex);
            }
        }
        return asteriskRows;
    }

    private static int egyptianNumberToDecimal(String egyptianNumber) {
        int decimalNumber = 0;

        String[] groups = egyptianNumber.split(" ");
        for (String group : groups) {
            char symbol = group.charAt(0);
            int value;
            switch (symbol) {
                case '|': value = 1; break;
                case 'n': value = 10; break;
                case '9': value = 100; break;
                case '8': value = 1000; break;
                default: value = 10000;
            }
            decimalNumber += value * group.length();
        }
        return decimalNumber % MOD;
    }

    private static String decimalToEgyptianNumber(int decimal) {
        StringBuilder egyptianNumber = new StringBuilder();
        decimal %= MOD;

        int tenThousands = decimal / 10000;
        if (tenThousands > 0) {
            addSymbols(egyptianNumber, 'r', tenThousands);
            decimal %= 10000;
        }
        int thousands = decimal / 1000;
        if (thousands > 0) {
            addSymbols(egyptianNumber, '8', thousands);
            decimal %= 1000;
        }
        int hundreds = decimal / 100;
        if (hundreds > 0) {
            addSymbols(egyptianNumber, '9', hundreds);
            decimal %= 100;
        }
        int tens = decimal / 10;
        if (tens > 0) {
            addSymbols(egyptianNumber, 'n', tens);
            decimal %= 10;
        }
        if (decimal > 0) {
            addSymbols(egyptianNumber, '|', decimal);
        }
        return egyptianNumber.reverse().toString().trim() + " ";
    }

    private static void addSymbols(StringBuilder egyptianNumber, char symbol, int length) {
        for (int i = 0; i < length; i++) {
            egyptianNumber.append(symbol);
        }
        egyptianNumber.append(" ");
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
