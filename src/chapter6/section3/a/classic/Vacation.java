package chapter6.section3.a.classic;

import java.io.*;

/**
 * Created by Rene Argento on 30/07/2026.
 */
public class Vacation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseId = 1;
        String sequence1 = FastReader.getLine();
        while (sequence1.isEmpty() || sequence1.charAt(0) != '#') {
            String sequence2 = FastReader.getLine();
            int lcs = longestCommonSubsequenceLength(sequence1, sequence2);

            outputWriter.printLine(String.format("Case #%d: you can visit at most %d cities.", caseId, lcs));
            caseId++;
            sequence1 = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int longestCommonSubsequenceLength(String sequence1, String sequence2) {
        int[][] dp = new int[sequence1.length() + 1][sequence2.length() + 1];

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (sequence1.charAt(i - 1) == sequence2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[sequence1.length()][sequence2.length()];
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