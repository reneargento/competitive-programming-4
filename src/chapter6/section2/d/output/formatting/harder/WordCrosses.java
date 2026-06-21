package chapter6.section2.d.output.formatting.harder;

import java.io.*;

/**
 * Created by Rene Argento on 20/06/2026.
 */
public class WordCrosses {

    private static class IndexMatch {
        int indexWord1;
        int indexWord2;

        public IndexMatch(int indexWord1, int indexWord2) {
            this.indexWord1 = indexWord1;
            this.indexWord2 = indexWord2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int caseId = 1;
        while (!line.equals("#")) {
            if (caseId > 1) {
                outputWriter.printLine();
            }
            String[] words = line.split("\\s+");
            printCrossword(words, outputWriter);
            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printCrossword(String[] words, OutputWriter outputWriter) {
        IndexMatch match1 = matchWords(words[0], words[1]);
        IndexMatch match2 = matchWords(words[2], words[3]);
        if (match1 == null || match2 == null) {
            outputWriter.printLine("Unable to make two crosses");
            return;
        }

        int word1IndexStartWord2 = match1.indexWord2 - match2.indexWord2;
        int word2Index = 0;
        int spaceBetweenWords = computeSpaceBetweenWords(words[0], match1, match2);
        int spaceBeforeFirstAndLastCharacters = match1.indexWord1 + spaceBetweenWords + 1;

        if (word1IndexStartWord2 < 0) {
            for (int i = 0; i < Math.abs(word1IndexStartWord2) && i < words[3].length(); i++) {
                for (int s = 0; s < spaceBeforeFirstAndLastCharacters; s++) {
                    outputWriter.print(" ");
                }
                outputWriter.printLine(words[3].charAt(i));
                word2Index++;
            }
        }

        for (int i = 0; i < words[1].length(); i++) {
            if (i != match1.indexWord2) {
                for (int s = 0; s < match1.indexWord1; s++) {
                    outputWriter.print(" ");
                }
            } else {
                for (char character : words[0].toCharArray()) {
                    outputWriter.print(character);
                }
                for (int s  = 0; s < 3; s++) {
                    outputWriter.print(" ");
                }
                for (char character : words[2].toCharArray()) {
                    outputWriter.print(character);
                }
                outputWriter.printLine();
                word2Index++;
                continue;
            }
            outputWriter.print(words[1].charAt(i));

            if (i >= word1IndexStartWord2 && word2Index < words[3].length()) {
                for (int s = 0; s < spaceBetweenWords; s++) {
                    outputWriter.print(" ");
                }
                outputWriter.print(words[3].charAt(word2Index));
                word2Index++;
            }
            outputWriter.printLine();
        }

        while (word2Index < words[3].length()) {
            for (int s = 0; s < spaceBeforeFirstAndLastCharacters; s++) {
                outputWriter.print(" ");
            }
            outputWriter.printLine(words[3].charAt(word2Index));
            word2Index++;
        }
    }

    private static int computeSpaceBetweenWords(String word1, IndexMatch match1, IndexMatch match2) {
        return (word1.length() - match1.indexWord1) + 3 + (match2.indexWord1 - 1);
    }

    private static IndexMatch matchWords(String word1, String word2) {
        for (int i = 0; i < word1.length(); i++) {
            char character = word1.charAt(i);

            for (int j = 0; j < word2.length(); j++) {
                if (character == word2.charAt(j)) {
                    return new IndexMatch(i, j);
                }
            }
        }
        return null;
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