package chapter6.section2.b.input.parsing.recursive;

import java.io.*;

/**
 * Created by Rene Argento on 01/05/26.
 */
public class Smeech {

    private static class Result {
        double value;
        int endIndex;

        public Result(double value, int endIndex) {
            this.value = value;
            this.endIndex = endIndex;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String expression = FastReader.getLine();
        while (!expression.equals("()")) {
            double expectedValue = evaluateSmeech(expression);
            outputWriter.printLine(String.format("%.2f", expectedValue));
            expression = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double evaluateSmeech(String expression) {
        if (!expression.contains("(")) {
            if (!expression.contains(" ")) {
                return Double.parseDouble(expression);
            }
            String[] tokens = expression.split(" ");
            double probability = Double.parseDouble(tokens[0]);
            double value1 = Double.parseDouble(tokens[1]);
            double value2 = Double.parseDouble(tokens[2]);
            return computeExpectedValue(probability, value1, value2);
        }

        Result result = getProbability(expression, 1);
        double probability = result.value;
        Result result1 = getExpressionValue(expression, result.endIndex + 2);
        Result result2 = getExpressionValue(expression, result1.endIndex + 2);
        return computeExpectedValue(probability, result1.value, result2.value);
    }

    private static Result getProbability(String expression, int index) {
        StringBuilder probabilityString = new StringBuilder();
        int endIndex = index;

        for (int i = index; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            if (symbol == ' ' || symbol == ')') {
                endIndex = i - 1;
                break;
            }
            probabilityString.append(symbol);
        }
        double value = Double.parseDouble(probabilityString.toString());
        return new Result(value, endIndex);
    }

    private static Result getExpressionValue(String expression, int expressionStartIndex) {
        if (expression.charAt(expressionStartIndex) == '(') {
            int leftParenthesisCount = 1;
            for (int i = expressionStartIndex + 1; i < expression.length(); i++) {
                if (expression.charAt(i) == '(') {
                    leftParenthesisCount++;
                } else if (expression.charAt(i) == ')') {
                    leftParenthesisCount--;
                    if (leftParenthesisCount == 0) {
                        String subExpression = expression.substring(expressionStartIndex + 1, i);
                        double value = evaluateSmeech(subExpression);
                        return new Result(value, i);
                    }
                }
            }
        } else {
            return getProbability(expression, expressionStartIndex);
        }
        return null;
    }

    private static double computeExpectedValue(double probability, double value1, double value2) {
        double probabilityComplement = 1 - probability;
        return probability * (value1 + value2)
                + probabilityComplement * (value1 - value2);
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
