package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/01/24.
 */
public class GruesomeCave {

    private static class Cell implements Comparable<Cell> {
        int row;
        int column;
        int probabilities;

        public Cell(int row, int column, int probabilities) {
            this.row = row;
            this.column = column;
            this.probabilities = probabilities;
        }

        @Override
        public int compareTo(Cell other) {
            return Integer.compare(probabilities, other.probabilities);
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        char[][] map = new char[FastReader.nextInt()][FastReader.nextInt()];
        Cell entrance = null;
        Cell diamond = null;

        for (int row = 0; row < map.length; row++) {
            String columns = FastReader.getLine();
            for (int column = 0; column < columns.length(); column++) {
                char symbol = columns.charAt(column);
                if (symbol == 'E') {
                    entrance = new Cell(row, column, 0);
                } else if (symbol == 'D') {
                    diamond = new Cell(row, column, 0);
                }
                map[row][column] = symbol;
            }
        }
        double grueProbability = computeGrueProbability(map, entrance, diamond);
        outputWriter.printLine(grueProbability);
        outputWriter.flush();
    }

    private static double computeGrueProbability(char[][] map, Cell entrance, Cell diamond) {
        int[][] cellProbabilities = new int[map.length][map[0].length];
        int totalProbabilities = computeCellProbabilities(map, cellProbabilities);

        int[][] bestProbabilities = new int[map.length][map[0].length];
        for (int[] values : bestProbabilities) {
            Arrays.fill(values, Integer.MAX_VALUE);
        }
        bestProbabilities[entrance.row][entrance.column] = 0;
        PriorityQueue<Cell> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(entrance);

        while (!priorityQueue.isEmpty()) {
            Cell cell = priorityQueue.poll();
            int row = cell.row;
            int column = cell.column;

            if (row == diamond.row && column == diamond.column) {
                return cell.probabilities / (double) totalProbabilities;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(map, neighborRow, neighborColumn)
                        && map[neighborRow][neighborColumn] != '#') {
                    int nextProbability = bestProbabilities[row][column] + cellProbabilities[neighborRow][neighborColumn];
                    if (bestProbabilities[neighborRow][neighborColumn] > nextProbability) {
                        bestProbabilities[neighborRow][neighborColumn] = nextProbability;
                        priorityQueue.offer(new Cell(neighborRow, neighborColumn, nextProbability));
                    }
                }
            }
        }
        return -1;
    }

    private static int computeCellProbabilities(char[][] map, int[][] cellProbabilities) {
        int totalProbabilities = 0;

        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[0].length; column++) {
                if (map[row][column] != ' ') {
                    continue;
                }
                int emptyNeighbors = 0;

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row + neighborRows[i];
                    int neighborColumn = column + neighborColumns[i];

                    if (isValid(map, neighborRow, neighborColumn)
                            && map[neighborRow][neighborColumn] == ' ') {
                        emptyNeighbors++;
                    }
                }
                cellProbabilities[row][column] = emptyNeighbors;
                totalProbabilities += emptyNeighbors;
            }
        }
        return totalProbabilities;
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
