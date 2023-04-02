package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/03/23.
 */
public class RobotMotion {

    private static class Result {
        int steps;
        int loopSteps;

        public Result(int steps, int loopSteps) {
            this.steps = steps;
            this.loopSteps = loopSteps;
        }
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
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int startColumn = FastReader.nextInt();

        while (rows != 0 || columns != 0 || startColumn != 0) {
            char[][] grid = new char[rows][columns];
            for (int row = 0; row < grid.length; row++) {
                grid[row] = FastReader.next().toCharArray();
            }

            Result steps = computeSteps(grid, startColumn - 1);
            if (steps.loopSteps == 0) {
                outputWriter.printLine(String.format("%d step(s) to exit", steps.steps));
            } else {
                outputWriter.printLine(String.format("%d step(s) before a loop of %d step(s)", steps.steps,
                        steps.loopSteps));
            }
            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
            startColumn = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeSteps(char[][] grid, int startColumn) {
        int[][] stepNumber = new int[grid.length][grid[0].length];
        int steps;
        int loopSteps = 0;
        int currentStep = 1;

        int row = 0;
        int column = startColumn;
        stepNumber[row][column] = currentStep;

        while (true) {
            char direction = grid[row][column];
            int neighborRow = row;
            int neighborColumn = column;

            switch (direction) {
                case 'N': neighborRow = neighborRow - 1; break;
                case 'S': neighborRow = neighborRow + 1; break;
                case 'E': neighborColumn = neighborColumn + 1; break;
                case 'W': neighborColumn = neighborColumn - 1;
            }

            if (!isValid(grid, neighborRow, neighborColumn)) {
                steps = currentStep;
                break;
            }
            if (stepNumber[neighborRow][neighborColumn] != 0) {
                steps = stepNumber[neighborRow][neighborColumn] - 1;
                loopSteps = currentStep - stepNumber[neighborRow][neighborColumn] + 1;
                break;
            }

            currentStep++;
            stepNumber[neighborRow][neighborColumn] = currentStep;
            row = neighborRow;
            column = neighborColumn;
        }
        return new Result(steps, loopSteps);
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
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
