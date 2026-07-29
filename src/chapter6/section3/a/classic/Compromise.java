package chapter6.section3.a.classic;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by Rene Argento on 28/07/2026.
 */
public class Compromise {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            List<String> text1 = new ArrayList<>();
            addWordsToText(text1, line);
            readText(text1);

            List<String> text2 = new ArrayList<>();
            readText(text2);

            List<String> lcs = longestCommonSubsequence(text1, text2);
            for (int i = 0; i < lcs.size(); i++) {
                if (i > 0) {
                    outputWriter.print(" ");
                }
                outputWriter.print(lcs.get(i));
            }
            outputWriter.printLine();

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void readText(List<String> text) throws IOException {
        String line = FastReader.getLine();
        while (!line.equals("#")) {
            addWordsToText(text, line);
            line = FastReader.getLine();
        }
    }

    private static void addWordsToText(List<String> text, String line) {
        text.addAll(Arrays.asList(line.split(" ")));
    }

    private static List<String> longestCommonSubsequence(List<String> sequence1, List<String> sequence2) {
        if (sequence1 == null || sequence2 == null) {
            return new ArrayList<>();
        }
        int[][] dp = new int[sequence1.size() + 1][sequence2.size() + 1];

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (sequence1.get(i - 1).equals(sequence2.get(j - 1))) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return longestCommonSubsequence(dp, sequence1, sequence2);
    }

    private static List<String> longestCommonSubsequence(int[][] dp, List<String> sequence1, List<String> sequence2) {
        Stack<String> stack = new Stack<>();

        int i = sequence1.size();
        int j = sequence2.size();

        while (i > 0 && j > 0) {
            if (sequence1.get(i - 1).equals(sequence2.get(j - 1))) {
                stack.push(sequence1.get(i - 1));

                i--;
                j--;
            } else {
                if (dp[i - 1][j] > dp[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }
            }
        }

        List<String> longestCommonSubsequence = new ArrayList<>();
        while (!stack.isEmpty()) {
            longestCommonSubsequence.add(stack.pop());
        }
        return longestCommonSubsequence;
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