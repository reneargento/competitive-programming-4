package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 01/05/26.
 */
public class Calculator {

    private static class NumberParse {
        double value;
        int nextIndex;

        public NumberParse(double value, int nextIndex) {
            this.value = value;
            this.nextIndex = nextIndex;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String expression = FastReader.getLine();
        while (expression != null) {
            double result = evaluateExpression(expression);
            outputWriter.printLine(String.format("%.2f", result));
            expression = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double evaluateExpression(String expression) {
        expression = expression.replaceAll("\\s+", "");
        expression = removeSequentialUnaryOperators(expression);
        return evaluateExpression(expression, 0, expression.length() - 1);
    }

    private static double evaluateExpression(String expression, int left, int right) {
        Deque<Double> operands = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        for (int s = left; s <= right; s++) {
            char symbol = expression.charAt(s);
            if (isOperator(symbol)) {
                if (isUnaryOperator(expression, symbol, s, left)) {
                    if (expression.charAt(s + 1) == '(') {
                        int closingParenthesisIndex = getClosingParenthesisIndex(expression, s + 1, right);
                        double result = evaluateExpression(expression, s + 2, closingParenthesisIndex - 1);
                        operands.push(-result);
                        s = closingParenthesisIndex;
                    } else {
                        NumberParse numberParse = parseNumber(expression, s + 1, right);
                        s = numberParse.nextIndex;
                        operands.push(-numberParse.value);
                    }
                } else {
                    while (!operators.isEmpty() && isHigherOrEqualsPriority(operators.peek(), symbol)) {
                        processOperation(operands, operators);
                    }
                    operators.push(symbol);
                }
            } else if (symbol == '(') {
                int closingParenthesisIndex = getClosingParenthesisIndex(expression, s, right);
                double result = evaluateExpression(expression, s + 1, closingParenthesisIndex - 1);
                operands.push(result);
                s = closingParenthesisIndex;
            } else {
                NumberParse numberParse = parseNumber(expression, s, right);
                s = numberParse.nextIndex;
                operands.push(numberParse.value);
            }
        }
        if (!operators.isEmpty() && isHighPriorityOperator(operators.peek())) {
            processOperation(operands, operators);
        }

        while (!operators.isEmpty()) {
            char operator = operators.removeLast();
            double operand1 = operands.removeLast();
            double operand2 = operands.removeLast();
            double result = computeExpression(operand1, operand2, operator);
            operands.push(result);
        }
        return operands.pop();
    }

    private static NumberParse parseNumber(String expression, int startIndex, int endIndex) {
        StringBuilder value = new StringBuilder();
        int nextIndex = startIndex;

        for (int i = startIndex; i <= endIndex + 1; i++) {
            if (i == endIndex + 1) {
                nextIndex = i;
                break;
            }
            char digit = expression.charAt(i);
            if (Character.isDigit(digit)) {
                value.append(digit);
            } else {
                nextIndex = i - 1;
                break;
            }
        }
        return new NumberParse(Double.parseDouble(value.toString()), nextIndex);
    }

    private static int getClosingParenthesisIndex(String expression, int startIndex, int endIndex) {
        int openParenthesis = 1;
        for (int i = startIndex + 1; i <= endIndex; i++) {
            if (expression.charAt(i) == '(') {
                openParenthesis++;
            } else if (expression.charAt(i) == ')') {
                openParenthesis--;
                if (openParenthesis == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static String removeSequentialUnaryOperators(String expression) {
        StringBuilder updatedExpression = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            boolean isPreviousOperator = i > 0 && isOperator(expression.charAt(i - 1));

            if (symbol == '-') {
                int unaryCount = 1;
                int lastIndexProcessed = i;

                for (int j = i + 1; j < expression.length(); j++) {
                    if (expression.charAt(j) == '-') {
                        unaryCount++;
                        lastIndexProcessed = j;
                    } else {
                        break;
                    }
                }

                if (unaryCount % 2 == 1) {
                    updatedExpression.append("-");
                } else if (!isPreviousOperator) {
                    updatedExpression.append("+");
                }
                i = lastIndexProcessed;
            } else {
                updatedExpression.append(symbol);
            }
        }
        return updatedExpression.toString();
    }

    private static void processOperation(Deque<Double> operands, Deque<Character> operators) {
        char operator = operators.pop();
        double operand2 = operands.pop();
        double operand1 = operands.pop();
        double result = computeExpression(operand1, operand2, operator);
        operands.push(result);
    }

    private static double computeExpression(double operand1, double operand2, char operator) {
        switch (operator) {
            case '+': return operand1 + operand2;
            case '-': return operand1 - operand2;
            case '*': return operand1 * operand2;
            default: return operand1 / operand2;
        }
    }

    private static boolean isOperator(char symbol) {
        return symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/';
    }

    private static boolean isHighPriorityOperator(char symbol) {
        return symbol == '*' || symbol == '/';
    }

    private static boolean isHigherOrEqualsPriority(char operator1, char operator2) {
        return isHighPriorityOperator(operator1) || !isHighPriorityOperator(operator2);
    }

    private static boolean isUnaryOperator(String expression, char symbol, int index, int expressionStartIndex) {
        return (symbol == '-') &&
                (index == expressionStartIndex
                        || expression.charAt(index - 1) == '('
                        || isOperator(expression.charAt(index - 1)));
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
