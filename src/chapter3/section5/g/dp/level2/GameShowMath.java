package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/01/23.
 */
public class GameShowMath {

    private static final int OFFSET = 32000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] numbers = new int[FastReader.nextInt()];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = FastReader.nextInt();
            }
            int target = FastReader.nextInt();
            String expression = computeExpression(numbers, target);
            if (expression != null) {
                outputWriter.printLine(expression);
            } else {
                outputWriter.printLine("NO EXPRESSION");
            }
        }
        outputWriter.flush();
    }

    private static String computeExpression(int[] numbers, int target) {
        // dp[numberID][currentSum] = possible to reach target or not
        Boolean[][] dp = new Boolean[numbers.length][64001];
        boolean possible = isPossible(numbers, target, dp, 1, numbers[0]);
        if (possible) {
            return computeExpression(numbers, target, dp);
        } else {
            return null;
        }
    }

    private static boolean isPossible(int[] numbers, int target, Boolean[][] dp, int numberID, int currentSum) {
        if (numberID == numbers.length) {
            return currentSum == target;
        }
        if (currentSum > OFFSET || currentSum < -OFFSET) {
            return false;
        }
        if (dp[numberID][currentSum + OFFSET] != null) {
            return dp[numberID][currentSum + OFFSET];
        }

        boolean isPossibleAdd = isPossible(numbers, target, dp, numberID + 1, currentSum + numbers[numberID]);
        boolean isPossibleSubtract = !isPossibleAdd
                && isPossible(numbers, target, dp, numberID + 1, currentSum - numbers[numberID]);
        boolean isPossibleMultiply = !isPossibleAdd && !isPossibleSubtract
                && isPossible(numbers, target, dp, numberID + 1, currentSum * numbers[numberID]);
        boolean isPossibleDivide = false;
        if (!isPossibleAdd && !isPossibleSubtract && !isPossibleMultiply &&
                numbers[numberID] != 0 && currentSum % numbers[numberID] == 0) {
            isPossibleDivide = isPossible(numbers, target, dp, numberID + 1, currentSum / numbers[numberID]);
        }
        dp[numberID][currentSum + OFFSET] = isPossibleAdd || isPossibleSubtract || isPossibleMultiply || isPossibleDivide;
        return dp[numberID][currentSum + OFFSET];
    }

    private static String computeExpression(int[] numbers, int target, Boolean[][] dp) {
        StringBuilder expression = new StringBuilder();
        expression.append(numbers[0]);
        int sum = numbers[0];

        for (int numberID = 1; numberID < numbers.length; numberID++) {
            int addSum = sum + numbers[numberID];
            int subtractSum = sum - numbers[numberID];
            int multiplySum = sum * numbers[numberID];

            if (isPossible(numbers, target, dp, numberID + 1, addSum)) {
                expression.append("+");
                sum = addSum;
            } else if (isPossible(numbers, target, dp, numberID + 1, subtractSum)) {
                expression.append("-");
                sum = subtractSum;
            } else if (isPossible(numbers, target, dp, numberID + 1, multiplySum)) {
                expression.append("*");
                sum = multiplySum;
            } else {
                expression.append("/");
                sum = sum / numbers[numberID];
            }
            expression.append(numbers[numberID]);
        }
        expression.append("=").append(target);
        return expression.toString();
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
