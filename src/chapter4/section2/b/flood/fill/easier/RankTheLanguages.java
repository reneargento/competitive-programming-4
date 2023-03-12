package chapter4.section2.b.flood.fill.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/03/23.
 */
public class RankTheLanguages {

    private static class LanguageRank implements Comparable<LanguageRank> {
        char language;
        int frequency;

        public LanguageRank(char language, int frequency) {
            this.language = language;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(LanguageRank other) {
            if (frequency != other.frequency) {
                return Integer.compare(other.frequency, frequency);
            }
            return Character.compare(language, other.language);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int t = 1; t <= tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            char[][] map = new char[rows][columns];
            for (int row = 0; row < map.length; row++) {
                map[row] = FastReader.next().toCharArray();
            }

            List<LanguageRank> ranks = computeRanks(map, neighborRows, neighborColumns);
            outputWriter.printLine(String.format("World #%d", t));
            for (LanguageRank rank : ranks) {
                outputWriter.printLine(String.format("%c: %d", rank.language, rank.frequency));
            }
        }
        outputWriter.flush();
    }

    private static List<LanguageRank> computeRanks(char[][] map, int[] neighborRows, int[] neighborColumns) {
        Map<Character, Integer> languageFrequencyMap = new HashMap<>();

        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[0].length; column++) {
                if (map[row][column] != '.') {
                    int frequency = languageFrequencyMap.getOrDefault(map[row][column], 0);
                    languageFrequencyMap.put(map[row][column], frequency + 1);
                    clearState(map, neighborRows, neighborColumns, map[row][column], row, column);
                }
            }
        }

        List<LanguageRank> ranks = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : languageFrequencyMap.entrySet()) {
            ranks.add(new LanguageRank(entry.getKey(), entry.getValue()));
        }
        Collections.sort(ranks);
        return ranks;
    }

    private static void clearState(char[][] map, int[] neighborRows, int[] neighborColumns, char language, int row,
                                   int column) {
        map[row][column] = '.';
        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(map, neighborRow, neighborColumn) && map[neighborRow][neighborColumn] == language) {
                clearState(map, neighborRows, neighborColumns, language, neighborRow, neighborColumn);
            }
        }
    }

    private static boolean isValid(char[][] map, int row, int column) {
        return row >= 0 && row < map.length && column >= 0 && column < map[0].length;
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
