package chapter6.section3.a.classic;

import java.io.*;

/**
 * Created by Rene Argento on 27/07/2026.
 */
public class StringComputer {

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

        String line = FastReader.getLine();
        while (!line.equals("#")) {
            String[] data = line.split(" ");
            String program = buildX9091Program(data[0], data[1]);
            outputWriter.printLine(program);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String buildX9091Program(String string, String target) {
        Cell[][] previous = new Cell[string.length() + 1][target.length() + 1];
        int[][] dp = computeDpTable(string, target, previous);

        StringBuilder optimalAlignmentA = new StringBuilder();
        StringBuilder optimalAlignmentB = new StringBuilder();

        int row = dp.length - 1;
        int column = dp[0].length - 1;
        while (row > 0 || column > 0) {
            if (row == 0) {
                optimalAlignmentA.append("_");
                optimalAlignmentB.append(target.charAt(column - 1));
                column--;
                continue;
            }
            if (column == 0) {
                optimalAlignmentA.append(string.charAt(row - 1));
                optimalAlignmentB.append("_");
                row--;
                continue;
            }

            Cell previousCell = previous[row][column];
            if (previousCell.row == row - 1 && previousCell.column == column - 1) {
                optimalAlignmentA.append(string.charAt(row - 1));
                optimalAlignmentB.append(target.charAt(column - 1));
                row--;
                column--;
            } else if (previousCell.row == row - 1) {
                optimalAlignmentA.append(string.charAt(row - 1));
                optimalAlignmentB.append("_");
                row--;
            } else {
                optimalAlignmentA.append("_");
                optimalAlignmentB.append(target.charAt(column - 1));
                column--;
            }
        }
        return buildProgram(optimalAlignmentA.reverse().toString(), optimalAlignmentB.reverse().toString());
    }

    private static int[][] computeDpTable(String string1, String string2, Cell[][] previous) {
        int[][] dp = new int[string1.length() + 1][string2.length() + 1];

        // Base cases
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = i * -1;
        }
        for (int j = 1; j < dp[0].length; j++) {
            dp[0][j] = j * -1;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                // Match: 2 points, mismatch: -1 point
                int score1 = dp[i - 1][j - 1] + (string1.charAt(i - 1) == string2.charAt(j - 1) ? 2 : -1);
                int score2 = dp[i - 1][j] - 1;
                int score3 = dp[i][j - 1] - 1;

                if (score1 >= score2 && score1 >= score3) {
                    dp[i][j] = score1;
                    previous[i][j] = new Cell(i - 1, j - 1);
                } else if (score2 >= score1 && score2 >= score3) {
                    dp[i][j] = score2;
                    previous[i][j] = new Cell(i - 1, j);
                } else {
                    dp[i][j] = score3;
                    previous[i][j] = new Cell(i, j - 1);
                }
            }
        }
        return dp;
    }

    private static String buildProgram(String optimalAlignmentA, String optimalAlignmentB) {
        StringBuilder program = new StringBuilder();
        int deletedChars = 0;
        for (int i = 0; i < optimalAlignmentA.length(); i++) {
            String resultIndex = getResultIndex(i + 1 - deletedChars);
            if (optimalAlignmentA.charAt(i) == '_') {
                String instruction = "I" + optimalAlignmentB.charAt(i) + resultIndex;
                program.append(instruction);
            } else if (optimalAlignmentB.charAt(i) == '_') {
                String instruction = "D" + optimalAlignmentA.charAt(i) + resultIndex;
                program.append(instruction);
                deletedChars++;
            } else if (optimalAlignmentA.charAt(i) != optimalAlignmentB.charAt(i)) {
                String instruction = "C" + optimalAlignmentB.charAt(i) + resultIndex;
                program.append(instruction);
            }
        }
        return program + "E";
    }

    private static String getResultIndex(int index) {
        if (index <= 9) {
            return "0" + index;
        }
        return String.valueOf(index);
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