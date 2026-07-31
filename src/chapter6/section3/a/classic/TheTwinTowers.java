package chapter6.section3.a.classic;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/07/2026.
 */
public class TheTwinTowers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int towerHeight1 = FastReader.nextInt();
        int towerHeight2 = FastReader.nextInt();
        int twinTowerId = 1;
        while (towerHeight1 != 0 || towerHeight2 != 0) {
            int[] tower1 = readTiles(towerHeight1);
            int[] tower2 = readTiles(towerHeight2);

            int lcsLength = longestCommonSubsequenceLength(tower1, tower2);
            outputWriter.printLine(String.format("Twin Towers #%d", twinTowerId));
            outputWriter.printLine(String.format("Number of Tiles : %d\n", lcsLength));

            twinTowerId++;
            towerHeight1 = FastReader.nextInt();
            towerHeight2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[] readTiles(int towerHeight) throws IOException {
        int[] sequence = new int[towerHeight];
        for (int i = 0; i < towerHeight; i++) {
            sequence[i] = FastReader.nextInt();
        }
        return sequence;
    }

    private static int longestCommonSubsequenceLength(int[] sequence1, int[] sequence2) {
        int[][] dp = new int[sequence1.length + 1][sequence2.length + 1];

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (sequence1[i - 1] == sequence2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[sequence1.length][sequence2.length];
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