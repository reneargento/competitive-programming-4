package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 20/05/24.
 */
public class HowMany {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int targetColumn = Integer.parseInt(data[0]) * 2;
            int targetPeaks = Integer.parseInt(data[1]);
            int targetHeight = Integer.parseInt(data[2]);

            int numberOfPaths = countPaths(targetHeight, targetColumn, targetPeaks);
            outputWriter.printLine(numberOfPaths);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countPaths(int targetHeight, int targetColumn, int targetPeaks) {
        int[][][] dp = new int[21][41][targetPeaks + 1];
        for (int[][] values1 : dp) {
            for (int[] values2 : values1) {
                Arrays.fill(values2, -1);
            }
        }
        return countPaths(dp, targetHeight, 0, targetColumn, targetPeaks);
    }

    private static int countPaths(int[][][] dp, int targetHeight, int row, int column, int peaksRemaining) {
        if (row < 0 || column < 0 || peaksRemaining < 0 || row == dp.length) {
            return 0;
        }
        if (row == 0 && column == 0 && peaksRemaining == 0) {
            return 1;
        }
        if (dp[row][column][peaksRemaining] != -1) {
            return dp[row][column][peaksRemaining];
        }

        int paths = countPaths(dp, targetHeight, row - 1, column - 1, peaksRemaining);

        if (row + 1 == targetHeight) {
            paths += countPaths(dp, targetHeight, row, column - 2, peaksRemaining - 1);
            paths += countPaths(dp, targetHeight, row + 2, column - 2, peaksRemaining);
        } else {
            paths += countPaths(dp, targetHeight, row + 1, column - 1, peaksRemaining);
        }
        dp[row][column][peaksRemaining] = paths;
        return dp[row][column][peaksRemaining];
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
