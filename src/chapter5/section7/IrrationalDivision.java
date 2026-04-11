package chapter5.section7;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/04/26.
 */
public class IrrationalDivision {

    private static final int PLAYER_ID = 0;
    private static final int UNCOMPUTED = -9999;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int height = FastReader.nextInt();
        int width = FastReader.nextInt();
        int highestDifference = computeHighestDifference(height, width);
        outputWriter.printLine(highestDifference);
        outputWriter.flush();
    }

    private static int computeHighestDifference(int height, int width) {
        // dp[player id][left column index][bottom row index]
        int[][][] dp = new int[2][width + 1][height + 1];
        for (int[][] values : dp) {
            for (int[] value2 : values) {
                Arrays.fill(value2, UNCOMPUTED);
            }
        }
        return computeHighestDifference(dp, height, width, 0, 0, 0);
    }

    private static int computeHighestDifference(int[][][] dp, int originalHeight, int originalWidth,
                                                int playerId, int leftColumnIndex, int bottomRowIndex) {
        int height = originalHeight - bottomRowIndex;
        int width = originalWidth - leftColumnIndex;

        if (height == 0 || width == 0) {
            return 0;
        }
        if (dp[playerId][leftColumnIndex][bottomRowIndex] != UNCOMPUTED) {
            return dp[playerId][leftColumnIndex][bottomRowIndex];
        }

        int nextPlayerId = playerId ^ 1;
        boolean topCellIsBlack = leftColumnIndex % 2 == 0;
        int highestDifference = -999;
        if (playerId == PLAYER_ID) {
            for (int i = leftColumnIndex + 1; i <= originalWidth; i++) {
                int currentScore = computeCurrentScore(topCellIsBlack, height, i - leftColumnIndex);
                int opponentScore = computeHighestDifference(dp, originalHeight, originalWidth, nextPlayerId, i,
                        bottomRowIndex);
                int difference = currentScore - opponentScore;
                highestDifference = Math.max(highestDifference, difference);
            }
        } else {
            for (int i = bottomRowIndex + 1; i <= originalHeight; i++) {
                int cutDistance = height - i;
                boolean topCellForBottomIsBlack;
                if (cutDistance % 2 == 0) {
                    topCellForBottomIsBlack = topCellIsBlack;
                } else {
                    topCellForBottomIsBlack = !topCellIsBlack;
                }

                int currentScore = computeCurrentScore(topCellForBottomIsBlack, i - bottomRowIndex, width);
                int opponentScore = computeHighestDifference(dp, originalHeight, originalWidth, nextPlayerId,
                        leftColumnIndex, i);
                int difference = currentScore - opponentScore;
                highestDifference = Math.max(highestDifference, difference);
            }
        }
        dp[playerId][leftColumnIndex][bottomRowIndex] = highestDifference;
        return highestDifference;
    }

    private static int computeCurrentScore(boolean topCellIsBlack, int height, int width) {
        boolean hasPositiveScore = (topCellIsBlack && height % 2 == 1 && width % 2 == 1);
        boolean hasNegativeScore = (!topCellIsBlack && height % 2 == 1 && width % 2 == 1);
        if (hasPositiveScore) {
            return 1;
        }
        if (hasNegativeScore) {
            return -1;
        }
        return 0;
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
