package chapter5.section2.g.grid;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/04/25.
 */
public class BreakingBoard {

    private static class FrequencyChar implements Comparable<FrequencyChar> {
        char character;
        int frequency;

        public FrequencyChar(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(FrequencyChar other) {
            return Integer.compare(other.frequency, frequency);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String message = FastReader.getLine();
            int minimumCost = computeMinimumCost(message);
            outputWriter.printLine(minimumCost);
        }
        outputWriter.flush();
    }

    private static int computeMinimumCost(String message) {
        Map<Character, Integer> frequencyMap = computeFrequencyMap(message);
        FrequencyChar[] frequencyChars = computeFrequencyChars(frequencyMap);
        char[][] grid = computeGrid(frequencyChars);

        int minimumCost = 0;
        for (char character : message.toCharArray()) {
            minimumCost += computeCost(grid, character);
        }
        return minimumCost;
    }

    private static Map<Character, Integer> computeFrequencyMap(String message) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char character : message.toCharArray()) {
            if (character == ' ') {
                continue;
            }

            int currentFrequency = 0;
            if (frequencyMap.containsKey(character)) {
                currentFrequency = frequencyMap.get(character);
            }
            frequencyMap.put(character, currentFrequency + 1);
        }
        return frequencyMap;
    }

    private static FrequencyChar[] computeFrequencyChars(Map<Character, Integer> frequencyMap) {
        FrequencyChar[] frequencyChars = new FrequencyChar[frequencyMap.size()];
        int frequencyCharsIndex = 0;
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            frequencyChars[frequencyCharsIndex] = new FrequencyChar(entry.getKey(), entry.getValue());
            frequencyCharsIndex++;
        }

        Arrays.sort(frequencyChars);
        return frequencyChars;
    }

    private static char[][] computeGrid(FrequencyChar[] frequencyChars) {
        char[][] grid = new char[6][6];
        int row = 0;
        int column = 0;
        int maxRow = 0;
        int startColumn = 0;
        int frequencyCharIndex = 0;

        int secondHalfBoardRowStart = 1;

        while (row < grid.length
                && column < grid[0].length
                && frequencyCharIndex < frequencyChars.length) {
            grid[row][column] = frequencyChars[frequencyCharIndex].character;
            boolean secondHalfBoardReached = row == grid.length - 1;

            if (row == maxRow) {
                if (row != grid.length - 1) {
                    row = 0;
                    maxRow++;
                } else {
                    row = secondHalfBoardRowStart;
                    secondHalfBoardRowStart++;
                }
            } else {
                row++;
            }

            if (column == 0
                    || (secondHalfBoardReached && row == secondHalfBoardRowStart - 1)) {
                if (startColumn < grid[0].length - 1) {
                    startColumn++;
                }
                column = startColumn;
            } else {
                column--;
            }
            frequencyCharIndex++;
        }
        return grid;
    }

    private static int computeCost(char[][] grid, char character) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] == character) {
                    return row + column + 2;
                }
            }
        }
        return 0;
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
