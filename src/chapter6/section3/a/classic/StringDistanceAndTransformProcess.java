package chapter6.section3.a.classic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 28/07/2026.
 */
public class StringDistanceAndTransformProcess {

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

        String string1 = FastReader.getLine();
        int caseId = 1;
        while (string1 != null) {
            String string2 = FastReader.getLine();
            List<String> transformList = stringAlignment(string1, string2);

            if (caseId > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(transformList.size());
            for (String transform : transformList) {
                outputWriter.printLine(transform);
            }

            caseId++;
            string1 = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> stringAlignment(String string1, String string2) {
        Cell[][] previous = new Cell[string1.length() + 1][string2.length() + 1];
        int[][] dp = computeDpTable(string1, string2, previous);

        StringBuilder optimalAlignmentA = new StringBuilder();
        StringBuilder optimalAlignmentB = new StringBuilder();

        int row = dp.length - 1;
        int column = dp[0].length - 1;
        while (row > 0 || column > 0) {
            if (row == 0) {
                optimalAlignmentA.append("_");
                optimalAlignmentB.append(string2.charAt(column - 1));
                column--;
                continue;
            }
            if (column == 0) {
                optimalAlignmentA.append(string1.charAt(row - 1));
                optimalAlignmentB.append("_");
                row--;
                continue;
            }

            Cell previousCell = previous[row][column];
            if (previousCell.row == row - 1 && previousCell.column == column - 1) {
                optimalAlignmentA.append(string1.charAt(row - 1));
                optimalAlignmentB.append(string2.charAt(column - 1));
                row--;
                column--;
            } else if (previousCell.row == row - 1) {
                optimalAlignmentA.append(string1.charAt(row - 1));
                optimalAlignmentB.append("_");
                row--;
            } else {
                optimalAlignmentA.append("_");
                optimalAlignmentB.append(string2.charAt(column - 1));
                column--;
            }
        }
        return computeTransformList(optimalAlignmentA.reverse().toString(), optimalAlignmentB.reverse().toString());
    }

    private static int[][] computeDpTable(String string1, String string2, Cell[][] previous) {
        int[][] dp = new int[string1.length() + 1][string2.length() + 1];

        // Base cases
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j < dp[0].length; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                // Match: 2 points, mismatch: -1 point
                int score1 = dp[i - 1][j - 1] + (string1.charAt(i - 1) == string2.charAt(j - 1) ? 0 : 1);
                int score2 = dp[i - 1][j] + 1;
                int score3 = dp[i][j - 1] + 1;

                if (score1 <= score2 && score1 <= score3) {
                    dp[i][j] = score1;
                    previous[i][j] = new Cell(i - 1, j - 1);
                } else if (score2 <= score1 && score2 <= score3) {
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

    private static List<String> computeTransformList(String optimalAlignmentA, String optimalAlignmentB) {
        List<String> commands = new ArrayList<>();
        int deletedChars = 0;
        int commandCount = 1;

        for (int i = 0; i < optimalAlignmentA.length(); i++) {
            int index = (i + 1) - deletedChars;
            if (optimalAlignmentA.charAt(i) == '_') {
                String command = commandCount + " Insert " + index + "," + optimalAlignmentB.charAt(i);
                commands.add(command);
                commandCount++;
            } else if (optimalAlignmentB.charAt(i) == '_') {
                String command = commandCount + " Delete " + index;
                commands.add(command);
                deletedChars++;
                commandCount++;
            } else if (optimalAlignmentA.charAt(i) != optimalAlignmentB.charAt(i)) {
                String command = commandCount + " Replace " + index + "," + optimalAlignmentB.charAt(i);
                commands.add(command);
                commandCount++;
            }
        }
        return commands;
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