package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/03/23.
 */
public class Terraces {

    private static class DFSResult {
        int componentSize;
        boolean isFlatArea;

        public DFSResult(int componentSize, boolean isFlatArea) {
            this.componentSize = componentSize;
            this.isFlatArea = isFlatArea;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int columns = FastReader.nextInt();
        int rows = FastReader.nextInt();
        int[][] garden = new int[rows][columns];

        for (int row = 0; row < garden.length; row++) {
            for (int column = 0; column < garden[0].length; column++) {
                garden[row][column] = FastReader.nextInt();
            }
        }

        int landToGrowRice = computeLand(garden);
        outputWriter.printLine(landToGrowRice);
        outputWriter.flush();
    }

    private static int computeLand(int[][] garden) {
        int landToGrowRice = 0;
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };
        boolean[][] visited = new boolean[garden.length][garden[0].length];

        for (int row = 0; row < garden.length; row++) {
            for (int column = 0; column < garden[0].length; column++) {

                if (!visited[row][column]) {
                    DFSResult result = depthFirstSearch(garden, visited, neighborRows, neighborColumns, row, column);
                    if (result.isFlatArea) {
                        landToGrowRice += result.componentSize;
                    }
                }
            }
        }
        return landToGrowRice;
    }

    private static DFSResult depthFirstSearch(int[][] garden, boolean[][] visited, int[] neighborRows,
                                              int[] neighborColumns, int row, int column) {
        visited[row][column] = true;
        int componentSize = 1;
        boolean isFlatArea = true;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(garden, neighborRow, neighborColumn)) {
                if (garden[neighborRow][neighborColumn] < garden[row][column]) {
                    isFlatArea = false;
                }

                if (!visited[neighborRow][neighborColumn]
                        && garden[neighborRow][neighborColumn] == garden[row][column]) {
                    DFSResult result = depthFirstSearch(garden, visited, neighborRows, neighborColumns,
                            neighborRow, neighborColumn);
                    componentSize += result.componentSize;
                    isFlatArea &= result.isFlatArea;
                }
            }
        }
        return new DFSResult(componentSize, isFlatArea);
    }

    private static boolean isValid(int[][] garden, int row, int column) {
        return row >= 0 && row < garden.length && column >= 0 && column < garden[0].length;
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
