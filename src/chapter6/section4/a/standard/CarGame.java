package chapter6.section4.a.standard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/08/2026.
 */
public class CarGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] dictionary = new String[FastReader.nextInt()];
        int licensePlates = FastReader.nextInt();

        for (int i = 0; i < dictionary.length; i++) {
            dictionary[i] = FastReader.next();
        }
        List<Integer>[][] positions = preProcessPositions(dictionary);

        for (int i = 0; i < licensePlates; i++) {
            String licensePlate = FastReader.next().toLowerCase();
            String result = searchWord(dictionary, licensePlate, positions);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String searchWord(String[] dictionary, String licensePlate, List<Integer>[][] positions) {
        for (int i = 0; i < positions.length; i++) {
            if (isMatch(licensePlate, positions[i])) {
                return dictionary[i];
            }
        }
        return "No valid word";
    }

    private static boolean isMatch(String licencePlate, List<Integer>[] positions) {
        int[] frequencyUsed = new int[26];
        int lastIndexUsed = -1;

        for (int i = 0; i < licencePlate.length(); i++) {
            int characterIndex = licencePlate.charAt(i) - 'a';
            if (positions[characterIndex] == null) {
                return false;
            }
            List<Integer> indexes = positions[characterIndex];
            if (indexes.size() <= frequencyUsed[characterIndex]) {
                return false;
            }

            boolean found = false;
            while (frequencyUsed[characterIndex] < indexes.size()) {
                int indexFound = indexes.get(frequencyUsed[characterIndex]);
                frequencyUsed[characterIndex]++;

                if (indexFound > lastIndexUsed) {
                    lastIndexUsed = indexFound;
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private static List<Integer>[][] preProcessPositions(String[] dictionary) {
        List<Integer>[][] positions = new ArrayList[dictionary.length][3];

        for (int i = 0; i < dictionary.length; i++) {
            String word = dictionary[i];
            List<Integer>[] characterPositions = new ArrayList[26];
            for (int c = 0; c < word.length(); c++) {
                int characterIndex = word.charAt(c) - 'a';
                if (characterPositions[characterIndex] == null) {
                    characterPositions[characterIndex] = new ArrayList<>();
                }
                characterPositions[characterIndex].add(c);
            }
            positions[i] = characterPositions;
        }
        return positions;
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