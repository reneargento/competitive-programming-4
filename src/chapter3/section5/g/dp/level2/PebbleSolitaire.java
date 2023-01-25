package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/01/23.
 */
public class PebbleSolitaire {

    private static class GameData {
        int gameState;
        int pebbles;

        public GameData(int gameState, int pebbles) {
            this.gameState = gameState;
            this.pebbles = pebbles;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String game = FastReader.getLine();
            int minimumPebbles = computeMinimumPebbles(game);
            outputWriter.printLine(minimumPebbles);
        }
        outputWriter.flush();
    }

    private static int computeMinimumPebbles(String game) {
        int maxStates = (int) Math.pow(2, 13) - 1;
        // dp[state] = minimum number of pebbles possible
        int[] dp = new int[maxStates];
        Arrays.fill(dp, -1);

        GameData gameData = getGameData(game);
        return computeMinimumPebbles(dp, gameData.gameState, gameData.pebbles);
    }

    private static GameData getGameData(String game) {
        int gameState = 0;
        int pebbles = 0;

        for (int i = 0; i < game.length(); i++) {
            if (game.charAt(i) == 'o') {
                gameState |= (1 << i);
                pebbles++;
            }
        }
        return new GameData(gameState, pebbles);
    }

    private static int computeMinimumPebbles(int[] dp, int gameState, int pebbles) {
        if (dp[gameState] != -1) {
            return dp[gameState];
        }
        int minimumPebbles = pebbles;
        int[] nextIndex = { 1, -1 };
        int[] jumpIndex = { 2, -2 };
        int[] startIndex = { 0, 11 };
        int[] endIndex = { 9, 2 };
        int[] increment = { 1, -1 };

        for (int i = 0; i < 2; i++) {
            for (int pebbleIndex = startIndex[i]; pebbleIndex != endIndex[i]; pebbleIndex += increment[i]) {
                if (hasPebble(gameState, pebbleIndex) && hasPebble(gameState, pebbleIndex + nextIndex[i])
                        && !hasPebble(gameState, pebbleIndex + jumpIndex[i]) ) {
                    int nextState = removePebble(gameState, pebbleIndex);
                    nextState = removePebble(nextState, pebbleIndex + nextIndex[i]);
                    nextState = addPebble(nextState, pebbleIndex + jumpIndex[i]);

                    int nextPebbles = computeMinimumPebbles(dp, nextState, pebbles - 1);
                    minimumPebbles = Math.min(minimumPebbles, nextPebbles);
                }
            }
        }
        dp[gameState] = minimumPebbles;
        return dp[gameState];
    }

    private static boolean hasPebble(int gameState, int index) {
        return (gameState & (1 << index)) > 0;
    }

    private static int addPebble(int gameState, int index) {
        return gameState | (1 << index);
    }

    private static int removePebble(int gameState, int index) {
        return gameState ^ (1 << index);
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
