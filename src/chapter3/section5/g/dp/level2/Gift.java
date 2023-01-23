package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/01/23.
 */
public class Gift {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rocks = FastReader.nextInt();
        int targetRock = FastReader.nextInt();

        while (rocks != 0 || targetRock != 0) {
            outputWriter.printLine(canLandOnGift(rocks, targetRock) ? "Let me try!" : "Don't make fun of me!");
            rocks = FastReader.nextInt();
            targetRock = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean canLandOnGift(int rocks, int targetRock) {
        if (targetRock >= 49) {
            // Nice proof of this on https://algorithmist.com/wiki/UVa_10120_-_Gift%3F!
            return true;
        }

        // dp[rock][jumpID] = can reach or not
        boolean[][] dp = new boolean[rocks + 1][50];
        dp[1][1] = true;
        return jump(targetRock, dp, 1, 2);
    }

    private static boolean jump(int targetRock, boolean[][] dp, int currentRock, int jumpID) {
        if (currentRock == targetRock) {
            return true;
        }
        if (currentRock <= 0 || currentRock >= dp.length || jumpID == dp[0].length) {
            return false;
        }
        if (dp[currentRock][jumpID]) {
            return dp[currentRock][jumpID];
        }
        int jumpLength = 2 * jumpID - 1;
        int rockForward = currentRock + jumpLength;
        int rockBackward = currentRock - jumpLength;

        dp[currentRock][jumpID] = jump(targetRock, dp, rockForward, jumpID + 1)
                || jump(targetRock, dp, rockBackward, jumpID + 1);
        return dp[currentRock][jumpID];
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
