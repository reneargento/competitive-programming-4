package chapter1.section6.p.time.waster.problems.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 26/12/20.
 */
public class Shark {

    private enum Type {
        SARDINE, MACKEREL, SALMON, GROUPER, TURTLE, DOLPHIN, WHALE, SHARK;
    }

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
            }

            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            char[][] photo = new char[rows][columns];

            for (int row = 0; row < rows; row++) {
                photo[row] = FastReader.getLine().toCharArray();
            }
            int[] animalFrequency = computeAnimalFrequency(photo);
            printAnimalFrequency(animalFrequency);
        }
    }

    private static int[] computeAnimalFrequency(char[][] photo) {
        int[] animalFrequency = new int[8];
        boolean[][] visited = new boolean[photo.length][photo[0].length];
        int[] neighborRows = {-1, 0, 0, 1};
        int[] neighborColumns = {0, -1, 1, 0};

        for (int row = 0; row < photo.length; row++) {
            for (int column = 0; column < photo[0].length; column++) {
                if (photo[row][column] != '.' && !visited[row][column]) {
                    Type animalType = floodFill(photo, visited, row, column, neighborRows, neighborColumns);
                    switch (animalType) {
                        case SARDINE: animalFrequency[0]++; break;
                        case MACKEREL: animalFrequency[1]++; break;
                        case SALMON: animalFrequency[2]++; break;
                        case GROUPER: animalFrequency[3]++; break;
                        case TURTLE: animalFrequency[4]++; break;
                        case DOLPHIN: animalFrequency[5]++; break;
                        case WHALE: animalFrequency[6]++; break;
                        case SHARK: animalFrequency[7]++; break;
                    }
                }
            }
        }
        return animalFrequency;
    }

    private static Type floodFill(char[][] photo, boolean[][] visited, int row, int column, int[] neighborRows,
                                  int[] neighborColumns) {
        Set<Integer> rows = new HashSet<>();
        Set<Integer> columns = new HashSet<>();
        boolean hasVerticalTailFin = false;

        visited[row][column] = true;
        char animalSymbol = photo[row][column];

        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(row, column));

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            row = cell.row;
            column = cell.column;

            rows.add(row);
            columns.add(column);

            if (hasVerticalTailFin(photo, row, column)) {
                hasVerticalTailFin = true;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(photo, neighborRow, neighborColumn)
                        && photo[neighborRow][neighborColumn] == animalSymbol
                        && !visited[neighborRow][neighborColumn]) {
                    queue.add(new Cell(neighborRow, neighborColumn));
                    visited[neighborRow][neighborColumn] = true;
                }
            }
        }

        return computeAnimalType(rows, columns, hasVerticalTailFin);
    }

    private static boolean hasVerticalTailFin(char[][] photo, int row, int column) {
        int topRow = row - 1;
        int bottomRow = row + 1;
        int leftColumn = column - 1;
        int rightColumn = column + 1;

        return (isValid(photo, topRow, column)
                && isValid(photo, bottomRow, column)
                && photo[topRow][column] == '.'
                && photo[bottomRow][column] == '.') ||
               (isValid(photo, row, leftColumn)
                && isValid(photo, row, rightColumn)
                && photo[row][leftColumn] == '.'
                && photo[row][rightColumn] == '.');
    }

    private static boolean isValid(char[][] photo, int row, int column) {
        return row >= 0 && row < photo.length && column >= 0 && column < photo[0].length;
    }

    private static Type computeAnimalType(Set<Integer> rows, Set<Integer> columns, boolean hasVerticalTailFin) {
        int width = Math.min(rows.size(), columns.size());
        int length = Math.max(rows.size(), columns.size());

        if (width == 1 && length == 1) {
            return Type.SARDINE;
        } else if (width == 1 && length == 2) {
            return Type.MACKEREL;
        } else if (width == 1) {
            return Type.SALMON;
        } else if (width == length) {
            return Type.TURTLE;
        } else if (width == 2) {
            return Type.GROUPER;
        } else if (width == 3 && !hasVerticalTailFin) {
            return Type.DOLPHIN;
        } else if (width == 4) {
            return Type.WHALE;
        } else {
            return Type.SHARK;
        }
    }

    private static void printAnimalFrequency(int[] animalFrequency) {
        StringJoiner description = new StringJoiner(" ");
        for (int frequency : animalFrequency) {
            description.add(String.valueOf(frequency));
        }
        System.out.println(description);
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
