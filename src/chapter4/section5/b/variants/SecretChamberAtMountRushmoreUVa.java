package chapter4.section5.b.variants;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/03/24.
 */
public class SecretChamberAtMountRushmoreUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int translations = Integer.parseInt(data[0]);
            int wordPairs = Integer.parseInt(data[1]);

            Map<Character, Integer> letterToIDMap = new HashMap<>();
            boolean[][] translatable = new boolean[26][26];
            for (int letterID = 0; letterID < translatable.length; letterID++) {
                translatable[letterID][letterID] = true;
            }

            for (int i = 0; i < translations; i++) {
                int letterID1 = getLetterID(letterToIDMap, FastReader.next().charAt(0));
                int letterID2 = getLetterID(letterToIDMap, FastReader.next().charAt(0));
                translatable[letterID1][letterID2] = true;
            }

            floydWarshall(translatable);

            for (int i = 0; i < wordPairs; i++) {
                String word1 = FastReader.next();
                String word2 = FastReader.next();
                outputWriter.printLine(wordsMatch(letterToIDMap, translatable, word1, word2) ? "yes" : "no");
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean wordsMatch(Map<Character, Integer> letterToIDMap, boolean[][] translatable, String word1,
                                      String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        for (int i = 0; i < word1.length(); i++) {
            char letter1 = word1.charAt(i);
            char letter2 = word2.charAt(i);
            int letterID1 = getLetterID(letterToIDMap, letter1);
            int letterID2 = getLetterID(letterToIDMap, letter2);
            if (!translatable[letterID1][letterID2]) {
                return false;
            }
        }
        return true;
    }

    private static int getLetterID(Map<Character, Integer> letterToIDMap, char letter) {
        if (!letterToIDMap.containsKey(letter)) {
            letterToIDMap.put(letter, letterToIDMap.size());
        }
        return letterToIDMap.get(letter);
    }

    private static void floydWarshall(boolean[][] translatable) {
        for (int vertex1 = 0; vertex1 < translatable.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < translatable.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < translatable.length; vertex3++) {
                    translatable[vertex2][vertex3] |= (translatable[vertex2][vertex1] && translatable[vertex1][vertex3]);
                }
            }
        }
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
