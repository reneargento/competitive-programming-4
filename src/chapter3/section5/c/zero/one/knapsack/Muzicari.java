package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/11/22.
 */
public class Muzicari {

    private static final int INFINITE = 5001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int concertLength = FastReader.nextInt();
        int[] breakLengths = new int[FastReader.nextInt()];

        for (int i = 0; i < breakLengths.length; i++) {
            breakLengths[i] = FastReader.nextInt();
        }
        int[] breakTimes = computeBreakTimes(breakLengths, concertLength);
        outputWriter.print(breakTimes[0]);
        for (int i = 1; i < breakTimes.length; i++) {
            outputWriter.print(" " + breakTimes[i]);
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static int[] computeBreakTimes(int[] breakLengths, int concertLength) {
        // dp[musicians checked until index][time possible to assign to spot 1] = minimum time used for spot 2
        int[][] dp = new int[breakLengths.length][concertLength + 1];
        for (int[] timeLeft : dp) {
            Arrays.fill(timeLeft, -1);
        }
        int minimumTime = computeDp(breakLengths, dp, 0, concertLength);

        int[] breakTimes = new int[breakLengths.length];
        computeBreakTimes(breakLengths, dp, breakTimes, 0, concertLength, minimumTime, 0, 0);
        return breakTimes;
    }

    private static int computeDp(int[] breakLengths, int[][] dp, int musicianId, int timeLeft) {
        if (timeLeft < 0) {
            return INFINITE;
        }
        if (musicianId == dp.length) {
            return 0;
        }
        if (dp[musicianId][timeLeft] != -1) {
            return dp[musicianId][timeLeft];
        }

        int minimumTimeWithoutCurrent = computeDp(breakLengths, dp, musicianId + 1, timeLeft) +
                breakLengths[musicianId];
        if (timeLeft < breakLengths[musicianId]) {
            dp[musicianId][timeLeft] = minimumTimeWithoutCurrent;
            return dp[musicianId][timeLeft];
        }

        int minimumTimeWithCurrent = computeDp(breakLengths, dp, musicianId + 1,
                timeLeft - breakLengths[musicianId]);
        dp[musicianId][timeLeft] = Math.min(minimumTimeWithoutCurrent, minimumTimeWithCurrent);
        return dp[musicianId][timeLeft];
    }

    private static void computeBreakTimes(int[] breakLengths, int[][] dp, int[] breakTimes, int musicianId, int timeLeft,
                                          int minimumTime, int spot1Time, int spot2Time) {
        if (musicianId == breakLengths.length) {
            return;
        }

        if (timeLeft < breakLengths[musicianId]) {
            // Cannot take spot 1, so take spot 2
            breakTimes[musicianId] = spot2Time;
            spot2Time += breakLengths[musicianId];
            minimumTime -= breakLengths[musicianId];
            computeBreakTimes(breakLengths, dp, breakTimes, musicianId + 1, timeLeft, minimumTime, spot1Time, spot2Time);
        } else {
            if (minimumTime == computeDp(breakLengths, dp, musicianId + 1,
                    timeLeft - breakLengths[musicianId])) {
                // Take spot 1
                breakTimes[musicianId] = spot1Time;
                spot1Time += breakLengths[musicianId];
                computeBreakTimes(breakLengths, dp, breakTimes, musicianId + 1,
                        timeLeft - breakLengths[musicianId], minimumTime, spot1Time, spot2Time);
            } else {
                // Take spot 2
                breakTimes[musicianId] = spot2Time;
                spot2Time += breakLengths[musicianId];
                minimumTime -= breakLengths[musicianId];
                computeBreakTimes(breakLengths, dp, breakTimes, musicianId + 1, timeLeft, minimumTime,
                        spot1Time, spot2Time);
            }
        }
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
