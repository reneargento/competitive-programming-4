package chapter6.section2.b.input.parsing.recursive;

import java.io.*;

/**
 * Created by Rene Argento on 28/04/26.
 */
public class PolishNotation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseId = 1;
        String expression = FastReader.getLine();
        while (expression != null) {
            String simplifiedExpression = simplifyExpression(expression);
            outputWriter.printLine(String.format("Case %d: %s", caseId, simplifiedExpression));

            caseId++;
            expression = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String simplifyExpression(String expression) {
        String[] tokens = expression.split(" ");
        return simplifyExpression(tokens, 0, tokens.length - 1);
    }

    private static String simplifyExpression(String[] tokens, int left, int right) {
        if (left == right) {
            return tokens[left];
        }
        int middle = left;
        int operators = 0;
        int operands = 0;

        for (int i = left + 1; i <= right; i++) {
            if (isOperator(tokens[i])) {
                operators++;
            } else {
                operands++;
            }

            if (operands == operators + 1) {
                middle = i;
                break;
            }
        }

        String leftExpression = simplifyExpression(tokens, left + 1, middle);
        String rightExpression = simplifyExpression(tokens, middle + 1, right);

        boolean isLeftExpressionNumber = isNumber(leftExpression);
        boolean isRightExpressionNumber = isNumber(rightExpression);
        if (isLeftExpressionNumber && isRightExpressionNumber) {
            return computeOperation(leftExpression, rightExpression, tokens[left]);
        }
        return tokens[left] + " " + leftExpression + " " + rightExpression;
    }

    private static String computeOperation(String operand1, String operand2, String operator) {
        int result;
        int value1 = Integer.parseInt(operand1);
        int value2 = Integer.parseInt(operand2);

        switch (operator) {
            case "+": result = value1 + value2; break;
            case "-": result = value1 - value2; break;
            default: result = value1 * value2; break;
        }
        return String.valueOf(result);
    }

    private static boolean isOperator(String token) {
        if (token.length() != 1) {
            return false;
        }
        char symbol = token.charAt(0);
        return symbol == '+' || symbol == '-' || symbol == '*';
    }

    private static boolean isNumber(String expression) {
        try {
            Integer.parseInt(expression);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
