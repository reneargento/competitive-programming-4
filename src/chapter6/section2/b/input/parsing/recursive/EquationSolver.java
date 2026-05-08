package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 05/05/26.
 */
public class EquationSolver {

    private static class Result {
        double value;
        boolean hasNoSolution;
        boolean hasInfiniteSolutions;

        public Result(double value, boolean hasNoSolution, boolean hasInfiniteSolutions) {
            this.value = value;
            this.hasNoSolution = hasNoSolution;
            this.hasInfiniteSolutions = hasInfiniteSolutions;
        }
    }

    private static class Operand {
        double xValue;
        double nonXValue;

        public Operand(double xValue, double nonXValue) {
            this.xValue = xValue;
            this.nonXValue = nonXValue;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String equation = FastReader.getLine();
        int equationId = 1;
        while (equation != null) {
            Result result = evaluateEquation(equation);
            if (equationId > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine("Equation #" + equationId);
            if (result.hasInfiniteSolutions) {
                outputWriter.printLine("Infinitely many solutions.");
            } else if (result.hasNoSolution) {
                outputWriter.printLine("No solution.");
            } else {
                outputWriter.printLine(String.format("x = %.6f", result.value));
            }

            equationId++;
            equation = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result evaluateEquation(String equation) {
        int equalsIndex = equation.indexOf("=");
        String part1 = equation.substring(0, equalsIndex);
        String part2 = equation.substring(equalsIndex + 1);

        Operand part1Result = evaluateEquation(part1.toCharArray(), 0, part1.length() - 1);
        Operand part2Result = evaluateEquation(part2.toCharArray(), 0, part2.length() - 1);
        return combineParts(part1Result, part2Result);
    }

    private static Operand evaluateEquation(char[] equation, int startIndex, int endIndex) {
        Deque<Character> operatorsDeque = new ArrayDeque<>();
        Deque<Operand> operandsDeque = new ArrayDeque<>();

        for (int i = startIndex; i <= endIndex; i++) {
            char symbol = equation[i];
            if (symbol == 'x') {
                operandsDeque.push(new Operand(1, 0));
            } else if (isOperator(symbol)) {
                while (!operatorsDeque.isEmpty()
                        && isHigherOrEqualsPriority(operatorsDeque.peek(), symbol)) {
                    Operand operand2 = operandsDeque.pop();
                    Operand operand1 = operandsDeque.pop();
                    char operator = operatorsDeque.pop();
                    Operand result = computeExpression(operand1, operand2, operator);
                    operandsDeque.push(result);
                }
                operatorsDeque.push(symbol);
            } else if (Character.isDigit(symbol)) {
                StringBuilder number = new StringBuilder();
                for (int j = i; j <= endIndex; j++) {
                    if (Character.isDigit(equation[j])) {
                        number.append(equation[j]);
                        i = j;
                    } else {
                        i = j - 1;
                        break;
                    }
                }
                double value = Double.parseDouble(number.toString());
                operandsDeque.push(new Operand(0, value));
            } else {
                int openParenthesis = 1;
                for (int j = i + 1; j <= endIndex; j++) {
                    if (equation[j] == '(') {
                        openParenthesis++;
                    } else if (equation[j] == ')') {
                        openParenthesis--;
                        if (openParenthesis == 0) {
                            Operand partialResult = evaluateEquation(equation, i + 1, j - 1);
                            operandsDeque.push(partialResult);
                            i = j;
                            break;
                        }
                    }
                }
            }
        }

        while (!operatorsDeque.isEmpty()) {
            char operator = operatorsDeque.pop();
            Operand operand2 = operandsDeque.pop();

            if (!operandsDeque.isEmpty()) {
                Operand operand1 = operandsDeque.pop();
                Operand result = computeExpression(operand1, operand2, operator);
                operandsDeque.push(result);
            } else {
                double xValue = 0;
                double nonXValue = 0;
                if (operator == '+') {
                    xValue += operand2.xValue;
                    nonXValue += operand2.nonXValue;
                } else {
                    xValue -= operand2.xValue;
                    nonXValue -= operand2.nonXValue;
                }
                operandsDeque.push(new Operand(xValue, nonXValue));
            }
        }
        return operandsDeque.pop();
    }

    private static Result combineParts(Operand part1Result, Operand part2Result) {
        if (part1Result.xValue == part2Result.xValue && part1Result.nonXValue != part2Result.nonXValue) {
            return new Result(0, true, false);
        }
        if (part1Result.xValue == part2Result.xValue && part1Result.nonXValue == part2Result.nonXValue) {
            return new Result(0, false, true);
        }

        double totalValue;
        double totalXValue;
        double totalNonXValue;
        if (part1Result.xValue >= part2Result.xValue) {
            totalXValue = part1Result.xValue - part2Result.xValue;
            totalNonXValue = part2Result.nonXValue - part1Result.nonXValue;
        } else {
            totalXValue = part2Result.xValue - part1Result.xValue;
            totalNonXValue = part1Result.nonXValue - part2Result.nonXValue;
        }
        totalValue = totalNonXValue / totalXValue;
        return new Result(totalValue, false, false);
    }

    private static Operand computeExpression(Operand operand1, Operand operand2, char operator) {
        double resultXValue;
        double resultNonXValue;

        if (operator == '*') {
            if (operand1.xValue == 0) {
                resultXValue = computeExpression(operand1.nonXValue, operand2.xValue, operator);
                resultNonXValue = computeExpression(operand1.nonXValue, operand2.nonXValue, operator);
            } else {
                resultXValue = computeExpression(operand2.nonXValue, operand1.xValue, operator);
                resultNonXValue = computeExpression(operand2.nonXValue, operand1.nonXValue, operator);
            }
        } else {
            resultXValue = computeExpression(operand1.xValue, operand2.xValue, operator);
            resultNonXValue = computeExpression(operand1.nonXValue, operand2.nonXValue, operator);
        }
        return new Operand(resultXValue, resultNonXValue);
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
