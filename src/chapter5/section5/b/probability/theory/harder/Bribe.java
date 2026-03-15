package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/03/26.
 */
public class Bribe {

    private static class Henchman {
        int moneyToBribe;
        double probabilityToConvert;

        public Henchman(int moneyToBribe, double probabilityToConvert) {
            this.moneyToBribe = moneyToBribe;
            this.probabilityToConvert = probabilityToConvert;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Henchman[] henchmen = new Henchman[FastReader.nextInt()];
            int neededToConvert = FastReader.nextInt();
            int money = FastReader.nextInt();

            for (int i = 0; i < henchmen.length; i++) {
                henchmen[i] = new Henchman(FastReader.nextInt(), FastReader.nextInt() / 100.0);
            }

            double probabilityToSucceed = computeProbabilityToSucceed(neededToConvert, money, henchmen);
            outputWriter.printLine(probabilityToSucceed);
        }
        outputWriter.flush();
    }

    private static double computeProbabilityToSucceed(int neededToConvert, int money, Henchman[] henchmen) {
        // dp[henchmen needed to convert][bitmask of available henchmen to try to bribe]
        int maxBitmask = (1 << henchmen.length);
        double[][] dp = new double[neededToConvert + 1][maxBitmask];
        for (double[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeProbabilityToSucceed(neededToConvert, 0, money, henchmen, dp);
    }

    private static double computeProbabilityToSucceed(int neededToConvert, int bitmask, int totalMoney,
                                                      Henchman[] henchmen, double[][] dp) {
        if (neededToConvert == 0) {
            return 1;
        }
        if (bitmask == (2 << henchmen.length) - 1) {
            return 0;
        }
        if (dp[neededToConvert][bitmask] != -1) {
            return dp[neededToConvert][bitmask];
        }

        int moneySpent = computeMoneySpent(bitmask, henchmen);
        int moneyLeft = totalMoney - moneySpent;

        double probabilityToSucceed = 0;
        for (int i = 0; i < henchmen.length; i++) {
            if (((1 << i) & bitmask) == 0 && henchmen[i].moneyToBribe <= moneyLeft) {
                Henchman henchman = henchmen[i];
                int nextBitmask = bitmask | (1 << i);
                double probabilitySuccess = henchman.probabilityToConvert *
                        computeProbabilityToSucceed(neededToConvert - 1, nextBitmask, totalMoney,
                                henchmen, dp);
                double probabilityFailure = (1 - henchman.probabilityToConvert) *
                        computeProbabilityToSucceed(neededToConvert, nextBitmask, totalMoney, henchmen, dp);
                probabilityToSucceed = Math.max(probabilityToSucceed, probabilitySuccess + probabilityFailure);
            }
        }

        dp[neededToConvert][bitmask] = probabilityToSucceed;
        return probabilityToSucceed;
    }

    private static int computeMoneySpent(int bitmask, Henchman[] henchmen) {
        int moneySpent = 0;
        for (int i = 0; i < henchmen.length; i++) {
            if (((1 << i) & bitmask) > 0) {
                moneySpent += henchmen[i].moneyToBribe;
            }
        }
        return moneySpent;
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
