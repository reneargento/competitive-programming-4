package chapter5.section7;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/04/26.
 */
public class Ivana {

    private static final int UNCOMPUTED = -9999;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] numbers = new int[FastReader.nextInt()];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = FastReader.nextInt() % 2;
        }
        int winningMoves = computeWinningMoves(numbers);
        outputWriter.printLine(winningMoves);
        outputWriter.flush();
    }

    private static int computeWinningMoves(int[] numbers) {
        int winningMoves = 0;

        for (int startIndex = 0; startIndex < numbers.length; startIndex++) {
            int[] circularArray = buildArray(numbers, startIndex);

            // dp[left index][right index]
            int[][] dp = new int[numbers.length][numbers.length];
            for (int[] values : dp) {
                Arrays.fill(values, UNCOMPUTED);
            }
            int result = computeWinningMoves(circularArray, dp, 0, circularArray.length - 2);
            if (result < circularArray[circularArray.length - 1]) {
                winningMoves++;
            }
        }
        return winningMoves;
    }

    private static int[] buildArray(int[] numbers, int startIndex) {
        int[] circularValues = new int[numbers.length];
        int index = 0;
        for (int i = startIndex; i < numbers.length; i++) {
            circularValues[index] = numbers[i];
            index++;
        }
        for (int i = 0; i < startIndex; i++) {
            circularValues[index] = numbers[i];
            index++;
        }
        return circularValues;
    }

    private static int computeWinningMoves(int[] numbers, int[][] dp, int left, int right) {
        if (left > right) {
            return 0;
        }
        if (dp[left][right] != UNCOMPUTED) {
            return dp[left][right];
        }

        int value1 = computeWinningMoves(numbers, dp, left + 1, right);
        int value2 = computeWinningMoves(numbers, dp, left, right - 1);
        dp[left][right] = Math.max(numbers[left] - value1, numbers[right] - value2);
        return dp[left][right];
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
