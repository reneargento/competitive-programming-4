package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/04/23.
 */
public class CoastTracker {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int robotColumn = FastReader.nextInt();
        int[] neighborRows = { -1, -1, 0, 1, 1, 1, 0, -1};
        int[] neighborColumns = { 0, -1, -1, -1, 0, 1, 1, 1 };

        while (robotColumn != -1) {
            int robotRow = FastReader.nextInt();
            int direction = FastReader.nextInt();
            boolean[][] isLand = new boolean[9][9];

            for (int i = 0; i < 8; i++) {
                int column = FastReader.nextInt();
                int row = FastReader.nextInt();
                int type = FastReader.nextInt();

                int adjustedColumn = column - (robotColumn - 1);
                int adjustedRow = row - (robotRow - 1);
                int reverseRow = 2 - adjustedRow;
                isLand[reverseRow][adjustedColumn] = (type == 1);
            }

            int nextDirection = computeNextDirection(direction, isLand, neighborRows, neighborColumns);
            outputWriter.printLine(nextDirection);
            robotColumn = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeNextDirection(int direction, boolean[][] isLand, int[] neighborRows,
                                            int[] neighborColumns) {
        for (int i = 0; i < neighborRows.length; i++) {
            int nextDirection = (direction + i + 5) % 8;
            int nextRow = neighborRows[nextDirection] + 1;
            int nextColumn = neighborColumns[nextDirection] + 1;
            if (isLand[nextRow][nextColumn]) {
                return nextDirection;
            }
        }
        return direction;
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
