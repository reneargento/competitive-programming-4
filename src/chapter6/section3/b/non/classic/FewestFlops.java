package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/08/2026.
 */
public class FewestFlops {

    private static final int WILDCARD = 26;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int k = FastReader.nextInt();
            char[] string = FastReader.next().toCharArray();

            int minimumChunks = countMinimumChunks(k, string);
            outputWriter.printLine(minimumChunks);
        }
        outputWriter.flush();
    }

    private static int countMinimumChunks(int k, char[] string) {
        // dp[section id][previous end character] = minimum chunks
        int sections = string.length / k;
        int[][] dp = new int[sections][27];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return countMinimumChunks(k, string, dp, 0, WILDCARD);
    }

    private static int countMinimumChunks(int k, char[] string, int[][] dp, int sectionIndex,
                                          int previousEndCharacterId) {
        if (sectionIndex == string.length / k) {
            return 0;
        }
        if (dp[sectionIndex][previousEndCharacterId] != -1) {
            return dp[sectionIndex][previousEndCharacterId];
        }

        int minimumChunks = Integer.MAX_VALUE;
        Set<Character> characterSet = getCharacterSet(string, sectionIndex * k, k);

        for (char startCharacter : characterSet) {
            for (char endCharacter : characterSet) {
                if (startCharacter == endCharacter && characterSet.size() != 1) {
                    continue;
                }

                int startCharacterId = getCharacterId(startCharacter);
                int endCharacterId = getCharacterId(endCharacter);
                int currentChunks = characterSet.size();
                if (previousEndCharacterId == startCharacterId) {
                    currentChunks--;
                }
                int chunks = currentChunks + countMinimumChunks(k, string, dp, sectionIndex + 1, endCharacterId);
                minimumChunks = Math.min(minimumChunks, chunks);
            }
        }
        dp[sectionIndex][previousEndCharacterId] = minimumChunks;
        return dp[sectionIndex][previousEndCharacterId];
    }

    private static Set<Character> getCharacterSet(char[] string, int startIndex, int length) {
        Set<Character> characterSet = new HashSet<>();
        for (int i = startIndex; i < startIndex + length; i++) {
            characterSet.add(string[i]);
        }
        return characterSet;
    }

    private static int getCharacterId(char character) {
        return character - 'a';
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