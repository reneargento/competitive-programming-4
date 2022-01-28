package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/01/22.
 */
public class TwentyThreeOutOf5 {

    private enum Operator {
        PLUS, MINUS, MULTIPLY
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] numbers = readNumbers();

        while (numbers[0] != 0 || numbers[1] != 0 || numbers[2] != 0 || numbers[3] != 0
                || numbers[4] != 0) {
            boolean canResult23 = canResultIn23(numbers);
            outputWriter.printLine(canResult23 ? "Possible" : "Impossible");
            numbers = readNumbers();
        }
        outputWriter.flush();
    }

    private static int[] readNumbers() throws IOException {
        int[] numbers = new int[5];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = FastReader.nextInt();
        }
        return numbers;
    }

    private static boolean canResultIn23(int[] numbers) {
        int[] selectedNumbers = new int[5];
        Operator[] operators = new Operator[4];
        Operator[] operatorTypes = { Operator.PLUS, Operator.MINUS, Operator.MULTIPLY };
        return canResultIn23WithNumbers(numbers, selectedNumbers, 0, 0,
                operators, operatorTypes);
    }

    private static boolean canResultIn23WithNumbers(int[] numbers, int[] selectedNumbers, int numberIndex,
                                                    int numberSelectedMask, Operator[] operators,
                                                    Operator[] operatorTypes) {
        if (numberIndex == 5) {
            return canResultIn23WithNumbersAndOperators(selectedNumbers, operators, operatorTypes, 0);
        }

        for (int i = 0; i < numbers.length; i++) {
            if ((numberSelectedMask & (1 << i)) == 0) {
                int newMask = numberSelectedMask | (1 << i);
                selectedNumbers[numberIndex] = numbers[i];
                if (canResultIn23WithNumbers(numbers, selectedNumbers, numberIndex + 1, newMask, operators,
                        operatorTypes)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean canResultIn23WithNumbersAndOperators(int[] selectedNumbers, Operator[] operators,
                                                                Operator[] operatorTypes, int operatorIndex) {
        if (operatorIndex == 4) {
            return processFormula(selectedNumbers, operators) == 23;
        }

        for (Operator operatorType : operatorTypes) {
            operators[operatorIndex] = operatorType;
            if (canResultIn23WithNumbersAndOperators(selectedNumbers, operators, operatorTypes, operatorIndex + 1)) {
                return true;
            }
        }
        return false;
    }

    private static int processFormula(int[] selectedNumbers, Operator[] operators) {
        int result = selectedNumbers[0];
        for (int i = 0; i < operators.length; i++) {
            result = processOperator(result, operators[i], selectedNumbers[i + 1]);
        }
        return result;
    }

    private static int processOperator(int number, Operator operator, int value2) {
        switch (operator) {
            case PLUS: return number + value2;
            case MINUS: return number - value2;
            default: return number * value2;
        }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
