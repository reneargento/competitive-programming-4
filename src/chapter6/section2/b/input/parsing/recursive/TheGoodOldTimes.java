package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 30/04/26.
 */
public class TheGoodOldTimes {

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
            outputWriter.printLine(String.format("%.3f", result));
            expression = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double evaluateExpression(String expression) {
        Deque<Double> operands = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);

            if (isOperator(symbol)) {
                if (isUnaryOperator(expression, symbol, i)) {
                    NumberParse numberParse = parseNumber(expression, i + 1);
                    i = numberParse.nextIndex;
                    operands.push(-numberParse.value);
                } else {
                    while (!operators.isEmpty()
                            && isHigherOrEqualsPriority(operators.peek(), symbol)) {
                        processOperation(operands, operators);
                    }
                    operators.push(symbol);
                }
            } else {
                NumberParse numberParse = parseNumber(expression, i);
                i = numberParse.nextIndex;
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

    private static NumberParse parseNumber(String expression, int startIndex) {
        StringBuilder value = new StringBuilder();
        int nextIndex = startIndex;

        for (int i = startIndex; i <= expression.length(); i++) {
            if (i == expression.length()) {
                nextIndex = i;
                break;
            }
            char digit = expression.charAt(i);
            if (!isOperator(digit)) {
                value.append(digit);
            } else {
                nextIndex = i - 1;
                break;
            }
        }
        return new NumberParse(Double.parseDouble(value.toString()), nextIndex);
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

    private static boolean isUnaryOperator(String expression, char symbol, int index) {
        return (symbol == '-') &&
                (index == 0 || isOperator(expression.charAt(index - 1)));
    }

    private static boolean isHighPriorityOperator(char symbol) {
        return symbol == '*' || symbol == '/';
    }

    private static boolean isHigherOrEqualsPriority(char operator1, char operator2) {
        return isHighPriorityOperator(operator1) || !isHighPriorityOperator(operator2);
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
