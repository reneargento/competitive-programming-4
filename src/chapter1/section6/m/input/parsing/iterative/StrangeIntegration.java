package chapter1.section6.m.input.parsing.iterative;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 03/12/20.
 */
public class StrangeIntegration {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 1; t <= tests; t++) {
            int expressions = scanner.nextInt();
            scanner.nextLine();
            Map<String, String> expressionsMap = new HashMap<>();
            String lastExpression = "";

            for (int i = 0; i < expressions; i++) {
                String expression = scanner.nextLine();
                String[] components = removeSpaces(expression).split("=");
                String key = components[0];
                String operation = components[1];
                operation = addParenthesis(operation, expressionsMap);

                StringBuilder value = new StringBuilder();
                StringBuilder currentKey = new StringBuilder();

                for (int j = 0; j < operation.length(); j++) {
                    char symbol = operation.charAt(j);

                    if (symbol == '+' || symbol == '*' || symbol == '(' || symbol == ')' || j == operation.length() - 1) {
                        if (j == operation.length() - 1 && symbol != ')') {
                            currentKey.append(symbol);
                        }

                        String currentKeyString = currentKey.toString();
                        String subExpression = currentKeyString;

                        if (expressionsMap.containsKey(currentKeyString)) {
                            subExpression = expressionsMap.get(currentKey.toString());
                        }

                        value.append(subExpression);

                        if (j != operation.length() - 1
                                || (j ==  operation.length() - 1 && symbol == ')')) {
                            value.append(symbol);
                        }
                        currentKey = new StringBuilder();
                    } else {
                        currentKey.append(symbol);
                    }
                }
                String newExpression = value.toString();
                expressionsMap.put(key, newExpression);
                lastExpression = newExpression;
            }
            System.out.printf("Expression #%d: %s\n", t, lastExpression);
        }
    }

    // When to add parenthesis
    // 1 + 1 No parenthesis
    // A + 1 No parenthesis
    // 1 * 1 No parenthesis

    // 1 * A On the right, always

    // 1 + A On the right if it contains + outside parenthesis
    // A + A On the right if it contains + outside parenthesis

    // A * 1 On the left if it contains + outside parenthesis
    // A * A On the left if it contains + outside parenthesis, on the right if it contains any operation outside parenthesis
    private static String addParenthesis(String expression, Map<String, String> expressionsMap) {
        boolean isSum = expression.contains("+");
        String splitChar = isSum ? "+" : "*";
        String regexSplitChar = isSum ? "\\+" : "\\*";

        String[] components = expression.split(regexSplitChar);
        boolean isNumberComponent1 = Character.isDigit(components[0].charAt(0));
        boolean isNumberComponent2 = Character.isDigit(components[1].charAt(0));

        // 1 + 1 No parenthesis
        // A + 1 No parenthesis
        // 1 * 1 No parenthesis
        if (isNumberComponent1 && isNumberComponent2
                || (!isNumberComponent1 && isNumberComponent2 && isSum)) {
            return expression;
        }

        // 1 * A On the right, always
        if (isNumberComponent1 && !isSum && !isNumberComponent2) {
            components[1] = addParenthesisToComponent(components[1]);
        }

        String expression0 = expressionsMap.get(components[0]);
        String expression1 = expressionsMap.get(components[1]);

        // 1 + A On the right if it contains + outside parenthesis
        // A + A On the right if it contains + outside parenthesis
        if (isSum && containsOperationOutsideParenthesis(expression1, '+')) {
            components[1] = addParenthesisToComponent(components[1]);
        }

        // A * 1 On the left if it contains + outside parenthesis
        if (!isNumberComponent1 && !isSum && isNumberComponent2 && containsOperationOutsideParenthesis(expression0, '+')) {
            components[0] = addParenthesisToComponent(components[0]);
        }

        // A * A On the left if it contains + outside parenthesis, on the right if it contains any operation outside parenthesis
        if (!isNumberComponent1 && !isSum && !isNumberComponent2) {
            if (containsOperationOutsideParenthesis(expression0, '+')) {
                components[0] = addParenthesisToComponent(components[0]);
            }
            if (containsOperationOutsideParenthesis(expression1, '+')
                    || containsOperationOutsideParenthesis(expression1, '*') ) {
                components[1] = addParenthesisToComponent(components[1]);
            }
        }
        return components[0] + splitChar + components[1];
    }

    private static boolean containsOperationOutsideParenthesis(String expression, char operation) {
        int leftParenthesis = 0;
        int rightParenthesis = 0;

        for (char symbol : expression.toCharArray()) {
            if (symbol == '(') {
                leftParenthesis++;
            } else if (symbol == ')') {
                rightParenthesis++;
            } else if (symbol == operation) {
                if (rightParenthesis - leftParenthesis == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String addParenthesisToComponent(String component) {
        return "(" + component + ")";
    }

    private static String removeSpaces(String string) {
        StringBuilder stringWithoutSpaces = new StringBuilder();
        for (char character : string.toCharArray()) {
            if (character != ' ') {
                stringWithoutSpaces.append(character);
            }
        }
        return stringWithoutSpaces.toString();
    }
}
