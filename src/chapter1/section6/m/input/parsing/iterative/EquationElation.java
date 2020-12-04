package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 02/12/20.
 */
public class EquationElation {

    private static class PartialSolution {
        String newEquation;
        int index;

        public PartialSolution(String newEquation, int index) {
            this.newEquation = newEquation;
            this.index = index;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int equationNumber = 1;

        while (scanner.hasNextLine()) {
            if (equationNumber > 1) {
                System.out.println();
            }

            String equation = cleanInput(scanner.nextLine());
            solve(equation);
            equationNumber++;
        }
    }

    private static void solve(String equation) {
        System.out.println(equation);
        int equalIndex = equation.indexOf('=');

        String result1 = solveEquations(equation, '*', '/', equalIndex);
        equalIndex = result1.indexOf('=');
        solveEquations(result1, '+', '-', equalIndex);
    }

    private static String cleanInput(String equation) {
        StringBuilder removedPlusEquation = new StringBuilder();

        for (int i = 0; i < equation.length(); i++) {
            char character = equation.charAt(i);
            boolean skip = false;

            if (character == '+') {
                for (int j = i - 1; j >= 0; j--) {
                    char previousCharacter = equation.charAt(j);
                    if (Character.isDigit(previousCharacter)) {
                        break;
                    }
                    if (previousCharacter == '+'
                            || previousCharacter == '-'
                            || previousCharacter == '*'
                            || previousCharacter == '/') {
                        skip = true;
                        break;
                    }
                }
            }

            if (!skip) {
                removedPlusEquation.append(character);
            }
        }
        return removedPlusEquation.toString();
    }

    private static String solveEquations(String equation, char operation1, char operation2, int equalIndex) {
        for (int i = 0; i < equalIndex; i++) {
            char character = equation.charAt(i);

            if ((character == operation1 || character == operation2)
                    && !(i == 0 && character == '-')) {
                PartialSolution partialSolution = solve(equation, i, character, equalIndex);
                equation = partialSolution.newEquation;
                equalIndex = equation.indexOf('=');
                i = partialSolution.index;
            }
        }
        return equation;
    }

    private static PartialSolution solve(String equation, int index, char operation, int equalIndex) {
        StringBuilder value1String = new StringBuilder();
        StringBuilder value2String = new StringBuilder();
        long value1 = 0;
        long value2 = 0;
        int startIndex = 0;
        int endIndex = 0;

        boolean started = false;
        boolean usedMinus = false;

        for (int i = index - 1; i >= 0; i--) {
            char character = equation.charAt(i);

            if (isTerminatorCharacter(character) ||
                    (character == ' ' && started) ||
                    (usedMinus && character == '-')) {
                value1 = Integer.parseInt(value1String.toString());
                startIndex = i + 1;
                break;
            }
            if (Character.isDigit(character) || character == '-') {
                if (!started) {
                    started = true;
                }
                if (character == '-') {
                    usedMinus = true;
                }

                value1String.insert(0, character);

                if (i == 0) {
                    value1 = Integer.parseInt(value1String.toString());
                    startIndex = i;
                }
            }
        }

        started = false;
        for (int i = index + 1; i < equalIndex; i++) {
            char character = equation.charAt(i);

            if (isTerminatorCharacter(character) ||
                    (started && (character == ' ' || character == '-'))) {
                value2 = Integer.parseInt(value2String.toString());
                endIndex = i - 1;
                break;
            }
            if (Character.isDigit(character) || character == '-') {
                if (!started) {
                    started = true;
                }
                value2String.append(character);

                if (i == equalIndex - 1) {
                    value2 = Integer.parseInt(value2String.toString());
                    endIndex = i;
                }
            }
        }

        long result = getResult(value1, value2, operation);
        String newEquation = equation.substring(0, startIndex) + result + equation.substring(endIndex + 1);
        System.out.println(newEquation);
        return new PartialSolution(newEquation, startIndex);
    }

    private static boolean isTerminatorCharacter(char character) {
        return character == '*' || character == '/' || character == '+';
    }

    private static long getResult(long value1, long value2, char operation) {
        switch (operation) {
            case '+': return value1 + value2;
            case '-': return value1 - value2;
            case '*': return value1 * value2;
            case '/': return value1 / value2;
        }
        return 0;
    }
}
