package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by Rene Argento on 17/01/26.
 */
public class CoinToss {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int coins = Integer.parseInt(data[0]);
            int minimalHeads = Integer.parseInt(data[1]);

            BigInteger validSequences = computeValidSequences(coins, minimalHeads);
            outputWriter.printLine(validSequences);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigInteger computeValidSequences(int coins, int minimalHeads) {
        // dp[coins remaining][currentHeadSequence]
        BigInteger[][] dp = new BigInteger[coins + 1][coins + 1];
        for (BigInteger[] values : dp) {
            Arrays.fill(values, BigInteger.valueOf(-1));
        }
        return computeSequences(dp, minimalHeads, coins, 0);
    }

    private static BigInteger computeSequences(BigInteger[][] dp, int minimalHeads, int coinsRemaining, int currentHeadSequence) {
        if (coinsRemaining == 0) {
            if (currentHeadSequence >= minimalHeads) {
                return BigInteger.ONE;
            } else {
                return BigInteger.ZERO;
            }
        }
        if (dp[coinsRemaining][currentHeadSequence].compareTo(BigInteger.valueOf(-1)) != 0) {
            return dp[coinsRemaining][currentHeadSequence];
        }

        int nextHeadSequenceForTails;
        if (currentHeadSequence >= minimalHeads) {
            nextHeadSequenceForTails = minimalHeads;
        } else {
            nextHeadSequenceForTails = 0;
        }
        BigInteger totalValidSequences =
                computeSequences(dp, minimalHeads, coinsRemaining - 1, nextHeadSequenceForTails) // Tails
                        .add(computeSequences(dp, minimalHeads, coinsRemaining - 1, currentHeadSequence + 1)); // Heads;
        dp[coinsRemaining][currentHeadSequence] = totalValidSequences;
        return totalValidSequences;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
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
