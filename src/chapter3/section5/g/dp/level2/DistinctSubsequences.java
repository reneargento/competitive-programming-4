package chapter3.section5.g.dp.level2;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/01/23.
 */
public class DistinctSubsequences {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.getLine();
            String target = FastReader.getLine();
            BigInteger substrings = countSubstrings(string, target);
            outputWriter.printLine(substrings);
        }
        outputWriter.flush();
    }

    private static BigInteger countSubstrings(String string, String target) {
        // dp[currentStringIndex][targetStringIndex] = number of substrings
        BigInteger[][] dp = new BigInteger[string.length() + 1][target.length() + 1];
        for (BigInteger[] values : dp) {
            Arrays.fill(values, BigInteger.valueOf(-1));
        }
        return countSubstrings(string, target, dp, 0, 0);
    }

    private static BigInteger countSubstrings(String string, String target, BigInteger[][] dp, int stringIndex, int targetIndex) {
        if (targetIndex == target.length()) {
            return BigInteger.ONE;
        }
        if (stringIndex == string.length()) {
            return BigInteger.ZERO;
        }
        if (!dp[stringIndex][targetIndex].equals(BigInteger.valueOf(-1))) {
            return dp[stringIndex][targetIndex];
        }

        BigInteger substringsCount = BigInteger.ZERO;
        if (string.charAt(stringIndex) == target.charAt(targetIndex)) {
            substringsCount = substringsCount
                    .add(countSubstrings(string, target, dp, stringIndex + 1, targetIndex + 1));
        }
        substringsCount = substringsCount
                .add(countSubstrings(string, target, dp, stringIndex + 1, targetIndex));

        dp[stringIndex][targetIndex] = substringsCount;
        return dp[stringIndex][targetIndex];
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
