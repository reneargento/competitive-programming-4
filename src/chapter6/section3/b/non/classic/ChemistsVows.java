package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/08/2026.
 */
public class ChemistsVows {

    private static final String[] symbols = {
            "h", "he", "li", "be", "b", "c", "n", "o", "f", "ne",
            "na", "mg", "al", "si", "p", "s", "cl", "ar",
            "k", "ca", "sc", "ti", "v", "cr", "mn", "fe", "co", "ni", "cu", "zn", "ga", "ge", "as", "se", "br", "kr",
            "rb", "sr", "y", "zr", "nb", "mo", "tc", "ru", "rh", "pd", "ag", "cd", "in", "sn", "sb", "te", "i", "xe",
            "cs", "ba", "hf", "ta", "w", "re", "os", "ir", "pt", "au", "hg", "tl", "pb", "bi", "po", "at", "rn",
            "fr", "ra", "rf", "db", "sg", "bh", "hs", "mt", "ds", "rg", "cn", "fl", "lv",
            "la", "ce", "pr", "nd", "pm", "sm", "eu", "gd", "tb", "dy", "ho", "er", "tm", "yb", "lu",
            "ac", "th", "pa", "u", "np", "pu", "am", "cm", "bk", "cf", "es", "fm", "md", "no", "lr"
    };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            char[] word = FastReader.next().toCharArray();
            String result = canSpeak(word);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String canSpeak(char[] word) {
        // dp[word index] = can speak or not
        Boolean[] dp = new Boolean[word.length];
        return canSpeak(word, dp, 0) ? "YES" : "NO";
    }

    private static boolean canSpeak(char[] word, Boolean[] dp, int wordIndex) {
        if (wordIndex == word.length) {
            return true;
        }
        if (dp[wordIndex] != null) {
            return dp[wordIndex];
        }

        boolean canSpeak = false;
        for (String symbol : symbols) {
            int endIndex = wordIndex + symbol.length();
            if (endIndex <= word.length
                    && matchesSymbol(word, wordIndex, symbol)) {
                canSpeak |= canSpeak(word, dp, endIndex);
            }
        }

        dp[wordIndex] = canSpeak;
        return dp[wordIndex];
    }

    private static boolean matchesSymbol(char[] word, int wordIndex, String symbol) {
        for (int c = 0; c < symbol.length(); c++) {
            if (word[wordIndex + c] != symbol.charAt(c)) {
                return false;
            }
        }
        return true;
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