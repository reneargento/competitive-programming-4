package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 05/12/21.
 */
public class Doubleplusgood {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        char[] expression = FastReader.getLine().toCharArray();
        int numberOfResults = evaluatePossibleExpressions(expression);
        outputWriter.printLine(numberOfResults);

        outputWriter.flush();
    }

    private static int evaluatePossibleExpressions(char[] expression) {
        Set<Long> results = new HashSet<>();
        evaluatePossibleExpressions(expression, 0, results);
        return results.size();
    }

    private static void evaluatePossibleExpressions(char[] expression, int index, Set<Long> results) {
        if (index == expression.length) {
            String finalExpression = new String(expression).replace("R", "");
            long result = evaluateExpression(finalExpression);
            results.add(result);
            return;
        }

        if (expression[index] == '+') {
            expression[index] = 'R';
            evaluatePossibleExpressions(expression, index + 1, results);
            expression[index] = '+';
        }
        evaluatePossibleExpressions(expression, index + 1, results);
    }

    private static long evaluateExpression(String expression) {
        long result = 0;
        StringBuilder currentNumber = null;

        for (char symbol : expression.toCharArray()) {
            if (Character.isDigit(symbol)) {
                if (currentNumber == null) {
                    currentNumber = new StringBuilder();
                }
                currentNumber.append(symbol);
            } else {
                long currentValue = Long.parseLong(currentNumber.toString());
                result += currentValue;
                currentNumber = null;
            }
        }

        long lastValue = Long.parseLong(currentNumber.toString());
        result += lastValue;
        return result;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
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
