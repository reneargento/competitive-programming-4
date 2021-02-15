package chapter2.section2.b.oned.array.manipulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/02/21.
 */
public class PipeRotation {

    private enum Direction {
        LEFT, RIGHT, TOP, BOTTOM
    }

    private static class Cell {
        char type;
        boolean connectedTop;
        boolean connectedBottom;
        boolean connectedLeft;
        boolean connectedRight;
        int connections;

        public Cell(char type) {
            this.type = type;
        }

        private void connect(Direction direction) {
            connections++;
            switch (direction) {
                case TOP: connectedTop = true; break;
                case BOTTOM: connectedBottom = true; break;
                case LEFT: connectedLeft = true; break;
                case RIGHT: connectedRight = true;
            }
        }

        private boolean canConnect(Direction direction) {
            if (type == 'A') {
                return false;
            }

            switch (direction) {
                case LEFT:
                    if (type == 'B') {
                        return !connectedLeft && !connectedTop && !connectedBottom;
                    } else if (type == 'C') {
                        return connections < 2 && !connectedLeft && !connectedRight;
                    } else {
                        return !connectedLeft;
                    }
                case RIGHT:
                    if (type == 'B') {
                        return !connectedRight && !connectedTop && !connectedBottom;
                    } else if (type == 'C') {
                        return connections < 2 && !connectedLeft && !connectedRight;
                    } else {
                        return !connectedRight;
                    }
                case TOP:
                    if (type == 'B') {
                        return !connectedTop && !connectedLeft && !connectedRight;
                    } else if (type == 'C') {
                        return connections < 2 && !connectedTop && !connectedBottom;
                    } else {
                        return !connectedTop;
                    }
                default:
                    if (type == 'B') {
                        return !connectedBottom && !connectedLeft && !connectedRight;
                    } else if (type == 'C') {
                        return connections < 2 && !connectedTop && !connectedBottom;
                    } else {
                        return !connectedBottom;
                    }
            }
        }

        private boolean isConnected() {
            switch (type) {
                case 'B':
                case 'C':
                    return connections == 2;
                case 'D': return connections == 4;
                default: return connections == 0;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        Cell[][] grid = new Cell[rows][columns];

        for (int row = 0; row < grid.length; row++) {
            String rowData = FastReader.next();
            for (int column = 0; column < grid[0].length; column++) {
                grid[row][column] = new Cell(rowData.charAt(column));
            }
        }

        boolean possible = areValidPipes(grid);
        System.out.println(possible ? "Possible" : "Impossible");
    }

    private static boolean areValidPipes(Cell[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                int rightColumn = column + 1;
                int bottomRow = row + 1;
                connectPipe(grid, row, column, rightColumn, bottomRow);
            }
        }
        return areAllPipesValid(grid);
    }

    private static void connectPipe(Cell[][] grid, int row, int column, int rightColumn, int bottomRow) {
        Cell cell = grid[row][column];

        if (isValid(grid, row, rightColumn) && grid[row][rightColumn].canConnect(Direction.LEFT)
                && cell.canConnect(Direction.RIGHT)) {
            grid[row][rightColumn].connect(Direction.LEFT);
            cell.connect(Direction.RIGHT);
        }
        if (isValid(grid, bottomRow, column) && grid[bottomRow][column].canConnect(Direction.TOP)
                && cell.canConnect(Direction.BOTTOM)) {
            grid[bottomRow][column].connect(Direction.TOP);
            cell.connect(Direction.BOTTOM);
        }
    }

    private static boolean areAllPipesValid(Cell[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (!grid[row][column].isConnected()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(Cell[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
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
