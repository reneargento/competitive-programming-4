package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/01/23.
 */
public class Watersheds {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[][] map = new int[FastReader.nextInt()][FastReader.nextInt()];
            for (int row = 0; row < map.length; row++) {
                for (int column = 0; column < map[0].length; column++) {
                    map[row][column] = FastReader.nextInt();
                }
            }

            int[][] labels = computeLabels(map);
            outputWriter.printLine(String.format("Case #%d:", t));
            for (int row = 0; row < labels.length; row++) {
                for (int column = 0; column < labels[0].length; column++) {
                    char label = (char) ('a' + labels[row][column]);
                    outputWriter.print(label);
                    if (column != labels[0].length - 1) {
                        outputWriter.print(" ");
                    }
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static int[][] computeLabels(int[][] map) {
        int[][] labels = new int[map.length][map[0].length];
        for (int[] values : labels) {
            Arrays.fill(values, -1);
        }
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };
        int label = 0;

        for (int row = 0; row < labels.length; row++) {
            for (int column = 0; column < labels[0].length; column++) {
                if (labels[row][column] != -1) {
                    continue;
                }

                int finalLabel = floodFill(map, labels, neighborRows, neighborColumns, label, row, column);
                labels[row][column] = finalLabel;
                if (finalLabel == label) {
                    label++;
                }
            }
        }
        return labels;
    }

    private static int floodFill(int[][] map, int[][] labels, int[] neighborRows, int[] neighborColumns, int label,
                                 int row, int column) {
        int bestDirection = -1;
        int smallestAltitude = Integer.MAX_VALUE;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(map, neighborRow, neighborColumn)
                    && map[neighborRow][neighborColumn] < map[row][column]) {
                if (map[neighborRow][neighborColumn] < smallestAltitude) {
                    smallestAltitude = map[neighborRow][neighborColumn];
                    bestDirection = i;
                }
            }
        }

        int finalLabel;
        if (bestDirection != -1) {
            int neighborLabel = labels[row + neighborRows[bestDirection]][column + neighborColumns[bestDirection]];
            if (neighborLabel != -1) {
                finalLabel = neighborLabel;
            } else {
                finalLabel = floodFill(map, labels, neighborRows, neighborColumns, label,
                        row + neighborRows[bestDirection], column + neighborColumns[bestDirection]);
            }
        } else {
            // Sink found
            finalLabel = label;
        }
        labels[row][column] = finalLabel;
        return labels[row][column];
    }

    private static boolean isValid(int[][] map, int row, int column) {
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
