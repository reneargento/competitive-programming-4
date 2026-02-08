package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/02/26.
 */
public class DiceBetting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int numberOfThrows = FastReader.nextInt();
        int sides = FastReader.nextInt();
        int differentNumbers = FastReader.nextInt();

        double winProbability = computeWinProbability(numberOfThrows, differentNumbers, sides);
        outputWriter.printLine(winProbability);
        outputWriter.flush();
    }

    private static double computeWinProbability(int numberOfThrows, int differentNumbers, int sides) {
        double[][] dp = new double[numberOfThrows + 1][sides + 1];
        for (double[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeWinProbability(0, 0, dp, numberOfThrows, differentNumbers, sides);
    }

    private static double computeWinProbability(int throwId, int uniqueNumbersCount, double[][] dp,
                                                int numberOfThrows, int differentNumbers, double sides) {
        if (uniqueNumbersCount >= differentNumbers) {
            return 1;
        }
        if (throwId == numberOfThrows) {
            return 0;
        }
        if (dp[throwId][uniqueNumbersCount] != -1) {
            return dp[throwId][uniqueNumbersCount];
        }

        double repeatProbability = uniqueNumbersCount / sides;
        double newProbability = 1 - repeatProbability;

        dp[throwId][uniqueNumbersCount] = repeatProbability * computeWinProbability(throwId + 1, uniqueNumbersCount,
                dp, numberOfThrows, differentNumbers, sides)
                + newProbability * computeWinProbability(throwId + 1, uniqueNumbersCount + 1,
                dp, numberOfThrows, differentNumbers, sides);
        return dp[throwId][uniqueNumbersCount];
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
