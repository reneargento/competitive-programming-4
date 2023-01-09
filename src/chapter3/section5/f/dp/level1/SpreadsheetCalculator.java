package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rene Argento on 07/01/23.
 */
public class SpreadsheetCalculator {

    private static final int CIRCULAR_REFERENCE_ID = -10000000;
    private static final Character ADD = '+';
    private static final Character SUBTRACT = '-';
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(".*[A-Z]+.*");

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        while (rows != 0 || columns != 0) {
            String[][] spreadsheet = new String[rows][columns];
            for (int row = 0; row < spreadsheet.length; row++) {
                for (int column = 0; column < spreadsheet[0].length; column++) {
                    spreadsheet[row][column] = FastReader.next();
                }
            }

            boolean hasCircularReference = computeSpreadsheet(spreadsheet);
            if (hasCircularReference) {
                printUnevaluatedCells(spreadsheet, outputWriter);
            } else {
                printAllCells(spreadsheet, outputWriter);
            }
            outputWriter.printLine();

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean computeSpreadsheet(String[][] spreadsheet) {
        Set<Integer> inProgressCells = new HashSet<>();
        boolean hasCircularReference = false;

        for (int row = 0; row < spreadsheet.length; row++) {
            for (int column = 0; column < spreadsheet[row].length; column++) {
                if (isExpression(spreadsheet[row][column])) {
                    int cellId = getCellId(row, column, spreadsheet[row].length);
                    inProgressCells.add(cellId);
                    if (computeSpreadsheet(spreadsheet, row, column, inProgressCells) == CIRCULAR_REFERENCE_ID) {
                        hasCircularReference = true;
                    }
                    inProgressCells.remove(cellId);
                }
            }
        }
        return hasCircularReference;
    }

    private static long computeSpreadsheet(String[][] spreadsheet, int row, int column, Set<Integer> inProgressCells) {
        if (!isExpression(spreadsheet[row][column])) {
            return Long.parseLong(spreadsheet[row][column]);
        }

        String expression = spreadsheet[row][column];
        long value = 0;
        Character nextOperation = null;

        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);

            if (symbol == ADD) {
                nextOperation = ADD;
            } else if (symbol == SUBTRACT) {
                nextOperation = SUBTRACT;
            } else {
                long result;
                if (Character.isDigit(symbol)) {
                    StringBuilder constant = new StringBuilder();
                    constant.append(symbol);
                    while (i < expression.length() - 1 && Character.isDigit(expression.charAt(i + 1))) {
                        constant.append(expression.charAt(i + 1));
                        i++;
                    }
                    result = Long.parseLong(constant.toString());
                } else {
                    int nextRow = symbol - 'A';
                    int nextColumn = Character.getNumericValue(expression.charAt(i + 1));
                    i++;

                    int nextCellId = getCellId(nextRow, nextColumn, spreadsheet[nextRow].length);
                    if (inProgressCells.contains(nextCellId)) {
                        return CIRCULAR_REFERENCE_ID;
                    }
                    inProgressCells.add(nextCellId);

                    result = computeSpreadsheet(spreadsheet, nextRow, nextColumn, inProgressCells);
                    inProgressCells.remove(nextCellId);
                    if (result == CIRCULAR_REFERENCE_ID) {
                        return CIRCULAR_REFERENCE_ID;
                    }
                }

                if (nextOperation == ADD) {
                    value += result;
                } else if (nextOperation == SUBTRACT) {
                    value -= result;
                } else {
                    value = result;
                }
            }
        }
        spreadsheet[row][column] = String.valueOf(value);
        return value;
    }

    private static void printUnevaluatedCells(String[][] spreadsheet, OutputWriter outputWriter) {
        for (int row = 0; row < spreadsheet.length; row++) {
            for (int column = 0; column < spreadsheet[row].length; column++) {
                if (isExpression(spreadsheet[row][column])) {
                    String cell = String.valueOf(getRowId(row)) + column;
                    outputWriter.printLine(String.format("%s: %s", cell, spreadsheet[row][column]));
                }
            }
        }
    }

    private static void printAllCells(String[][] spreadsheet, OutputWriter outputWriter) {
        outputWriter.print(" ");
        for (int column = 0; column < spreadsheet[0].length; column++) {
            outputWriter.print(String.format("%6d", column));
        }
        outputWriter.printLine();

        for (int row = 0; row < spreadsheet.length; row++) {
            for (int column = 0; column < spreadsheet[row].length; column++) {
                if (column == 0) {
                    outputWriter.print(getRowId(row));
                }
                outputWriter.print(String.format("%6s", spreadsheet[row][column]));
            }
            outputWriter.printLine();
        }
    }

    private static boolean isExpression(String cell) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(cell);
        return matcher.matches();
    }

    private static char getRowId(int row) {
        return (char) ('A' + row);
    }

    private static int getCellId(int row, int column, int columnNumber) {
        return row * columnNumber + column;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
