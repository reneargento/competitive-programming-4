package chapter6.section3.b.non.classic;

import java.io.*;

/**
 * Created by Rene Argento on 31/07/2026.
 */
public class StringFactoringKattis {

    private static final int INFINITE = 1000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        char[] letters = FastReader.getLine().toCharArray();
        int maximalFactoringLength = computeMaximalFactoringLength(letters);
        outputWriter.printLine(maximalFactoringLength);
        outputWriter.flush();
    }

    private static int computeMaximalFactoringLength(char[] letters) {
        // dp[start index][end index] = maximal factoring length from start index to end index
        int[][] dp = new int[letters.length + 1][letters.length + 1];
        return computeMaximalFactoringLength(letters, dp, 0, letters.length - 1);
    }

    private static int computeMaximalFactoringLength(char[] letters, int[][] dp, int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            return 1;
        }
        if (dp[startIndex][endIndex] != 0) {
            return dp[startIndex][endIndex];
        }

        dp[startIndex][endIndex] = INFINITE;
        for (int middleIndex = startIndex; middleIndex < endIndex; middleIndex++) {
            int weightCandidate = computeMaximalFactoringLength(letters, dp, startIndex, middleIndex) +
                    computeMaximalFactoringLength(letters, dp, middleIndex + 1, endIndex);
            dp[startIndex][endIndex] = Math.min(dp[startIndex][endIndex], weightCandidate);
        }

        int substringLength = endIndex - startIndex + 1;
        for (int length = 1; length <= substringLength; length++) {
            if (substringLength % length == 0) {
                int offsetIndex = 0;
                int currentIndex;
                for (currentIndex = startIndex; currentIndex <= endIndex; currentIndex++) {
                    if (letters[currentIndex] != letters[startIndex + offsetIndex]) {
                        break;
                    }
                    offsetIndex++;
                    if (offsetIndex >= length) {
                        offsetIndex = 0;
                    }
                }

                if (currentIndex == endIndex + 1 && endIndex != startIndex + length - 1) {
                    int weightCandidate = computeMaximalFactoringLength(letters, dp, startIndex, startIndex + length - 1);
                    dp[startIndex][endIndex] = Math.min(dp[startIndex][endIndex], weightCandidate);
                }
            }
        }
        return dp[startIndex][endIndex];
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