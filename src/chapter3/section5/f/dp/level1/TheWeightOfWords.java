package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/01/23.
 */
public class TheWeightOfWords {

    private static final String NOT_FOUND = "NOT_FOUND";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int length = FastReader.nextInt();
        int targetWeight = FastReader.nextInt();

        String word = computeWord(length, targetWeight);
        if (!word.equals(NOT_FOUND)) {
            outputWriter.printLine(word);
        } else {
            outputWriter.printLine("impossible");
        }
        outputWriter.flush();
    }

    private static String computeWord(int length, int targetWeight) {
        String[][] dp = new String[length + 1][targetWeight + 1];
        return computeWord(dp, length, targetWeight, new StringBuilder(), 0, 0);
    }

    private static String computeWord(String[][] dp, int targetLength, int targetWeight, StringBuilder word,
                                      int currentLength, int currentWeight) {
        if (currentLength == targetLength) {
            if (currentWeight == targetWeight) {
                return word.toString();
            } else {
                return NOT_FOUND;
            }
        }
        if (currentWeight > targetWeight) {
            return NOT_FOUND;
        }
        if (dp[currentLength][currentWeight] != null) {
            return dp[currentLength][currentWeight];
        }

        for (int nextLetterWeight = 1; nextLetterWeight <= 26; nextLetterWeight++) {
            int nextWeight = currentWeight + nextLetterWeight;
            char nextLetter = (char) ('a' + (nextLetterWeight - 1));
            word.append(nextLetter);
            String targetWord = computeWord(dp, targetLength, targetWeight, word, currentLength + 1, nextWeight);
            if (!targetWord.equals(NOT_FOUND)) {
                dp[currentLength][currentWeight] = targetWord;
                return targetWord;
            }
            word.deleteCharAt(word.length() - 1);
        }
        dp[currentLength][currentWeight] = NOT_FOUND;
        return NOT_FOUND;
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
