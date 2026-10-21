package chapter6.section7.palindrome.checking;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/10/2026.
 */
public class MagicSquarePalindromes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String sentence = FastReader.getLine();
            int squareDimension = computeSquareDimension(sentence);
            outputWriter.printLine(String.format("Case #%d:", t));
            if (squareDimension == -1) {
                outputWriter.printLine("No magic :(");
            } else {
                outputWriter.printLine(squareDimension);
            }
        }
        outputWriter.flush();
    }

    private static int computeSquareDimension(String sentence) {
        String clearedSentence = sentence.replaceAll("[^a-z]","");
        int length = clearedSentence.length();

        int dimensionCandidate = (int) Math.sqrt(length);
        if (dimensionCandidate != Math.sqrt(length)
                || !isPalindrome(clearedSentence)) {
            return -1;
        }
        return dimensionCandidate;
    }

    private static boolean isPalindrome(String clearedSentence) {
        for (int i = 0; i < clearedSentence.length() / 2; i++) {
            if (clearedSentence.charAt(i) != clearedSentence.charAt(clearedSentence.length() - i - 1)) {
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