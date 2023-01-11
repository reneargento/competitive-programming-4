package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/01/23.
 */
public class FlightPlanner {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int sections = FastReader.nextInt() / 100;
            int[][] windStrength = new int[10][sections];
            for (int row = windStrength.length - 1; row >= 0; row--) {
                for (int column = 0; column < windStrength[0].length; column++) {
                    windStrength[row][column] = FastReader.nextInt();
                }
            }

            int minimumFuelNeeded = computeMinimumFuelNeeded(windStrength);
            outputWriter.printLine(minimumFuelNeeded);
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumFuelNeeded(int[][] windStrength) {
        // dp[height][section] = minimum fuel needed
        int[][] dp = new int[windStrength.length][windStrength[0].length];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMinimumFuelNeeded(windStrength, dp, 0, 0);
    }

    private static int computeMinimumFuelNeeded(int[][] windStrength, int[][] dp, int height, int section) {
        if (section == dp[0].length) {
            if (height == 0) {
                return 0;
            } else {
                return INFINITE;
            }
        }

        if (dp[height][section] != -1) {
            return dp[height][section];
        }

        int minimumFuelNeeded = INFINITE;
        if (height > 0) {
            int fuelToSink = 20 - windStrength[height][section];
            minimumFuelNeeded = fuelToSink + computeMinimumFuelNeeded(windStrength, dp, height - 1, section + 1);
        }
        if (height < windStrength.length - 1) {
            int fuelToClimb = 60 - windStrength[height][section];
            int fuelNeeded = fuelToClimb + computeMinimumFuelNeeded(windStrength, dp, height + 1, section + 1);
            minimumFuelNeeded = Math.min(minimumFuelNeeded, fuelNeeded);
        }
        int fuelToHold = 30 - windStrength[height][section];
        int fuelNeeded = fuelToHold + computeMinimumFuelNeeded(windStrength, dp, height, section + 1);
        minimumFuelNeeded = Math.min(minimumFuelNeeded, fuelNeeded);

        dp[height][section] = minimumFuelNeeded;
        return minimumFuelNeeded;
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
