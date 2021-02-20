package chapter2.section2.d.twod.array.manipulation.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/02/21.
 */
public class SpreadsheetTracking {

    private static class Cell {
        int row;
        int column;
        boolean deleted;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int spreadSheetNumber = 1;

        while (rows != 0 || columns != 0) {
            if (spreadSheetNumber > 1) {
                System.out.println();
            }

            Cell[][] spreadsheet = createSpreadsheet(rows, columns);
            Cell[][] spreadsheetCopy = copySpreadsheet(spreadsheet);

            int operations = FastReader.nextInt();

            for (int i = 0; i < operations; i++) {
                String operation = FastReader.next();
                if (operation.equals("EX")) {
                    int row1 = FastReader.nextInt() - 1;
                    int column1 = FastReader.nextInt() - 1;
                    int row2 = FastReader.nextInt() - 1;
                    int column2 = FastReader.nextInt() - 1;
                    exchange(spreadsheetCopy, row1, column1, row2, column2);
                } else {
                    int labelsNumber = FastReader.nextInt();
                    List<Integer> labels = new ArrayList<>();
                    for (int l = 0; l < labelsNumber; l++) {
                        int label = FastReader.nextInt() - 1;
                        labels.add(label);
                    }

                    switch (operation) {
                        case "DC": spreadsheetCopy = delete(spreadsheetCopy, labels, false); break;
                        case "DR": spreadsheetCopy = delete(spreadsheetCopy, labels, true); break;
                        case "IC": spreadsheetCopy = insert(spreadsheetCopy, labels, false); break;
                        case "IR": spreadsheetCopy = insert(spreadsheetCopy, labels, true); break;
                    }
                }
            }

            System.out.printf("Spreadsheet #%d\n", spreadSheetNumber);
            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int row = FastReader.nextInt() - 1;
                int column = FastReader.nextInt() - 1;

                System.out.printf("Cell data in (%d,%d) ", row + 1, column + 1);
                if (spreadsheet[row][column].deleted) {
                    System.out.println("GONE");
                } else {
                    Cell cell = spreadsheet[row][column];
                    System.out.printf("moved to (%d,%d)\n", cell.row + 1, cell.column + 1);
                }
            }

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
            spreadSheetNumber++;
        }
    }

    private static Cell[][] createSpreadsheet(int rows, int columns) {
        Cell[][] spreadsheet = new Cell[rows][columns];
        for (int row = 0; row < spreadsheet.length; row++) {
            for (int column = 0; column < spreadsheet[0].length; column++) {
                spreadsheet[row][column] = new Cell(row, column);
            }
        }
        return spreadsheet;
    }

    private static Cell[][] copySpreadsheet(Cell[][] spreadsheet) {
        Cell[][] spreadsheetCopy = new Cell[spreadsheet.length][spreadsheet[0].length];
        for (int row = 0; row < spreadsheet.length; row++) {
            for (int column = 0; column < spreadsheet[0].length; column++) {
                spreadsheetCopy[row][column] = spreadsheet[row][column];
            }
        }
        return spreadsheetCopy;
    }

    private static void exchange(Cell[][] spreadsheetCopy, int row1, int column1, int row2, int column2) {
        Cell temp = spreadsheetCopy[row1][column1];

        spreadsheetCopy[row1][column1] = spreadsheetCopy[row2][column2];
        if (spreadsheetCopy[row1][column1] != null) {
            spreadsheetCopy[row1][column1].row = row1;
            spreadsheetCopy[row1][column1].column = column1;
        }

        spreadsheetCopy[row2][column2] = temp;
        if (spreadsheetCopy[row2][column2] != null) {
            spreadsheetCopy[row2][column2].row = row2;
            spreadsheetCopy[row2][column2].column = column2;
        }
    }

    private static Cell[][] delete(Cell[][] spreadsheetCopy, List<Integer> labels, boolean deleteRows) {
        Cell[][] newSpreadsheet;

        if (deleteRows) {
            int newRowsLength = spreadsheetCopy.length - labels.size();
            int newColumnsLength = spreadsheetCopy[0].length;
            newSpreadsheet = new Cell[newRowsLength][newColumnsLength];

            for (int row = 0, newRow = 0; row < spreadsheetCopy.length; row++) {
                boolean deleted = labels.contains(row);
                for (int column = 0; column < spreadsheetCopy[0].length; column++) {
                    if (deleted) {
                        if (spreadsheetCopy[row][column] != null) {
                            spreadsheetCopy[row][column].deleted = true;
                        }
                    } else {
                        newSpreadsheet[newRow][column] = spreadsheetCopy[row][column];
                        if (spreadsheetCopy[row][column] != null) {
                            spreadsheetCopy[row][column].row = newRow;
                        }
                    }
                }

                if (!deleted) {
                    newRow++;
                }
            }
        } else {
            int newRowsLength = spreadsheetCopy.length;
            int newColumnsLength = spreadsheetCopy[0].length - labels.size();
            newSpreadsheet = new Cell[newRowsLength][newColumnsLength];

            for (int row = 0; row < spreadsheetCopy.length; row++) {
                for (int column = 0, newColumn = 0; column < spreadsheetCopy[0].length; column++) {
                    if (labels.contains(column)) {
                        if (spreadsheetCopy[row][column] != null) {
                            spreadsheetCopy[row][column].deleted = true;
                        }
                    } else {
                        newSpreadsheet[row][newColumn] = spreadsheetCopy[row][column];
                        if (spreadsheetCopy[row][column] != null) {
                            spreadsheetCopy[row][column].column = newColumn;
                        }
                        newColumn++;
                    }
                }
            }
        }
        return newSpreadsheet;
    }

    private static Cell[][] insert(Cell[][] spreadsheetCopy, List<Integer> labels, boolean insertRows) {
        int newRowsLength;
        int newColumnsLength;

        if (insertRows) {
            newRowsLength = spreadsheetCopy.length + labels.size();
            newColumnsLength = spreadsheetCopy[0].length;
        } else {
            newRowsLength = spreadsheetCopy.length;
            newColumnsLength = spreadsheetCopy[0].length + labels.size();
        }

        Cell[][] newSpreadsheet = new Cell[newRowsLength][newColumnsLength];
        for (int row = 0, newRow = 0; row < spreadsheetCopy.length; row++, newRow++) {
            if (insertRows && labels.contains(row)) {
                newRow++;
            }
            for (int column = 0, newColumn = 0; column < spreadsheetCopy[0].length; column++, newColumn++) {
                if (!insertRows && labels.contains(column)) {
                    newColumn++;
                }
                newSpreadsheet[newRow][newColumn] = spreadsheetCopy[row][column];
                if (spreadsheetCopy[row][column] != null) {
                    spreadsheetCopy[row][column].row = newRow;
                    spreadsheetCopy[row][column].column = newColumn;
                }
            }
        }
        return newSpreadsheet;
    }

    private static class FastReader {
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
    }
}
