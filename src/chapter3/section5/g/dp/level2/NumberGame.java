package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/01/23.
 */
public class NumberGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int numbers = FastReader.nextInt();
        while (numbers != 0) {
            int[] values = new int[numbers * 2 - 1];
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.nextInt();
            }

            List<Integer> numbersToSum = computeNumbers(values);
            if (numbersToSum != null) {
                outputWriter.printLine("Yes");
                outputWriter.print(numbersToSum.get(0));
                for (int i = 1; i < numbersToSum.size(); i++) {
                    outputWriter.print(" " + numbersToSum.get(i));
                }
                outputWriter.printLine();
            } else {
                outputWriter.printLine("No");
            }
            numbers = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeNumbers(int[] values) {
        int target = (values.length + 1) / 2;
        // dp[numbersChosen][sum % target] = possible to make or not
        Boolean[][] dp = new Boolean[target + 1][target];

        boolean possible = checkPossibility(values, dp, target, 0, 0, 0);
        if (possible) {
            return computeNumbers(values, dp, target);
        } else {
            return null;
        }
    }

    private static boolean checkPossibility(int[] values, Boolean[][] dp, int target, int numberID, int sum,
                                            int numbersChosen) {
        if (numbersChosen == target && sum == 0) {
            return true;
        }
        if (numberID == values.length || numbersChosen == dp.length) {
            return false;
        }

        if (dp[numbersChosen][sum] != null) {
            return dp[numbersChosen][sum];
        }

        int nextSum = (sum + values[numberID]) % target;
        boolean possibleAdding = checkPossibility(values, dp, target, numberID + 1, nextSum,
                numbersChosen + 1);
        if (possibleAdding) {
            dp[numbersChosen][sum] = true;
            return true;
        }
        boolean possibleNotAdding = checkPossibility(values, dp, target, numberID + 1, sum, numbersChosen);
        dp[numbersChosen][sum] = possibleNotAdding;
        return dp[numbersChosen][sum];
    }

    private static List<Integer> computeNumbers(int[] values, Boolean[][] dp, int target) {
        List<Integer> numbersToSum = new ArrayList<>();

        int sum = 0;
        for (int numberID = 0; numberID < values.length; numberID++) {
            int nextSum = (sum + values[numberID]) % target;
            if (checkPossibility(values, dp, target, numberID, sum, numbersToSum.size())
                    == checkPossibility(values, dp, target, numberID + 1, nextSum, numbersToSum.size() + 1)) {
                numbersToSum.add(values[numberID]);
                sum = nextSum;
            }
        }
        return numbersToSum;
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
