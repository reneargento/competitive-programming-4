package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/07/22.
 */
public class CamelTrading {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String expression = FastReader.getLine();
            String[] numbersString = expression.split("[^0-9]");
            String[] operators = expression.split("[0-9]");

            long maximalInterpretation = computeResult(numbersString, operators, false);
            long minimalInterpretation = computeResult(numbersString, operators, true);
            outputWriter.printLine(String.format("The maximum and minimum are %d and %d.", maximalInterpretation,
                    minimalInterpretation));
        }
        outputWriter.flush();
    }

    private static long computeResult(String[] numbersString, String[] operatorsString, boolean multiplyFirst) {
        List<Long> numbers = new ArrayList<>();
        for (String value : numbersString) {
            numbers.add(Long.parseLong(value));
        }

        List<Character> operators = new ArrayList<>();
        for (String operator : operatorsString) {
            if (operator.isEmpty()) {
                continue;
            }
            operators.add(operator.charAt(0));
        }

        for (int process = 0; process < 2; process++) {
            boolean shouldMultiply = (process == 0 && multiplyFirst)
                    || (process == 1 && !multiplyFirst);

            for (int i = 0; i < operators.size(); i++) {
                long value1 = numbers.get(i);
                long value2 = numbers.get(i + 1);

                long operationResult;
                if (operators.get(i) == '*' && shouldMultiply) {
                    operationResult = value1 * value2;
                } else if (operators.get(i) == '+' && !shouldMultiply) {
                    operationResult = value1 + value2;
                } else {
                    continue;
                }
                numbers.set(i + 1, operationResult);
                numbers.set(i, 0L);
            }

            char operatorToRemove = shouldMultiply ? '*' : '+';
            operators = removeOperators(operators, operatorToRemove);
            numbers = removeZeroes(numbers);
        }
        return numbers.get(0);
    }

    private static List<Long> removeZeroes(List<Long> numbers) {
        List<Long> newNumbers = new ArrayList<>();
        for (Long value : numbers) {
            if (value != 0) {
                newNumbers.add(value);
            }
        }
        return newNumbers;
    }

    private static List<Character> removeOperators(List<Character> operators, char operatorToRemove) {
        List<Character> newOperators = new ArrayList<>();
        for (char operator : operators) {
            if (operator != operatorToRemove) {
                newOperators.add(operator);
            }
        }
        return newOperators;
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
