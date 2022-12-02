package chapter3.section5.d.coin.change;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/11/22.
 */
public class StampsAndEnvelopeSize {

    private static class Result {
        int maxCoverage;
        int[] denominations;

        public Result(int maxCoverage, int[] denominations) {
            this.maxCoverage = maxCoverage;
            this.denominations = denominations;
        }

        private int compare(Result other) {
            if (maxCoverage != other.maxCoverage) {
                return Integer.compare(other.maxCoverage, maxCoverage);
            }
            if (denominations.length != other.denominations.length) {
                return Integer.compare(denominations.length, other.denominations.length);
            }
            for (int i = denominations.length - 1; i >= 0; i--) {
                if (denominations[i] != other.denominations[i]) {
                    return Integer.compare(denominations[i], other.denominations[i]);
                }
            }
            return 0;
        }
    }

    private static final int INFINITE = 10000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int maxStamps = FastReader.nextInt();

        while (maxStamps != 0) {
            int[][] sets = new int[FastReader.nextInt()][];
            for (int set = 0; set < sets.length; set++) {
                sets[set] = new int[FastReader.nextInt()];
                for (int denomination = 0; denomination < sets[set].length; denomination++) {
                    sets[set][denomination] = FastReader.nextInt();
                }
            }

            Result bestCoverage = computeBestCoverage(sets, maxStamps);
            outputWriter.print(String.format("max coverage = %3d :", bestCoverage.maxCoverage));
            for (int denomination : bestCoverage.denominations) {
                outputWriter.print(String.format("%3d", denomination));
            }
            outputWriter.printLine();
            maxStamps = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeBestCoverage(int[][] sets, int maxStamps) {
        Result bestCoverage = null;

        for (int[] denominations : sets) {
            int[] dp = new int[1001];
            Arrays.fill(dp, INFINITE);
            dp[0] = 0;

            for (int denomination : denominations) {
                for (int coverage = denomination; coverage < dp.length; coverage++) {
                    int coinsNeededWithoutCurrent = dp[coverage];
                    int coinsNeededWithCurrent = dp[coverage - denomination] + 1;
                    dp[coverage] = Math.min(coinsNeededWithoutCurrent, coinsNeededWithCurrent);
                }
            }

            int maxCoverage = computeMaxCoverage(dp, maxStamps);
            Result result = new Result(maxCoverage, denominations);
            if (bestCoverage == null || bestCoverage.compare(result) > 0) {
                bestCoverage = result;
            }
        }
        return bestCoverage;
    }

    private static int computeMaxCoverage(int[] coverage, int maxStamps) {
        int maxCoverage = 0;
        for (int i = 1; i < coverage.length; i++) {
            if (coverage[i] <= maxStamps) {
                maxCoverage = i;
            } else {
                break;
            }
        }
        return maxCoverage;
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
