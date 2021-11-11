package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/11/21.
 */
public class NPuzzle {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String[][] puzzle = new String[4][4];

        for (int r = 0; r < puzzle.length; r++) {
            puzzle[r] = FastReader.next().split("");
        }
        Map<String, Cell> correctPositionsMap = getCorrectPositionsMap();

        int scatter = computeScatter(puzzle, correctPositionsMap);
        outputWriter.printLine(scatter);
        outputWriter.flush();
    }

    private static Map<String, Cell> getCorrectPositionsMap() {
        Map<String, Cell> correctPositionsMap = new HashMap<>();
        correctPositionsMap.put("A", new Cell(0, 0));
        correctPositionsMap.put("B", new Cell(0, 1));
        correctPositionsMap.put("C", new Cell(0, 2));
        correctPositionsMap.put("D", new Cell(0, 3));
        correctPositionsMap.put("E", new Cell(1, 0));
        correctPositionsMap.put("F", new Cell(1, 1));
        correctPositionsMap.put("G", new Cell(1, 2));
        correctPositionsMap.put("H", new Cell(1, 3));
        correctPositionsMap.put("I", new Cell(2, 0));
        correctPositionsMap.put("J", new Cell(2, 1));
        correctPositionsMap.put("K", new Cell(2, 2));
        correctPositionsMap.put("L", new Cell(2, 3));
        correctPositionsMap.put("M", new Cell(3, 0));
        correctPositionsMap.put("N", new Cell(3, 1));
        correctPositionsMap.put("O", new Cell(3, 2));
        return correctPositionsMap;
    }

    private static int computeScatter(String[][] puzzle, Map<String, Cell> correctPositionsMap) {
        int scatter = 0;

        for (int row = 0; row < puzzle.length; row++) {
            for (int column = 0; column < puzzle[0].length; column++) {
                String key = puzzle[row][column];
                if (!key.equals(".")) {
                    Cell correctCell = correctPositionsMap.get(key);
                    scatter += Math.abs(row - correctCell.row) + Math.abs(column - correctCell.column);
                }
            }
        }
        return scatter;
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
