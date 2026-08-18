package chapter6.section4.a.standard;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/08/2026.
 */
public class ScrollingSign {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.nextInt();
            String[] words = new String[FastReader.nextInt()];
            for (int i = 0; i < words.length; i++) {
                words[i] = FastReader.next();
            }

            int minimumLetters = computeMinimumLetters(words);
            outputWriter.printLine(minimumLetters);
        }
        outputWriter.flush();
    }

    private static int computeMinimumLetters(String[] words) {
        int minimumLetters = words[0].length();
        for (int i = 1; i < words.length; i++) {
            int longestPrefix = findLongestPrefix(words[i - 1], words[i]);
            minimumLetters += words[i].length() - longestPrefix;
        }
        return minimumLetters;
    }

    private static int findLongestPrefix(String word1, String word2) {
        int longestPrefix = 0;

        for (int size = 1; size <= word2.length(); size++) {
            for (int i = 0; i < size; i++) {
                int word1Index = word1.length() - size + i;
                if (word1.charAt(word1Index) == word2.charAt(i)) {
                    if (word1Index == word1.length() - 1
                            && i + 1 > longestPrefix) {
                        longestPrefix = i + 1;
                    }
                } else {
                    break;
                }
            }
        }
        return longestPrefix;
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