package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/11/21.
 */
public class BlackboardBonanza {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int wordsNumber = FastReader.nextInt();
            String[] words = new String[wordsNumber];
            for (int i = 0; i < words.length; i++) {
                words[i] = FastReader.next();
            }

            int maxPoints = computeMaxPoints(words);
            outputWriter.printLine(maxPoints);
        }
        outputWriter.flush();
    }

    private static int computeMaxPoints(String[] words) {
        int maxPoints = 0;

        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (i == j) {
                    continue;
                }

                for (int startIndex = 0; startIndex < words[j].length(); startIndex++) {
                    int points = computePoints(words[i], words[j], startIndex);
                    maxPoints = Math.max(maxPoints, points);
                }
            }
        }
        return maxPoints;
    }

    private static int computePoints(String word1, String word2, int startIndex) {
        int points = 0;
        int word1Index = 0;

        for (int word2Index = startIndex; word2Index < word2.length() && word1Index < word1.length();
             word1Index++, word2Index++) {
            if (word1.charAt(word1Index) == word2.charAt(word2Index)) {
                points++;
            }
        }
        return points;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
