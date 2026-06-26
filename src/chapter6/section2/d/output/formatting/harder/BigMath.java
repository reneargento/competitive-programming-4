package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 26/06/2026.
 */
public class BigMath {

    private static final String[][] SYMBOLS = new String[][] {
            {
                    "000",
                    "0.0",
                    "0.0",
                    "0.0",
                    "000"
            },
            {
                    ".0.",
                    ".0.",
                    ".0.",
                    ".0.",
                    ".0."
            },
            {
                    "000",
                    "..0",
                    "000",
                    "0..",
                    "000"
            },
            {
                    "000",
                    "..0",
                    "000",
                    "..0",
                    "000"
            },
            {
                    "0.0",
                    "0.0",
                    "000",
                    "..0",
                    "..0"
            },
            {
                    "000",
                    "0..",
                    "000",
                    "..0",
                    "000"
            },
            {
                    "0..",
                    "0..",
                    "000",
                    "0.0",
                    "000"
            },
            {
                    "000",
                    "..0",
                    "..0",
                    "..0",
                    "..0"
            },
            {
                    "000",
                    "0.0",
                    "000",
                    "0.0",
                    "000"
            },
            {
                    "000",
                    "0.0",
                    "000",
                    "..0",
                    "..0"
            },
            {
                    ".0.",
                    ".0.",
                    "000",
                    ".0.",
                    ".0."
            },
            {
                    "...",
                    "...",
                    "000",
                    "...",
                    "..."
            },
            {
                    "0.0",
                    "0.0",
                    ".0.",
                    "0.0",
                    "0.0"
            },
            {
                    ".0.",
                    "...",
                    "000",
                    "...",
                    ".0."
            }
    };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        while (true) {
            String[] scoreboard = new String[5];
            for (int i = 0; i < scoreboard.length; i++) {
                scoreboard[i] = FastReader.getLine();
            }

            String equation = getEquation(scoreboard);
            if (equation.equals("0")) {
                break;
            }

            String result = solveEquation(equation);
            printResult(result, outputWriter);
            FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String getEquation(String[] scoreboard) {
        StringBuilder equation = new StringBuilder();
        int columns = scoreboard[0].length();
        for (int i = 0; i < columns; i += 4) {
            char symbol = mapScoreboardToSymbol(scoreboard, i);
            equation.append(symbol);
        }
        return equation.toString();
    }

    private static String solveEquation(String equation) {
        Deque<Integer> operands = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        for (int i = 0; i < equation.length(); i++) {
            char symbol = equation.charAt(i);
            if (isOperator(symbol)) {
                while (!operators.isEmpty()
                        && isHighPriorityOperator(operators.peek())) {
                    processOperationTop(operands, operators);
                }
                operators.push(symbol);
            } else {
                StringBuilder operand = new StringBuilder();
                while (i < equation.length()
                        && Character.isDigit(equation.charAt(i))) {
                    operand.append(equation.charAt(i));
                    i++;
                }
                i--;
                int number = Integer.parseInt(operand.toString());
                operands.push(number);
            }
        }

        if (!operators.isEmpty()
                && isHighPriorityOperator(operators.peek())) {
            processOperationTop(operands, operators);
        }
        while (!operators.isEmpty()) {
            processOperationBottom(operands, operators);
        }
        return String.valueOf(operands.pop());
    }

    private static void processOperationTop(Deque<Integer> operands, Deque<Character> operators) {
        int number2 = operands.pop();
        int number1 = operands.pop();
        char operator = operators.pop();
        int result = solveEquation(number1, number2, operator);
        operands.push(result);
    }

    private static void processOperationBottom(Deque<Integer> operands, Deque<Character> operators) {
        int number1 = operands.removeLast();
        int number2 = operands.removeLast();
        char operator = operators.removeLast();
        int result = solveEquation(number1, number2, operator);
        operands.addLast(result);
    }

    private static int solveEquation(int number1, int number2, char operator) {
        if (operator == '+') {
            return number1 + number2;
        } else if (operator == '-') {
            return number1 - number2;
        } else if (operator == '*') {
            return number1 * number2;
        } else {
            return number1 / number2;
        }
    }

    private static char mapScoreboardToSymbol(String[] scoreboard, int startColumn) {
        String[] lines = new String[scoreboard.length];
        for (int i = 0; i < scoreboard.length; i++) {
            lines[i] = scoreboard[i].substring(startColumn, startColumn + 3);
        }

        for (int i = 0; i < SYMBOLS.length; i++) {
            if (isSymbol(lines, i)) {
                if (i <= 9) {
                    return (char) ('0' + i);
                } else if (i == 10) {
                    return '+';
                } else if (i == 11) {
                    return '-';
                } else if (i == 12) {
                    return '*';
                } else {
                    return '/';
                }
            }
        }
        return '0';
    }

    private static boolean isSymbol(String[] lines, int index) {
        for (int i = 0; i < lines.length; i++) {
            if (!lines[i].equals(SYMBOLS[index][i])) {
                return false;
            }
        }
        return true;
    }

    private static void printResult(String numbers, OutputWriter outputWriter) {
        for (int row = 0; row < 5; row++) {
            for (int i = 0; i < numbers.length(); i++) {
                if (i > 0) {
                    outputWriter.print(' ');
                }
                if (Character.isDigit(numbers.charAt(i))) {
                    int numberIndex = Character.getNumericValue(numbers.charAt(i));
                    outputWriter.print(SYMBOLS[numberIndex][row]);
                } else {
                    outputWriter.print(SYMBOLS[11][row]);
                }
            }
            outputWriter.printLine();
        }
        outputWriter.printLine();
    }

    private static boolean isOperator(char operator) {
        return operator == '+' || operator == '-' || operator == '*' || operator == '/';
    }

    private static boolean isHighPriorityOperator(char operator) {
        return operator == '*' || operator == '/';
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