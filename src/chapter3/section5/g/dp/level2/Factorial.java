package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 19/01/23.
 */
public class Factorial {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] minimumNumbers = computeMinimumNumbers();

        String line = FastReader.getLine();
        while (line != null) {
            int value = Integer.parseInt(line);
            outputWriter.printLine(minimumNumbers[value]);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[] computeMinimumNumbers() {
        int[] factorials = computeFactorials();
        int[] dp = new int[100001];
        Arrays.fill(dp, INFINITE);
        dp[0] = 0;

        for (int sum = 1; sum < dp.length; sum++) {
            for (int factorial : factorials) {
                if (factorial <= sum) {
                    dp[sum] = Math.min(dp[sum], 1 + dp[sum - factorial]);
                }
            }
        }
        return dp;
    }

    private static int[] computeFactorials() {
        int[] factorials = new int[9];
        factorials[0] = 1;
        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        return factorials;
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
