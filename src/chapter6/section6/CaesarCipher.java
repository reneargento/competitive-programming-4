package chapter6.section6;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/09/2026.
 */
public class CaesarCipher {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String alphabet = FastReader.getLine();
            String plainTextWord = FastReader.getLine();
            String encryptedText = FastReader.getLine();

            List<Integer> shifts = computeShifts(alphabet, plainTextWord, encryptedText);
            if (shifts.isEmpty()) {
                outputWriter.printLine("no solution");
            } else if (shifts.size() == 1) {
                outputWriter.printLine("unique: " + shifts.get(0));
            } else {
                outputWriter.print("ambiguous:");
                for (Integer shift : shifts) {
                    outputWriter.print(" " + shift);
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static List<Integer> computeShifts(String alphabet, String plainTextWord, String encryptedText) {
        List<Integer> shifts = new ArrayList<>();
        Map<Character, Integer> symbolToIndexMap = computeSymbolToIndexMap(alphabet);

        for (int shift = 0; shift < alphabet.length(); shift++) {
            String shiftedWord = shiftWord(alphabet, plainTextWord, symbolToIndexMap, shift);
            RabinKarp rabinKarp = new RabinKarp(shiftedWord, true);
            List<Integer> indexesFound = rabinKarp.searchAll(encryptedText);
            if (indexesFound.size() == 1) {
                shifts.add(shift);
            }
        }
        return shifts;
    }

    private static Map<Character, Integer> computeSymbolToIndexMap(String alphabet) {
        Map<Character, Integer> symbolToIndexMap = new HashMap<>();
        for (int i = 0; i < alphabet.length(); i++) {
            symbolToIndexMap.put(alphabet.charAt(i), i);
        }
        return symbolToIndexMap;
    }

    private static String shiftWord(String alphabet, String word, Map<Character, Integer> symbolToIndexMap, int shift) {
        StringBuilder shiftedWord = new StringBuilder();
        for (char symbol : word.toCharArray()) {
            int symbolIndex = symbolToIndexMap.get(symbol);
            int alphabetIndex = (symbolIndex + shift) % alphabet.length();
            shiftedWord.append(alphabet.charAt(alphabetIndex));
        }
        return shiftedWord.toString();
    }

    private static class RabinKarp {
        private final String pattern;        // Only needed in the Las Vegas version
        private final long patternHash;
        private final int patternLength;
        private final int alphabetSize = 256;
        private long rm;               // rm = alphabetSize^(patternLength - 1) % largePrimeNumber
        private final boolean isMonteCarloVersion;
        private static final long PRIME_NUMBER = 1_000_000_007L;

        public RabinKarp(String pattern, boolean isMonteCarloVersion) {
            this.pattern = pattern;
            patternLength = pattern.length();
            this.isMonteCarloVersion = isMonteCarloVersion;

            rm = 1;
            for (int patternIndex = 1; patternIndex <= patternLength - 1; patternIndex++) {
                rm = (rm * alphabetSize) % PRIME_NUMBER;  // Compute alphabetSize^(patternLength - 1) % PRIME_NUMBER
            }                                             // for use in removing leading digit.
            patternHash = hash(pattern);
        }

        private boolean check(String text, int textIndex) {
            if (isMonteCarloVersion) {
                return true;
            }

            // Las Vegas version
            for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                if (pattern.charAt(patternIndex) != text.charAt(textIndex + patternIndex)) {
                    return false;
                }
            }
            return true;
        }

        // Horner's method applied to modular hashing
        private long hash(String key) {
            // Compute hash for key[0..patternLength - 1]
            long hash = 0;

            for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                hash = (hash * alphabetSize + key.charAt(patternIndex)) % PRIME_NUMBER;
            }
            return hash;
        }

        public List<Integer> searchAll(String text) {
            List<Integer> offsets = new ArrayList<>();

            int textLength = text.length();
            if (textLength < patternLength) {
                return offsets;
            }

            long textHash = hash(text.substring(0, patternLength));
            if (patternHash == textHash && check(text, 0)) {
                offsets.add(0);  // match
            }

            for (int textIndex = patternLength; textIndex < textLength; textIndex++) {
                // Remove leading character, add trailing character, check for match
                textHash = (textHash + PRIME_NUMBER - rm * text.charAt(textIndex - patternLength) % PRIME_NUMBER)
                        % PRIME_NUMBER;
                textHash = (textHash * alphabetSize + text.charAt(textIndex)) % PRIME_NUMBER;

                int offset = textIndex - patternLength + 1;
                if (patternHash == textHash && check(text, offset)) {
                    offsets.add(offset);  // match
                }
            }
            return offsets;
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