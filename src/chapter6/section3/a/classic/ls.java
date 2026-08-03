package chapter6.section3.a.classic;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/07/2026.
 */
public class ls {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String pattern = FastReader.getLine();
        int files = FastReader.nextInt();
        for (int i = 0; i < files; i++) {
            String fileName = FastReader.getLine();
            if (canAlign(pattern, fileName)) {
                outputWriter.printLine(fileName);
            }
        }
        outputWriter.flush();
    }

    private static boolean canAlign(String string1, String string2) {
        Boolean[][] dp = new Boolean[string1.length() + 1][string2.length() + 1];
        int patternEndIndex = 0;
        for (int i = string1.length() - 1; i >= 0; i--) {
            if (string1.charAt(i) != '*') {
                patternEndIndex = i + 1;
                break;
            }
        }
        return stringAlignment(string1, string2, dp, patternEndIndex, 0, 0);
    }

    private static boolean stringAlignment(String string1, String string2, Boolean[][] dp, int patternEndIndex,
                                           int indexString1, int indexString2) {
        if (indexString2 == string2.length() && indexString1 >= patternEndIndex) {
            return true;
        }
        if (indexString1 == string1.length() || indexString2 == string2.length()) {
            return false;
        }
        if (dp[indexString1][indexString2] != null) {
           return dp[indexString1][indexString2];
        }

        boolean score1 = false;
        boolean score2 = false;
        boolean score3 = false;
        if (string1.charAt(indexString1) == '*'
                || string1.charAt(indexString1) == string2.charAt(indexString2)) {
            score1 = stringAlignment(string1, string2, dp, patternEndIndex, indexString1 + 1, indexString2 + 1);
        }
        if (string1.charAt(indexString1) == '*') {
            score2 = stringAlignment(string1, string2, dp, patternEndIndex, indexString1, indexString2 + 1);
            score3 = stringAlignment(string1, string2, dp, patternEndIndex, indexString1 + 1, indexString2);
        }
        dp[indexString1][indexString2] = score1 || score2 || score3;
        return dp[indexString1][indexString2];
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