package chapter5.section7;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/04/26.
 */
public class Stones {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int stones = FastReader.nextInt();
        Boolean[][] dp = new Boolean[1001][1001];

        while (stones != 0) {
            int initialMove = stones / 2;
            if (stones % 2 == 0) {
                initialMove--;
            }
            outputWriter.printLine(canWin(stones, initialMove, dp) ? "Alicia" : "Roberto");
            stones = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean canWin(int stones, int previousMove, Boolean[][] dp) {
        if (stones == 0) {
            return false;
        }
        if (dp[stones][previousMove] != null) {
            return dp[stones][previousMove];
        }

        boolean canWin = false;
        int maxStones = Math.min(previousMove * 2, stones);
        for (int move = 1; move <= maxStones; move++) {
            if (!canWin(stones - move, move, dp)) {
                canWin = true;
                break;
            }
        }
        dp[stones][previousMove] = canWin;
        return canWin;
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
