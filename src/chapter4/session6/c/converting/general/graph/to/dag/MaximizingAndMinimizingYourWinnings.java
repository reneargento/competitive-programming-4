package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 02/06/24.
 */
public class MaximizingAndMinimizingYourWinnings {

    private static class Result {
        int maxWin;
        int minWin;

        public Result(int maxWin, int minWin) {
            this.maxWin = maxWin;
            this.minWin = minWin;
        }
    }

    private static final int NEG_INFINITE = -1000000000;
    private static final int POS_INFINITE = 1000000000;

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rooms = inputReader.nextInt();

        while (rooms != 0) {
            int[][] moneyReceived = new int[rooms][rooms];
            for (int row = 0; row < moneyReceived.length; row++) {
                for (int column = 0; column < moneyReceived[0].length; column++) {
                    moneyReceived[row][column] = inputReader.nextInt();
                }
            }
            int turns = inputReader.nextInt();

            Result result = playGame(moneyReceived, turns);
            outputWriter.printLine(String.format("%d %d", result.maxWin, result.minWin));
            rooms = inputReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result playGame(int[][] moneyReceived, int turns) {
        // dp[room id][turns left]
        Result[][] dp = new Result[moneyReceived.length][turns + 1];
        return playGame(moneyReceived, dp, 0, turns);
    }

    private static Result playGame(int[][] moneyReceived, Result[][] dp, int roomId, int turnsLeft) {
        if (turnsLeft == 0) {
            return new Result(0, 0);
        }
        if (dp[roomId][turnsLeft] != null) {
            return dp[roomId][turnsLeft];
        }

        int maxWin = NEG_INFINITE;
        int minWin = POS_INFINITE;

        for (int nextRoomId = 0; nextRoomId < moneyReceived.length; nextRoomId++) {
            Result nextResult = playGame(moneyReceived, dp, nextRoomId, turnsLeft - 1);
            if (nextResult.maxWin != NEG_INFINITE) {
                int maxWinCandidate = moneyReceived[roomId][nextRoomId] + nextResult.maxWin;
                maxWin = Math.max(maxWin, maxWinCandidate);
            }
            if (nextResult.minWin != POS_INFINITE) {
                int minWinCandidate = moneyReceived[roomId][nextRoomId] + nextResult.minWin;
                minWin = Math.min(minWin, minWinCandidate);
            }
        }
        dp[roomId][turnsLeft] = new Result(maxWin, minWin);
        return dp[roomId][turnsLeft];
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
