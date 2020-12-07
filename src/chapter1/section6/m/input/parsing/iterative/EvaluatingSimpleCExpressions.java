package chapter1.section6.m.input.parsing.iterative;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 07/12/20.
 */
public class EvaluatingSimpleCExpressions {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        String expression = FastReader.getLine();

        while (expression != null) {
            evaluateExpression(expression);
            expression = FastReader.getLine();
        }
    }

    private static void evaluateExpression(String expression) {
        System.out.println("Expression: " + expression);
        expression = expression.replace(" ", "");

        Map<Character, Integer> variables = new HashMap<>();
        Map<Character, Integer> preIncrements = new HashMap<>();
        Map<Character, Integer> postIncrements = new HashMap<>();

        String newExpression = getNewExpression(expression, preIncrements, postIncrements);

        long expressionValue = evaluateExpressionValue(newExpression, variables, preIncrements, postIncrements);
        printValues(variables, expressionValue);
    }

    private static String getNewExpression(String expression, Map<Character, Integer> preIncrements,
                                           Map<Character, Integer> postIncrements) {
        StringBuilder newExpression = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);

            if (symbol == '+' || symbol == '-') {
                if (skip(expression, i, symbol, preIncrements, postIncrements, getIncrement(symbol))) {
                    i++;
                    continue;
                }
            }
            newExpression.append(symbol);
        }
        return newExpression.toString();
    }

    private static long evaluateExpressionValue(String newExpression, Map<Character, Integer> variables,
                                                Map<Character, Integer> preIncrements,
                                                Map<Character, Integer> postIncrements) {
        long expressionValue = 0;

        for (int i = 0; i < newExpression.length(); i++) {
            char symbol = newExpression.charAt(i);

            if (Character.isLetter(symbol)) {
                int value = getValue(symbol);
                if (preIncrements.containsKey(symbol)) {
                    value += preIncrements.get(symbol);
                }

                if (postIncrements.containsKey(symbol)) {
                    variables.put(symbol, value + postIncrements.get(symbol));
                } else {
                    variables.put(symbol, value);
                }

                if (i == 0 || newExpression.charAt(i - 1) == '+') {
                    expressionValue += value;
                } else {
                    expressionValue -= value;
                }
            }
        }
        return expressionValue;
    }

    private static void printValues(Map<Character, Integer> variables, long expressionValue) {
        List<Character> variableList = new ArrayList<>(variables.keySet());
        Collections.sort(variableList);

        System.out.printf("    value = %d\n", expressionValue);
        for (Character variable : variableList) {
            int value = variables.get(variable);
            System.out.printf("    %s = %d\n", variable, value);
        }
    }

    private static boolean skip(String expression, int index, char symbol, Map<Character, Integer> preIncrements,
                                Map<Character, Integer> postIncrements, int increment) {
        if (index < expression.length() - 1 && expression.charAt(index + 1) == symbol) {
            if (index > 0 && Character.isLetter(expression.charAt(index - 1))) {
                postIncrements.put(expression.charAt(index - 1), increment);
                return true;
            } else {
                preIncrements.put(expression.charAt(index + 2), increment);
                return true;
            }
        }
        return false;
    }

    private static int getValue(char variable) {
        return variable - 'a' + 1;
    }

    private static int getIncrement(char symbol) {
        return symbol == '+' ? 1 : -1;
    }

    public static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        // Used to check EOF
        // If getLine() == null, it is a EOF
        // Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
