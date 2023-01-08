package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/01/23.
 */
public class Spreadsheet {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int spreadsheets = FastReader.nextInt();

        for (int s = 0; s < spreadsheets; s++) {
            int columns = FastReader.nextInt();
            int rows = FastReader.nextInt();

            String[][] cells = new String[rows][columns];
            for (int row = 0; row < cells.length; row++) {
                for (int column = 0; column < cells[0].length; column++) {
                    cells[row][column] = FastReader.next();
                }
            }

            computeValues(cells);
            for (int row = 0; row < cells.length; row++) {
                for (int column = 0; column < cells[0].length; column++) {
                    outputWriter.print(cells[row][column]);
                    if (column != cells[0].length - 1) {
                        outputWriter.print(" ");
                    }
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static void computeValues(String[][] cells) {
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                computeValues(cells, row, column);
            }
        }
    }

    private static int computeValues(String[][] cells, int row, int column) {
        if (cells[row][column].charAt(0) != '=') {
            return Integer.parseInt(cells[row][column]);
        }

        String[] cellsToCompute = cells[row][column].substring(1).split("\\+");
        int value = 0;
        for (String cell : cellsToCompute) {
            StringBuilder columnCode = new StringBuilder();
            for (int i = 0; i < cell.length(); i++) {
                char character = cell.charAt(i);
                if (Character.isLetter(character)) {
                    columnCode.append(character);
                } else {
                    break;
                }
            }
            int nextColumn = getColumnNumber(columnCode.toString()) - 1;
            String rowString = cell.substring(columnCode.length());
            int nextRow = Integer.parseInt(rowString) - 1;
            value += computeValues(cells, nextRow, nextColumn);
        }
        cells[row][column] = String.valueOf(value);
        return value;
    }

    private static int getColumnNumber(String code) {
        int column = 0;
        int multiplier = 1;

        for (int i = code.length() - 1; i >= 0; i--) {
            column += getLetterValue(code.charAt(i)) * multiplier;
            multiplier *= 26;
        }
        return column;
    }

    private static int getLetterValue(char letter) {
        return letter - 'A' + 1;
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

        public void flush() {
            writer.flush();
        }
    }
}
