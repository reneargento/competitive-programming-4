package chapter5.section7;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/04/26.
 */
public class GameOfEuler {

    private static final int[] neighborRows = { -1, 1, 0, 0 };
    private static final int[] neighborColumns = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int maxIndex = (1 << 16);
        Boolean[] dp = new Boolean[maxIndex];

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            int bitmask = 0;

            for (int row = 0; row < 4; row++) {
                String columns = FastReader.getLine();
                for (int column = 0; column < columns.length(); column++) {
                    if (columns.charAt(column) == 'X') {
                        int cellId = (row * 4) + column;
                        bitmask |= (1 << cellId);
                    }
                }
            }

            boolean isWinningPosition = isWinningPosition(dp, bitmask);
            outputWriter.printLine(isWinningPosition ? "WINNING" : "LOSING");
        }
        outputWriter.flush();
    }

    private static boolean isWinningPosition(Boolean[] dp, int bitmask) {
        if (bitmask == (1 << 16) - 1) {
            return true;
        }
        if (dp[bitmask] != null) {
            return dp[bitmask];
        }
        boolean solved = false;

        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4 && !solved; column++) {
                int index = (row * 4) + column;
                if ((bitmask & (1 << index)) != 0) {
                    continue;
                }

                int nextBitmask = bitmask | (1 << index);
                solved = !isWinningPosition(dp, nextBitmask);
                if (solved) {
                    break;
                }

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row;
                    int neighborColumn = column;
                    boolean valid = true;
                    int longPinBitmask = bitmask;

                    while (isValid(neighborRow, neighborColumn)) {
                        int neighborIndex = (neighborRow * 4) + neighborColumn;
                        if ((bitmask & (1 << neighborIndex)) != 0) {
                            valid = false;
                            break;
                        }
                        int pinSizeRow = Math.abs(row - neighborRow);
                        int pinSizeColumn = Math.abs(column - neighborColumn);
                        if (pinSizeRow >= 3 || pinSizeColumn >= 3) {
                            valid = false;
                            break;
                        }

                        longPinBitmask |= (1 << neighborIndex);
                        neighborRow += neighborRows[i];
                        neighborColumn += neighborColumns[i];
                    }

                    if (valid) {
                        solved = !isWinningPosition(dp, longPinBitmask);
                        if (solved) {
                            break;
                        }
                    }
                }
            }
            if (solved) {
                break;
            }
        }
        dp[bitmask] = solved;
        return solved;
    }

    private static boolean isValid(int row, int column) {
        return row >= 0 && row < 4 && column >= 0 && column < 4;
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
