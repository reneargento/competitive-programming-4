package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/08/2026.
 */
public class Strings {

    private static final int MOD = 10007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            char[] string1 = readString();
            char[] string2 = readString();
            char[] targetString = readString();
            int ways = countWays(string1, string2, targetString);
            outputWriter.printLine(ways);
        }
        outputWriter.flush();
    }

    private static char[] readString() throws IOException {
        String stringValue = FastReader.next();
        char[] string = new char[stringValue.length() + 1];
        System.arraycopy(stringValue.toCharArray(), 0, string, 1, stringValue.length());
        return string;
    }

    private static int countWays(char[] string1, char[] string2, char[] targetString) {
        // dp[target string index][string 1 index][string 2 index]
        int[][][] dp = new int[targetString.length][string1.length][string2.length];
        int[][][] string1PrefixSum = new int[targetString.length][string1.length][string2.length];
        int[][][] string2PrefixSum = new int[targetString.length][string1.length][string2.length];

        for (int i = 0; i <= string1.length - 1; i++) {
            for (int j = 0; j <= string2.length - 1; j++) {
                dp[0][i][j] = 1;
                string1PrefixSum[0][i][j] = 1;
                string2PrefixSum[0][i][j] = 1;
            }
        }
        return countWays(string1, string2, targetString, dp, string1PrefixSum, string2PrefixSum);
    }

    private static int countWays(char[] string1, char[] string2, char[] targetString, int[][][] dp,
                                 int[][][] string1PrefixSum, int[][][] string2PrefixSum) {
        for (int i = 1; i <= targetString.length - 1; i++) {
            for (int j = 0; j <= string1.length - 1; j++) {
                for (int k = 0; k <= string2.length - 1; k++) {
                    if (j != 0) {
                        string1PrefixSum[i][j][k] = string1PrefixSum[i][j - 1][k];
                        if (string1[j] == targetString[i]) {
                            string1PrefixSum[i][j][k] += dp[i - 1][j - 1][k];
                        }
                        string1PrefixSum[i][j][k] %= MOD;
                    }
                    if (k != 0) {
                        string2PrefixSum[i][j][k] = string2PrefixSum[i][j][k - 1];
                        if (string2[k] == targetString[i]) {
                            string2PrefixSum[i][j][k] += dp[i - 1][j][k - 1];
                        }
                        string2PrefixSum[i][j][k] %= MOD;
                    }
                    dp[i][j][k] = (string1PrefixSum[i][j][k] + string2PrefixSum[i][j][k]) % MOD;
                }
            }
        }
        return dp[targetString.length - 1][string1.length - 1][string2.length - 1];
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