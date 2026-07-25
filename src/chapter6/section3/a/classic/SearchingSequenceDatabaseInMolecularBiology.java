package chapter6.section3.a.classic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 23/07/2026.
 */
public class SearchingSequenceDatabaseInMolecularBiology {

    private static final int NEGATIVE_INFINITE = -100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int bestAlignmentScore = NEGATIVE_INFINITE;
        List<String> mostSimilarList = new ArrayList<>();

        FastReader.getLine();
        String query = FastReader.getLine();
        String line = FastReader.getLine();
        while (line != null) {
            FastReader.getLine();
            String sequence = FastReader.getLine();
            int score = stringAlignment(query, sequence);

            if (score > bestAlignmentScore) {
                bestAlignmentScore = score;
                mostSimilarList = new ArrayList<>();
                mostSimilarList.add(sequence);
            } else if (score == bestAlignmentScore) {
                mostSimilarList.add(sequence);
            }
            line = FastReader.getLine();
        }

        outputWriter.printLine("The query sequence is:");
        outputWriter.printLine(query);
        outputWriter.printLine("\nThe most similar sequences are:");

        for (String mostSimilar : mostSimilarList) {
            outputWriter.printLine("\n" + mostSimilar);
            outputWriter.printLine("The similarity score is: " + bestAlignmentScore);
        }
        outputWriter.flush();
    }

    private static int stringAlignment(String string1, String string2) {
        if (string1 == null || string2 == null) {
            return 0;
        }
        int[][] dp = new int[string1.length() + 1][string2.length() + 1];

        // Base cases
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = i * -7;
        }
        for (int j = 1; j < dp[0].length; j++) {
            dp[0][j] = j * -7;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = dp[i - 1][j - 1] + (string1.charAt(i - 1) == string2.charAt(j - 1) ? 5 : -4);
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j] - 7);
                dp[i][j] = Math.max(dp[i][j], dp[i][j - 1] - 7);
            }
        }
        return dp[string1.length()][string2.length()];
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