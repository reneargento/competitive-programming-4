package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/01/23.
 */
public class TestTheRods {

    private static class Result {
        long minimumCost;
        int[] samplesTestedAtNCPC;

        public Result(long minimumCost, int[] samplesTestedAtNCPC) {
            this.minimumCost = minimumCost;
            this.samplesTestedAtNCPC = samplesTestedAtNCPC;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int ncpcRods = FastReader.nextInt();
        int bcewRods = FastReader.nextInt();

        while (ncpcRods != 0 || bcewRods != 0) {
            int sites = FastReader.nextInt();
            int[][][] costs = new int[sites][][];

            for (int site = 0; site < sites; site++) {
                int samples = FastReader.nextInt();
                costs[site] = new int[samples][2];
                for (int location = 0; location < 2; location++) {
                    for (int itemsTested = 0; itemsTested < samples; itemsTested++) {
                        costs[site][itemsTested][location] = FastReader.nextInt();
                    }
                }
            }

            Result result = computeResult(costs, ncpcRods, bcewRods);
            outputWriter.printLine(result.minimumCost);
            outputWriter.print(result.samplesTestedAtNCPC[0]);
            for (int i = 1; i < result.samplesTestedAtNCPC.length; i++) {
                outputWriter.print(" " + result.samplesTestedAtNCPC[i]);
            }
            outputWriter.printLine();
            outputWriter.printLine();

            ncpcRods = FastReader.nextInt();
            bcewRods = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeResult(int[][][] costs, int ncpcRods, int bcewRods) {
        // dp[siteId][rodsAtNCPC]
        int[][] dp = new int[costs.length][ncpcRods + 1];
        for (int site = 0; site < dp.length; site++) {
            Arrays.fill(dp[site], -1);
        }

        int minimumCost = computeMinimumCost(costs, ncpcRods, bcewRods, dp, 0, 0, 0);
        int[] samplesTestedAtNCPC = computeNCPCSamples(costs, ncpcRods, bcewRods, dp);
        return new Result(minimumCost, samplesTestedAtNCPC);
    }

    private static int computeMinimumCost(int[][][] costs, int ncpcRods, int bcewRods, int[][] dp, int siteId,
                                          int currentNCPCRods, int currentBCEWRods) {
        if (siteId == dp.length) {
            if (currentNCPCRods == ncpcRods && currentBCEWRods == bcewRods) {
                return 0;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        if (currentNCPCRods > ncpcRods || currentBCEWRods > bcewRods) {
            return Integer.MAX_VALUE;
        }
        if (dp[siteId][currentNCPCRods] != -1) {
            return dp[siteId][currentNCPCRods];
        }

        int minimumCost = Integer.MAX_VALUE;
        int totalRods = costs[siteId].length;
        for (int rods = 0; rods <= totalRods; rods++) {
            int newBCEWRods = totalRods - rods;
            int cost = computeMinimumCost(costs, ncpcRods, bcewRods, dp, siteId + 1,
                    currentNCPCRods + rods, currentBCEWRods + newBCEWRods);
            if (cost != Integer.MAX_VALUE) {
                if (rods > 0) {
                    cost += costs[siteId][rods - 1][0];
                }
                if (newBCEWRods > 0) {
                    cost += costs[siteId][newBCEWRods - 1][1];
                }
            }
            minimumCost = Math.min(minimumCost, cost);
        }
        dp[siteId][currentNCPCRods] = minimumCost;
        return minimumCost;
    }

    private static int[] computeNCPCSamples(int[][][] costs, int ncpcRods, int bcewRods, int[][] dp) {
        int[] samplesTestedAtNCPC = new int[costs.length];
        int currentNCPCRods = 0;
        int currentBCEWRods = 0;

        for (int siteId = 0; siteId < costs.length; siteId++) {
            int minimumCost = Integer.MAX_VALUE;
            int totalRods = costs[siteId].length;

            for (int rods = 0; rods <= totalRods; rods++) {
                int newBCEWRods = totalRods - rods;
                int cost = computeMinimumCost(costs, ncpcRods, bcewRods, dp, siteId + 1,
                        currentNCPCRods + rods, currentBCEWRods + newBCEWRods);
                if (cost != Integer.MAX_VALUE) {
                    if (rods > 0) {
                        cost += costs[siteId][rods - 1][0];
                    }
                    if (newBCEWRods > 0) {
                        cost += costs[siteId][newBCEWRods - 1][1];
                    }
                }
                if (cost < minimumCost) {
                    minimumCost = cost;
                    samplesTestedAtNCPC[siteId] = rods;
                }
                minimumCost = Math.min(minimumCost, cost);
            }
            currentNCPCRods += samplesTestedAtNCPC[siteId];
            currentBCEWRods += totalRods - samplesTestedAtNCPC[siteId];
        }
        return samplesTestedAtNCPC;
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
