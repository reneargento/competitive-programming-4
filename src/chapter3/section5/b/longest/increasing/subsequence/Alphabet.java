package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 11/11/22.
 */
public class Alphabet {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();
        int lettersNeeded = countLettersNeeded(string);
        outputWriter.printLine(lettersNeeded);
        outputWriter.flush();
    }

    private static int countLettersNeeded(String string) {
        int[] dp = new int[string.length()];
        Arrays.fill(dp, 1);
        int longestIncreasingSubsequence = 1;

        for (int i = 0; i < dp.length; i++) {
            for (int j = i + 1; j < dp.length; j++) {
                if (string.charAt(j) > string.charAt(i)
                        && dp[i] + 1 > dp[j]) {
                    dp[j] = dp[i] + 1;
                    longestIncreasingSubsequence = Math.max(longestIncreasingSubsequence, dp[j]);
                }
            }
        }
        return 26 - longestIncreasingSubsequence;
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
