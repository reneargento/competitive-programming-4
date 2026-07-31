package chapter6.section3.a.classic;

import java.io.*;

/**
 * Created by Rene Argento on 29/07/2026.
 */
public class LongestMatch {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int caseNumber = 1;
        while (line != null) {
            String[] sequence1 = getWords(line);
            String[] sequence2 = getWords(FastReader.getLine());

            outputWriter.print(String.format("%2d. ", caseNumber));
            int lcs = longestCommonSubsequenceLength(sequence1, sequence2);
            if (lcs == -1) {
                outputWriter.printLine("Blank!");
            } else {
                outputWriter.printLine(String.format("Length of longest match: %d", lcs));
            }
            caseNumber++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String[] getWords(String line) {
        line = line.replaceAll("[^A-Za-z0-9]", " ");
        return line.split("\\s+");
    }

    private static int longestCommonSubsequenceLength(String[] sequence1, String[] sequence2) {
        if (sequence1.length == 0 || sequence2.length == 0) {
            return -1;
        }
        if ((sequence1.length == 1 && sequence1[0].isEmpty())
                || (sequence2.length == 1 && sequence2[0].isEmpty())) {
            return -1;
        }
        int[][] dp = new int[sequence1.length + 1][sequence2.length + 1];

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (sequence1[i - 1].equals(sequence2[j - 1])) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[sequence1.length][sequence2.length];
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