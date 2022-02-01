package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/02/22.
 */
public class BoggleBlitz {

    private static class Word implements Comparable<Word> {
        String value;

        public Word(String value) {
            this.value = value;
        }

        @Override
        public int compareTo(Word other) {
            if (value.length() != other.value.length()) {
                return Integer.compare(value.length(), other.value.length());
            }
            return value.compareTo(other.value);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            Word word = (Word) other;
            return Objects.equals(value, word.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            Cell cell = (Cell) other;
            return row == cell.row && column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            int dimension = FastReader.nextInt();
            char[][] grid = new char[dimension][dimension];

            for (int i = 0; i < dimension; i++) {
                grid[i] = FastReader.getLine().toCharArray();
            }

            Set<Word> words = new HashSet<>();
            computeWords(grid, words, neighborRows, neighborColumns);

            List<Word> wordList = new ArrayList<>(words);
            Collections.sort(wordList);
            for (Word word : wordList) {
                outputWriter.printLine(word.value);
            }
        }
        outputWriter.flush();
    }

    private static void computeWords(char[][] grid, Set<Word> words, int[] neighborRows,
                                     int[] neighborColumns) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                Cell cell = new Cell(row, column);
                Set<Integer> visited = new HashSet<>();
                int cellId = computeCellId(grid, row, column);

                visited.add(cellId);
                StringBuilder word = new StringBuilder();
                word.append(grid[row][column]);
                search(grid, words, neighborRows, neighborColumns, visited, cell, word);
            }
        }
    }

    private static void search(char[][] grid, Set<Word> words, int[] neighborRows, int[] neighborColumns,
                               Set<Integer> visited, Cell cell, StringBuilder word) {
        char currentChar = grid[cell.row][cell.column];

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = cell.row + neighborRows[i];
            int neighborColumn = cell.column + neighborColumns[i];
            int cellId = computeCellId(grid, neighborRow, neighborColumn);

            if (isValid(grid, neighborRow, neighborColumn)
                    && !visited.contains(cellId)
                    && grid[neighborRow][neighborColumn] > currentChar) {
                StringBuilder nextWord = new StringBuilder(word.toString());
                nextWord.append(grid[neighborRow][neighborColumn]);

                if (nextWord.length() >= 3) {
                    int lastIndex = nextWord.length() - 3;
                    for (int j = 0; j <= lastIndex; j++) {
                        String finalWord = nextWord.substring(j);
                        words.add(new Word(finalWord));
                    }
                }
                visited.add(cellId);
                Cell nextCell = new Cell(neighborRow, neighborColumn);
                search(grid, words, neighborRows, neighborColumns, visited, nextCell, nextWord);
                visited.remove(cellId);
            }
        }
    }

    private static int computeCellId(char[][] grid, int row, int column) {
        return row * grid.length + column;
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
