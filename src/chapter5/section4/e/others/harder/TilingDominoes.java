package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 11/01/26.
 */
public class TilingDominoes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int rows = Integer.parseInt(data[0]);
            int columns = Integer.parseInt(data[1]);

            long numberOfTilings = computeNumberOfTilings(rows, columns);
            outputWriter.printLine(numberOfTilings);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeNumberOfTilings(int rows, int columns) {
        int minDimension = Math.min(rows, columns);
        int maxDimension = Math.max(rows, columns);
        int totalStates = 1 << minDimension;

        // dp[current and previous state][bitmap with bit 0 = no tiling, 1 = tiling] = total number of ways
        long[][] dp = new long[2][totalStates];
        int current = 0;

        dp[0][totalStates - 1] = 1;
        for (int i = 0; i < maxDimension; i++) {
            for (int j = 0; j < minDimension; j++) {
                current ^= 1;
                Arrays.fill(dp[current], 0);

                for (int state = 0; state < totalStates; state++) {
                    int aboveRowIndex = 1 << j;
                    int leftColumnIndex = 1 << (j - 1);
                    boolean hasUpDomino = ((state & aboveRowIndex) > 0);
                    boolean hasLeftDomino = ((state & leftColumnIndex) > 0);

                    if (hasUpDomino || i != 0) {
                        int newState = state ^ aboveRowIndex;
                        dp[current][newState] += dp[current ^ 1][state];
                    }
                    if (!hasLeftDomino && hasUpDomino && j != 0) {
                        int newState = state ^ leftColumnIndex;
                        dp[current][newState] += dp[current ^ 1][state];
                    }
                }
            }
        }
        return dp[current][totalStates - 1];
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
