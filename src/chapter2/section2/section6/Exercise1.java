package chapter2.section2.section6;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 22/01/21.
 */
public class Exercise1 {

    public static void main(String[] args) {
        String[] expression1 = { "+", "2", "*", "6", "3" };
        double value1 = evaluatePrefixExpression(expression1);
        System.out.println("Value: " + value1 + " Expected: 20.0");

        String[] expression2 = { "*", "+", "2", "6", "3" };
        double value2 = evaluatePrefixExpression(expression2);
        System.out.println("Value: " + value2 + " Expected: 24.0");

        String[] expression3 = { "*", "4", "-", "+", "1", "*", "2", "/", "9", "3", "5" };
        double value3 = evaluatePrefixExpression(expression3);
        System.out.println("Value: " + value3 + " Expected: 8.0");
    }

    private static double evaluatePrefixExpression(String[] expression) {
        Deque<String> stack = new ArrayDeque<>();

        for (String token : expression) {
            if (Character.isDigit(token.charAt(0))) {
                double value2 = Double.parseDouble(token);

                while (isTopOfStackANumber(stack)) {
                    double value1 = Double.parseDouble(stack.pop());
                    String operation = stack.pop();
                    value2 = compute(value1, operation, value2);
                }
                stack.push(String.valueOf(value2));
            } else {
                stack.push(token);
            }
        }
        return Double.parseDouble(stack.pop());
    }

    private static boolean isTopOfStackANumber(Deque<String> stack) {
        return !stack.isEmpty() && Character.isDigit(stack.peek().charAt(0));
    }

    private static double compute(double value1, String operation, double value2) {
        switch (operation) {
            case "+": return value1 + value2;
            case "-": return value1 - value2;
            case "/": return value1 / value2;
            default: return value1 * value2;
        }
    }
}
