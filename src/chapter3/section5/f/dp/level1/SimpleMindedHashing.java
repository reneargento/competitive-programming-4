package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/12/22.
 */
public class SimpleMindedHashing {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int length = FastReader.nextInt();
        int targetSum = FastReader.nextInt();
        int caseId = 1;
        int sumUpperBound = 26 * 26;
        int[][][] dp = createDp(sumUpperBound);

        while (length != 0 || targetSum != 0) {
            int stringsCount = 0;
            if (length <= 26 && targetSum < sumUpperBound) {
                stringsCount = countStrings(length, targetSum, dp);
            }
            outputWriter.printLine(String.format("Case %d: %d", caseId, stringsCount));

            caseId++;
            length = FastReader.nextInt();
            targetSum = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[][][] createDp(int targetSum) {
        // dp[length][last letter in string][sum]
        int[][][] dp = new int[27][26][targetSum];

        for (int i = 0; i < dp[0].length; i++) {
            dp[1][i][i + 1] = 1;
        }

        for (int length = 2; length < dp.length; length++) {
            for (int previousCharacterIndex = 0; previousCharacterIndex < dp[0].length; previousCharacterIndex++) {
                for (int sum = 0; sum < dp[0][0].length; sum++) {

                    for (int characterIndex = previousCharacterIndex + 1; characterIndex < dp[0].length; characterIndex++) {
                        if (sum >= (characterIndex + 1)) {
                            int newValues = dp[length - 1][previousCharacterIndex][sum - (characterIndex + 1)];
                            dp[length][characterIndex][sum] += newValues;
                        }
                    }
                }
            }
        }
        return dp;
    }

    private static int countStrings(int targetLength, int targetSum, int[][][] dp) {
        int stringsNumber = 0;
        for (int characterIndex = 0; characterIndex < dp[0].length; characterIndex++) {
            stringsNumber += dp[targetLength][characterIndex][targetSum];
        }
        return stringsNumber;
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
