package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/04/23.
 */
public class MutantFlatworldExplorers {

    private static class Result {
        int row;
        int column;
        char orientation;
        boolean lost;

        public Result(int row, int column, char orientation, boolean lost) {
            this.row = row;
            this.column = column;
            this.orientation = orientation;
            this.lost = lost;
        }
    }

    private static class Coordinates {
        int row;
        int column;

        public Coordinates(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int columns = FastReader.nextInt() + 1;
        int rows = FastReader.nextInt() + 1;
        boolean[][] grid = new boolean[rows][columns];

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int column = Integer.parseInt(data[0]);
            int row = (grid.length - 1) - Integer.parseInt(data[1]);
            char orientation = data[2].charAt(0);
            String instructions = FastReader.getLine();

            Result result = processRobot(grid, row, column, orientation, instructions);
            outputWriter.print(String.format("%d %d %s", result.column, result.row, result.orientation));
            if (result.lost) {
                outputWriter.print(" LOST");
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result processRobot(boolean[][] grid, int row, int column, char orientation, String instructions) {
        boolean isRobotLost = false;

        for (char instruction : instructions.toCharArray()) {
            if (instruction == 'L' || instruction == 'R') {
                orientation = getNextOrientation(orientation, instruction);
            } else {
                Coordinates nextCoordinates = getNextCoordinates(row, column, orientation);
                int nextRow = nextCoordinates.row;
                int nextColumn = nextCoordinates.column;
                if (nextRow < 0 || nextRow >= grid.length || nextColumn < 0 || nextColumn >= grid[row].length) {
                    if (!grid[row][column]) {
                        grid[row][column] = true;
                        isRobotLost = true;
                        break;
                    }
                } else {
                    row = nextRow;
                    column = nextColumn;
                }
            }
        }
        int adjustedRow = (grid.length - 1) - row;
        return new Result(adjustedRow, column, orientation, isRobotLost);
    }

    private static char getNextOrientation(char orientation, char instruction) {
        switch (orientation) {
            case 'N': return instruction == 'L' ? 'W' : 'E';
            case 'E': return instruction == 'L' ? 'N' : 'S';
            case 'S': return instruction == 'L' ? 'E' : 'W';
            default: return instruction == 'L' ? 'S' : 'N';
        }
    }

    private static Coordinates getNextCoordinates(int row, int column, char orientation) {
        switch (orientation) {
            case 'N': row--; break;
            case 'E': column++; break;
            case 'S': row++; break;
            default: column--;
        }
        return new Coordinates(row, column);
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
