package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by Rene Argento on 04/05/26.
 */
public class CalculatorLanguage {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] values = new int[26];
        String expression = FastReader.getLine();
        while (!expression.equals("#")) {
            evaluateExpression(outputWriter, values, expression);
            expression = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void evaluateExpression(OutputWriter outputWriter, int[] values, String expression) {
        expression = separateTokens(expression);
        List<String> tokensList = getWords(expression);
        String[] tokens = tokensList.toArray(new String[tokensList.size()]);
        int[] originalValues = new int[values.length];
        System.arraycopy(values, 0, originalValues, 0, originalValues.length);
        evaluateExpression(values, tokens, 0, tokens.length - 1);

        boolean changed = false;
        boolean isFirstValuePrinted = true;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != originalValues[i]) {
                if (!isFirstValuePrinted) {
                    outputWriter.print(", ");
                }
                char variable = (char) ('A' + i);
                outputWriter.print(variable + " = " + values[i]);
                isFirstValuePrinted = false;
                changed = true;
            }
        }
        if (!changed) {
            outputWriter.print("No Change");
        }
        outputWriter.printLine();
    }

    private static int evaluateExpression(int[] values, String[] tokens, int startIndex, int endIndex) {

        if (startIndex == endIndex) {
            return getValueFromToken(tokens[startIndex], values);
        }

        Deque<Character> operatorsDeque = new ArrayDeque<>();
        Deque<String> operandsDeque = new ArrayDeque<>();

        for (int i = startIndex; i <= endIndex; i++) {
            String token = tokens[i];
            if (isOperator(token)) {
                operatorsDeque.push(token.charAt(0));
            } else if (isVariable(token) || isNumber(token)) {
                operandsDeque.push(token);
            } else if (token.equals("(")) {
                int closeParenthesisIndex = i + 1;
                int parenthesisOpen = 1;

                for (int j = i + 1; j <= endIndex; j++) {
                    if (tokens[j].equals("(")) {
                        parenthesisOpen++;
                    } else if (tokens[j].equals(")")) {
                        parenthesisOpen--;
                        if (parenthesisOpen == 0) {
                            closeParenthesisIndex = j;
                            break;
                        }
                    }
                }
                int result = evaluateExpression(values, tokens, i + 1, closeParenthesisIndex - 1);
                operandsDeque.push(String.valueOf(result));
                i = closeParenthesisIndex;
            }
        }

        while (!operatorsDeque.isEmpty()) {
            char operator = operatorsDeque.pop();
            String operand2 = operandsDeque.pop();
            String operand1 = operandsDeque.pop();
            int result = computeExpression(operand1, operand2, operator, values);
            operandsDeque.push(String.valueOf(result));
        }
       return Integer.parseInt(operandsDeque.pop());
    }

    private static int getVariableIndex(char variable) {
        return variable - 'A';
    }

    private static int getValueFromToken(String token, int[] values) {
        if (isNumber(token)) {
            return Integer.parseInt(token);
        } else {
            int index = getVariableIndex(token.charAt(0));
            return values[index];
        }
    }

    private static int computeExpression(String operand1String, String operand2String, char operator, int[] values) {
        int operand1 = getValueFromToken(operand1String, values);
        int operand2 = getValueFromToken(operand2String, values);

        switch (operator) {
            case '+': return operand1 + operand2;
            case '-': return operand1 - operand2;
            case '*': return operand1 * operand2;
            case '/': return operand1 / operand2;
            default:  {
                int variableIndex = getVariableIndex(operand1String.charAt(0));
                values[variableIndex] = operand2;
                return operand2;
            }
        }
    }

    private static boolean isOperator(String token) {
        if (token.length() > 1) {
            return false;
        }
        char symbol = token.charAt(0);
        return symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/' || symbol == '=';
    }

    private static boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isVariable(String token) {
        return Character.isLetter(token.charAt(0));
    }

    private static String separateTokens(String expression) {
        StringBuilder updatedExpression = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            if (i > 0
                    && expression.charAt(i - 1) != ' '
                    && expression.charAt(i - 1) != '_'
                    && !(Character.isDigit(expression.charAt(i - 1)) && Character.isDigit(expression.charAt(i)))) {
                updatedExpression.append(" ");
            }
            if (expression.charAt(i) == '_') {
                updatedExpression.append("-");
            } else {
                updatedExpression.append(expression.charAt(i));
            }
        }
        return updatedExpression.toString();
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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
