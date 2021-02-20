package chapter2.section2.d.twod.array.manipulation.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/02/21.
 */
public class HaveFunWithMatrices {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int size = FastReader.nextInt();
            int[][] matrix = new int[size][size];

            for (int row = 0; row < matrix.length; row++) {
                String rowData = FastReader.next();
                for (int column = 0; column < rowData.length(); column++) {
                    matrix[row][column] = Integer.parseInt(String.valueOf(rowData.charAt(column)));
                }
            }

            int operations = FastReader.nextInt();
            for (int i = 0; i < operations; i++) {
                String[] operation = FastReader.getLine().split(" ");
                String operationName = operation[0];

                switch (operationName) {
                    case "inc": increment(matrix, 1); break;
                    case "dec": increment(matrix, -1); break;
                    case "transpose": matrix = transpose(matrix); break;
                }

                if (operationName.equals("row") || operationName.equals("col")) {
                    int value1 = Integer.parseInt(operation[1]) - 1;
                    int value2 = Integer.parseInt(operation[2]) - 1;

                    if (operationName.equals("row")) {
                        interchangeRow(matrix, value1, value2);
                    } else {
                        interchangeColumn(matrix, value1, value2);
                    }
                }
            }

            System.out.printf("Case #%d\n", t);
            printMatrix(matrix);
            System.out.println();
        }
    }

    private static void interchangeRow(int[][] matrix, int row1, int row2) {
        int columnsLength = matrix[0].length;
        int[] tempRow = new int[columnsLength];

        for (int column = 0; column < columnsLength; column++) {
            tempRow[column] = matrix[row1][column];
            matrix[row1][column] = matrix[row2][column];
            matrix[row2][column] = tempRow[column];
        }
    }

    private static void interchangeColumn(int[][] matrix, int column1, int column2) {
        int rowsLength = matrix.length;
        int[] tempColumn = new int[rowsLength];

        for (int row = 0; row < rowsLength; row++) {
            tempColumn[row] = matrix[row][column1];
            matrix[row][column1] = matrix[row][column2];
            matrix[row][column2] = tempColumn[row];
        }
    }

    private static void increment(int[][] matrix, int value) {
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[0].length; column++) {
                int newValue = matrix[row][column] + value;
                if (newValue > 9) {
                    newValue = 0;
                } else if (newValue < 0) {
                    newValue = 9;
                }
                matrix[row][column] = newValue;
            }
        }
    }

    private static int[][] transpose(int[][] matrix) {
        int[][] transposedMatrix = new int[matrix[0].length][matrix.length];

        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[0].length; column++) {
                transposedMatrix[column][row] = matrix[row][column];
            }
        }
        return transposedMatrix;
    }

    private static void printMatrix(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[0].length; column++) {
                System.out.print(matrix[row][column]);
            }
            System.out.println();
        }
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
