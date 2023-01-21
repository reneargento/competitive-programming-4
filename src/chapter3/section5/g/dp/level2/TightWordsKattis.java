package chapter3.section5.g.dp.level2;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Created by Rene Argento on 19/01/23.
 */
public class TightWordsKattis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int maxDigit = Integer.parseInt(data[0]);
            int length = Integer.parseInt(data[1]);

            BigDecimal tightWordsPercentage = computeTightWordsPercentage(maxDigit, length);
            outputWriter.printLine(String.format("%.9f", tightWordsPercentage));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigDecimal computeTightWordsPercentage(int maxDigit, int length) {
        BigDecimal totalWords = BigDecimal.valueOf(maxDigit + 1).pow(length);

        // dp[numberOfDigits][currentDigit] = quantity of tight words
        BigInteger[][] dp = new BigInteger[length + 1][maxDigit + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = BigInteger.valueOf(-1);
            }
        }
        for (int digit = 0; digit <= maxDigit; digit++) {
            dp[1][digit] = computeDp(dp, maxDigit, length, 1, digit);
        }

        BigDecimal tightWords = BigDecimal.ZERO;
        for (int lastDigit = 0; lastDigit <= maxDigit; lastDigit++) {
            tightWords = tightWords.add(new BigDecimal(dp[1][lastDigit]));
        }
        return tightWords.multiply(BigDecimal.valueOf(100)).divide(totalWords, 10, RoundingMode.DOWN);
    }

    private static BigInteger computeDp(BigInteger[][] dp, int maxDigit, int targetLength, int length, int currentDigit) {
        if (length == targetLength) {
            return BigInteger.ONE;
        }
        if (!dp[length][currentDigit].equals(BigInteger.valueOf(-1))) {
            return dp[length][currentDigit];
        }

        BigInteger numberOfWords = BigInteger.ZERO;
        int startDigit = Math.max(currentDigit - 1, 0);
        int endDigit = Math.min(currentDigit + 1, maxDigit);
        for (int nextDigit = startDigit; nextDigit <= endDigit; nextDigit++) {
            numberOfWords = numberOfWords.add(computeDp(dp, maxDigit, targetLength, length + 1, nextDigit));
        }
        dp[length][currentDigit] = numberOfWords;
        return dp[length][currentDigit];
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
