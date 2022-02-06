package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/02/22.
 */
public class DetermineTheCombination {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            char[] characters = data[0].toCharArray();

            int combinationSize = Integer.parseInt(data[1]);
            computeCombinations(characters, combinationSize, outputWriter);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void computeCombinations(char[] characters, int combinationSize,
                                            OutputWriter outputWriter) {
        Arrays.sort(characters);
        int uniqueCharactersCount = countUniqueCharacters(characters);

        char[] uniqueCharacters = new char[uniqueCharactersCount];
        int[] charactersFrequency = new int[uniqueCharactersCount];

        int characterIndex = 0;
        uniqueCharacters[characterIndex] = characters[0];
        charactersFrequency[characterIndex]++;

        for (int i = 1; i < characters.length; i++) {
            if (characters[i] != characters[i - 1]) {
                characterIndex++;
                uniqueCharacters[characterIndex] = characters[i];
            }
            charactersFrequency[characterIndex]++;
        }

        char[] currentCharacters = new char[combinationSize];
        computeCombinations(uniqueCharacters, combinationSize, outputWriter, charactersFrequency,
                currentCharacters, 0, 0);
    }

    private static int countUniqueCharacters(char[] characters) {
        int uniqueCharacters = 0;
        boolean[] visited = new boolean[26];

        for (char character : characters) {
            int index = character - 'a';
            if (!visited[index]) {
                visited[index] = true;
                uniqueCharacters++;
            }
        }
        return uniqueCharacters;
    }

    private static void computeCombinations(char[] uniqueCharacters, int combinationSize, OutputWriter outputWriter,
                                            int[] charactersFrequency, char[] currentCharacters,
                                            int index, int uniqueCharacterIndex) {
        if (index == combinationSize) {
            String combination = new String(currentCharacters);
            outputWriter.printLine(combination);
            return;
        }

        for (int i = uniqueCharacterIndex; i < uniqueCharacters.length; i++) {
            if (charactersFrequency[i] > 0) {
                charactersFrequency[i]--;
                currentCharacters[index] = uniqueCharacters[i];
                computeCombinations(uniqueCharacters, combinationSize, outputWriter, charactersFrequency,
                        currentCharacters, index + 1, i);
                charactersFrequency[i]++;
            }
        }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
