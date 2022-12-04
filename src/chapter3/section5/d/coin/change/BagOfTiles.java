package chapter3.section5.d.coin.change;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/12/22.
 */
public class BagOfTiles {

    private static class Result {
        long winDraws;
        long loseDraws;

        public Result(long winDraws, long loseDraws) {
            this.winDraws = winDraws;
            this.loseDraws = loseDraws;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int games = FastReader.nextInt();

        for (int game = 1; game <= games; game++) {
            int[] tiles = new int[FastReader.nextInt()];
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] = FastReader.nextInt();
            }
            int numbersToChoose = FastReader.nextInt();
            int target = FastReader.nextInt();

            Result gameOdds = computeGameOdds(tiles, numbersToChoose, target);
            outputWriter.printLine(String.format("Game %d -- %s : %s", game, gameOdds.winDraws, gameOdds.loseDraws));
        }
        outputWriter.flush();
    }

    private static Result computeGameOdds(int[] tiles, int numbersToChoose, int target) {
        // dp[tiles checked][number of tiles remaining to choose][sum] = number of ways
        long[][][] dp = new long[tiles.length][numbersToChoose + 1][target + 1];
        for (long[][] tilesToChoose : dp) {
            for (long[] values : tilesToChoose) {
                Arrays.fill(values, -1);
            }
        }
        long winDraws = computeGameOdds(tiles, dp, 0, numbersToChoose, target);

        long totalDraws = binomialCoefficient(tiles.length, numbersToChoose);
        long loseDraws = totalDraws - winDraws;
        return new Result(winDraws, loseDraws);
    }

    private static long computeGameOdds(int[] tiles, long[][][] dp, int tileId, int tilesToChoose, int sum) {
        if (sum == 0) {
            if (tilesToChoose == 0) {
                return 1;
            }
            return 0;
        }
        if (tileId == dp.length || sum < 0 || tilesToChoose == 0) {
            return 0;
        }

        if (dp[tileId][tilesToChoose][sum] != -1) {
            return dp[tileId][tilesToChoose][sum];
        }

        long waysWithoutTile = computeGameOdds(tiles, dp, tileId + 1, tilesToChoose, sum);
        long waysWithTile = computeGameOdds(tiles, dp, tileId + 1, tilesToChoose - 1, sum - tiles[tileId]);
        dp[tileId][tilesToChoose][sum] = waysWithoutTile + waysWithTile;
        return dp[tileId][tilesToChoose][sum];
    }

    // N choose K
    private static long binomialCoefficient(int totalLength, int numbersToChoose) {
        long result = 1;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result * (totalLength - i) / (i + 1);
        }
        return result;
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
