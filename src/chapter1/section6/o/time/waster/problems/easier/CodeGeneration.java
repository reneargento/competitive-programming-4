package chapter1.section6.o.time.waster.problems.easier;

import java.util.*;

/**
 * Created by Rene Argento on 17/12/20.
 */
public class CodeGeneration {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int test = 1;

        while (scanner.hasNext()) {
            if (test > 1) {
                System.out.println();
            }

            String expression = scanner.next();
            generateAssembly(expression);
            test++;
        }
    }

    private static void generateAssembly(String expression) {
        expression += "0"; // Add dummy character
        int variableDigit = 0;
        boolean isInStack = false;
        String previousOperand = null;

        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);

            if (!isOperator(symbol)) {
                String operand = String.valueOf(symbol);
                if (previousOperand != null) {
                    if (isInStack) {
                        String newSymbol = "$" + variableDigit;
                        variableDigit++;
                        System.out.println("ST " + newSymbol);
                    }
                    System.out.println("L " + previousOperand);
                    isInStack = true;
                }
                previousOperand = operand;
            } else {
                if (symbol != '@') {
                    if (symbol == '+') {
                        if (previousOperand != null) {
                            System.out.println("A " + previousOperand);
                            previousOperand = null;
                        } else {
                            variableDigit--;
                            System.out.println("A $" + variableDigit);
                        }
                    } else if (symbol == '*') {
                        if (previousOperand != null) {
                            System.out.println("M " + previousOperand);
                            previousOperand = null;
                        } else {
                            variableDigit--;
                            System.out.println("M $" + variableDigit);
                        }
                    } else if (symbol == '-') {
                        if (previousOperand != null) {
                            System.out.println("S " + previousOperand);
                            previousOperand = null;
                        } else {
                            variableDigit--;
                            System.out.println("N");
                            System.out.println("A $" + variableDigit);
                        }
                    } else if (symbol == '/') {
                        if (previousOperand != null) {
                            System.out.println("D " + previousOperand);
                            previousOperand = null;
                        } else {
                            String newSymbol = "$" + variableDigit;
                            System.out.println("ST " + newSymbol);
                            System.out.println("L $" + (variableDigit - 1));
                            System.out.println("D $" + variableDigit);
                            variableDigit--;
                        }
                    }
                } else {
                    if (previousOperand != null) {
                        if (isInStack) {
                            String newSymbol = "$" + variableDigit;
                            variableDigit++;
                            System.out.println("ST " + newSymbol);
                        }
                        System.out.println("L " + previousOperand);
                        previousOperand = null;
                        isInStack = true;
                    }
                    System.out.println("N");
                }
            }
        }
    }

    private static boolean isOperator(char symbol) {
        return symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/' || symbol == '@';
    }
}
