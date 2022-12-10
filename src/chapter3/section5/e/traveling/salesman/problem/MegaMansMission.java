package chapter3.section5.e.traveling.salesman.problem;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/12/22.
 */
public class MegaMansMission {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int robots = FastReader.nextInt();
            int[][] power = new int[robots + 1][robots];
            for (int row = 0; row < power.length; row++) {
                String line = FastReader.next();
                for (int column = 0; column < power[0].length; column++) {
                    power[row][column] = line.charAt(column) == '1' ? 1 : 0;
                }
            }
            long waysToKill = computeWaysToKill(power);
            outputWriter.printLine(String.format("Case %d: %d", t, waysToKill));
        }
        outputWriter.flush();
    }

    private static long computeWaysToKill(int[][] power) {
        int bitmaskSize = (int) Math.pow(2, power.length);
        long[][] dp = new long[power.length][bitmaskSize];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }

        int bitmask = 1;
        return computeWaysToKill(power, dp, 0, bitmask);
    }

    private static long computeWaysToKill(int[][] power, long[][] dp, int currentRobotId, int bitmask) {
        if (bitmask == dp[0].length - 1) {
            return 1;
        }
        if (dp[currentRobotId][bitmask] != -1) {
            return dp[currentRobotId][bitmask];
        }

        long waysToKill = 0;
        for (int robotId = 1; robotId < power.length; robotId++) {
            if ((bitmask & (1 << robotId)) == 0 && power[0][robotId - 1] == 1) {
                int powerGained = getWeapon(power[0], power[robotId]);
                int nextBitmask = bitmask | (1 << robotId);
                waysToKill += computeWaysToKill(power, dp, robotId, nextBitmask);
                loseWeapon(power[0], powerGained);
            }
        }
        dp[currentRobotId][bitmask] = waysToKill;
        return dp[currentRobotId][bitmask];
    }

    private static int getWeapon(int[] megaManPower, int[] robotPower) {
        int powerGained = 0;
        for (int bit = 0; bit < megaManPower.length; bit++) {
            if (megaManPower[bit] == 0 && robotPower[bit] == 1) {
                megaManPower[bit] = 1;
                powerGained |= (1 << bit);
            }
        }
        return powerGained;
    }

    private static void loseWeapon(int[] megaManPower, int power) {
        for (int bit = 0; bit < megaManPower.length; bit++) {
            if ((power & (1 << bit)) != 0) {
                megaManPower[bit] = 0;
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
