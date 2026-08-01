package chapter6.section3.a.classic;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/07/2026.
 */
public class CarefulDeclaration {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String text1 = FastReader.getLine();
        while (!text1.equals(".")) {
            String[] sequence1 = text1.split(" ");
            String[] sequence2 = FastReader.getLine().split(" ");

            String mergedDeclaration = mergeDeclarations(sequence1, sequence2);
            outputWriter.printLine(mergedDeclaration);
            text1 = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String mergeDeclarations(String[] text1, String[] text2) {
        StringBuilder mergedDeclaration = new StringBuilder();
        int length1 = text1.length - 1;
        int length2 = text2.length - 1;

        int[][] dp = computeDpLengths(text1, text2);

        int text1Index = 0;
        int text2Index = 0;
        while (text1Index < length1 && text2Index < length2) {
            if (text1[text1Index].equals(text2[text2Index])) {
                mergedDeclaration.append(text1[text1Index]);
                text1Index++;
                text2Index++;
            } else if (dp[text1Index + 1][text2Index] < dp[text1Index][text2Index + 1]) {
                mergedDeclaration.append(text1[text1Index]);
                text1Index++;
            } else if (dp[text1Index + 1][text2Index] > dp[text1Index][text2Index + 1]) {
                mergedDeclaration.append(text2[text2Index]);
                text2Index++;
            } else if (text1[text1Index].compareTo(text2[text2Index]) < 0) {
                mergedDeclaration.append(text1[text1Index]);
                text1Index++;
            } else {
                mergedDeclaration.append(text2[text2Index]);
                text2Index++;
            }
            mergedDeclaration.append(" ");
        }

        if (text1Index == length1) {
            while (text2Index < length2) {
                mergedDeclaration.append(text2[text2Index]).append(" ");
                text2Index++;
            }
        } else if (text2Index == length2) {
            while (text1Index < length1) {
                mergedDeclaration.append(text1[text1Index]).append(" ");
                text1Index++;
            }
        }
        mergedDeclaration.append(".");
        return mergedDeclaration.toString();
    }

    private static int[][] computeDpLengths(String[] text1, String[] text2) {
        int length1 = text1.length - 1;
        int length2 = text2.length - 1;

        // dp[i][j] = length of the optimal supersequence of (text1[i], ..., text1[length1]) and (text2[j], ..., text2[length2])
        int[][] dp = new int[length1 + 1][length2 + 1];
        // Base cases
        for (int i = 0; i < dp.length; i++) {
            dp[i][length2] = length1 - i;
        }
        for (int j = 0; j < dp[0].length; j++) {
            dp[length1][j] = length2 - j;
        }

        for (int i = length1 - 1; i >= 0; i--) {
            for (int j = length2 - 1; j >= 0; j--) {
                if (text1[i].equals(text2[j])) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                } else {
                    dp[i][j] = Math.min(dp[i + 1][j], dp[i][j + 1]) + 1;
                }
            }
        }
        return dp;
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