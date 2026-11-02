package chapter6.section7.palindrome.generating;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 29/10/2026.
 */
public class PalindromeNames {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String name = FastReader.getLine();
        int changesNeeded = countChangesNeeded(name);
        outputWriter.printLine(changesNeeded);
        outputWriter.flush();
    }

    private static int countChangesNeeded(String name) {
        // dp[start index][end index][can add more in the end, 0 = no, 1 = yes] = number of changes needed
        int[][][] dp = new int[name.length()][name.length()][2];
        for (int[][] values1 : dp) {
            for (int[] values2 : values1) {
                Arrays.fill(values2, -1);
            }
        }
        return countChangesNeeded(name, dp, 0, name.length() - 1, 1);
    }

    private static int countChangesNeeded(String name, int[][][] dp, int startIndex, int endIndex, int canAddEnd) {
        if (startIndex >= endIndex) {
            return 0;
        }
        if (dp[startIndex][endIndex][canAddEnd] != -1) {
            return dp[startIndex][endIndex][canAddEnd];
        }

        int changesNeeded1;
        if (name.charAt(startIndex) == name.charAt(endIndex)) {
            changesNeeded1 = countChangesNeeded(name, dp, startIndex + 1, endIndex - 1, 0);
        } else {
            // Replace letter
            changesNeeded1 = 1 + countChangesNeeded(name, dp, startIndex + 1, endIndex - 1, 0);
        }
        int changesNeeded2 = Integer.MAX_VALUE;
        if (canAddEnd == 1) {
            // Add letter to the end
            changesNeeded2 = 1 + countChangesNeeded(name, dp, startIndex + 1, endIndex, canAddEnd);
        }
        int changesNeeded = Math.min(changesNeeded1, changesNeeded2);

        dp[startIndex][endIndex][canAddEnd] = changesNeeded;
        return changesNeeded;
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