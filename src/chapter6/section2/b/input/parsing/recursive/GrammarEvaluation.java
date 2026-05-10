package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/05/26.
 */
public class GrammarEvaluation {

    private static class Result {
        Integer value;
        int endIndex;

        public Result(Integer value, int endIndex) {
            this.value = value;
            this.endIndex = endIndex;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String expression = FastReader.getLine();
            String result = evaluateExpression(expression);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String evaluateExpression(String expression) {
        char[] tokens = expression.toCharArray();
        Result result = evaluateExpression(tokens, 0, tokens.length - 1);
        if (result == null) {
            return "ERROR";
        } else {
            return String.valueOf(result.value);
        }
    }

    private static Result evaluateExpression(char[] tokens, int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            if (Character.isDigit(tokens[startIndex])) {
                int value = Character.getNumericValue(tokens[startIndex]);
                return new Result(value, endIndex + 1);
            } else {
                return null;
            }
        }

        Deque<Character> operatorsDeque = new ArrayDeque<>();
        Deque<Integer> operandsDeque = new ArrayDeque<>();
        for (int i = startIndex; i <= endIndex; i++) {
            char token = tokens[i];
            if (Character.isDigit(token)) {
                StringBuilder number = new StringBuilder();
                for (int j = i; j <= endIndex; j++) {
                    if (Character.isDigit(tokens[j])) {
                        number.append(tokens[j]);
                        i = j;
                    } else {
                        break;
                    }
                }
                operandsDeque.push(Integer.parseInt(number.toString()));
            } else if (isOperator(token)) {
                while (!operatorsDeque.isEmpty()
                        && isEqualOrHigherPriority(operatorsDeque.peek(), token)) {
                    boolean result = processOperation(operandsDeque, operatorsDeque);
                    if (!result) {
                        return null;
                    }
                }
                operatorsDeque.push(token);
            } else if (token == '(') {
                int openParenthesis = 1;
                boolean hasValidExpression = false;
                for (int j = i + 1; j <= endIndex; j++) {
                    if (tokens[j] == '(') {
                        openParenthesis++;
                    } else if (tokens[j] == ')') {
                        openParenthesis--;
                        if (openParenthesis == 0) {
                            Result subResult = evaluateExpression(tokens, i + 1, j - 1);
                            if (subResult == null) {
                                return null;
                            }
                            i = subResult.endIndex;
                            operandsDeque.push(subResult.value);
                            hasValidExpression = true;
                            break;
                        }
                    }
                }
                if (!hasValidExpression) {
                    return null;
                }
            } else {
                return null;
            }
        }

        if (operandsDeque.size() == 1) {
            return new Result(operandsDeque.pop(), endIndex + 1);
        }

        while (!operatorsDeque.isEmpty()) {
            boolean result = processOperation(operandsDeque, operatorsDeque);
            if (!result) {
                return null;
            }
        }
        if (operandsDeque.size() != 1) {
            return null;
        }
        return new Result(operandsDeque.pop(), endIndex + 1);
    }

    private static boolean processOperation(Deque<Integer> operands, Deque<Character> operators) {
        if (operators.isEmpty() || operands.size() < 2) {
            return false;
        }
        char operator = operators.pop();
        Integer operand2 = operands.pop();
        Integer operand1 = operands.pop();
        Integer result = computeExpression(operand1, operand2, operator);
        operands.push(result);
        return true;
    }

    private static Integer computeExpression(Integer operand1, Integer operand2, char operator) {
        if (operator == '+') {
            return operand1 + operand2;
        } else {
            return operand1 * operand2;
        }
    }

    private static boolean isOperator(char token) {
        return token == '*' || token == '+';
    }

    private static boolean isEqualOrHigherPriority(char tokenInDeque, char token) {
        return tokenInDeque == '*' || tokenInDeque == token;
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

        public void flush() {
            writer.flush();
        }
    }
}
