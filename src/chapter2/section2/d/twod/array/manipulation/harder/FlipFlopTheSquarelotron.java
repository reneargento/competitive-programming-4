package chapter2.section2.d.twod.array.manipulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/02/21.
 */
public class FlipFlopTheSquarelotron {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int order = FastReader.nextInt();
            int[][] squarelotron = new int[order][order];

            for (int row = 0; row < squarelotron.length; row++) {
                for (int column = 0; column < squarelotron[0].length; column++) {
                    squarelotron[row][column] = FastReader.nextInt();
                }
            }

            int rings = (int) Math.ceil(order / 2.0);
            for (int ring = 0; ring < rings; ring++) {
                int endRow = squarelotron.length - 1 - ring;
                int endColumn = squarelotron[0].length - 1 - ring;

                int moves = FastReader.nextInt();
                for (int i = 0; i < moves; i++) {
                    int move = FastReader.nextInt();

                    switch (move) {
                        case 1: flipUpsideDown(squarelotron, ring, ring, endRow, endColumn); break;
                        case 2: flipLeftRight(squarelotron, ring, ring, endRow, endColumn); break;
                        case 3: flipThroughMainDiagonal(squarelotron, ring, ring, endRow, endColumn); break;
                        case 4: flipThroughMainInverseDiagonal(squarelotron, ring, ring, endRow, endColumn);
                    }
                }
            }
            printSquarelotron(squarelotron, outputWriter);
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void flipUpsideDown(int[][] squarelotron, int row, int column, int endRow, int endColumn) {
        for (int c = column; c <= endColumn; c++) {
            int aux = squarelotron[row][c];
            squarelotron[row][c] = squarelotron[endRow][c];
            squarelotron[endRow][c] = aux;
        }
        int[] columnsToProcess = new int[]{ column, endColumn };
        int middleRow = row + (endRow - row) / 2;

        for (int c = 0; c < 2; c++) {
            int columnToProcess = columnsToProcess[c];

            for (int r = row + 1, invRow = endRow - 1; r <= middleRow; r++, invRow--) {
                int aux = squarelotron[r][columnToProcess];
                squarelotron[r][columnToProcess] = squarelotron[invRow][columnToProcess];
                squarelotron[invRow][columnToProcess] = aux;
            }
        }
    }

    private static void flipLeftRight(int[][] squarelotron, int row, int column, int endRow, int endColumn) {
        for (int r = row; r <= endRow; r++) {
            int aux = squarelotron[r][column];
            squarelotron[r][column] = squarelotron[r][endColumn];
            squarelotron[r][endColumn] = aux;
        }
        int[] rowsToProcess = new int[]{ row, endRow };
        int middleColumn = column + (endColumn - column) / 2;

        for (int r = 0; r < 2; r++) {
            int rowToProcess = rowsToProcess[r];

            for (int c = column + 1, invColumn = endColumn - 1; c <= middleColumn; c++, invColumn--) {
                int aux = squarelotron[rowToProcess][c];
                squarelotron[rowToProcess][c] = squarelotron[rowToProcess][invColumn];
                squarelotron[rowToProcess][invColumn] = aux;
            }
        }
    }

    private static void flipThroughMainDiagonal(int[][] squarelotron, int row, int column, int endRow, int endColumn) {
        for (int c = column + 1; c <= endColumn; c++) {
            int aux = squarelotron[row][c];
            squarelotron[row][c] = squarelotron[c][column];
            squarelotron[c][column] = aux;
        }

        for (int r = row + 1; r < endRow; r++) {
            int aux = squarelotron[r][endColumn];
            squarelotron[r][endColumn] = squarelotron[endRow][r];
            squarelotron[endRow][r] = aux;
        }
    }

    private static void flipThroughMainInverseDiagonal(int[][] squarelotron, int row, int column, int endRow,
                                                       int endColumn) {
        for (int c = endColumn - 1; c >= column; c--) {
            int inverseRow = squarelotron.length - 1 - c;

            int aux = squarelotron[row][c];
            squarelotron[row][c] = squarelotron[inverseRow][endColumn];
            squarelotron[inverseRow][endColumn] = aux;
        }

        for (int r = row + 1; r < endRow; r++) {
            int inverseColumn = squarelotron[0].length - 1 - r;

            int aux = squarelotron[r][column];
            squarelotron[r][column] = squarelotron[endRow][inverseColumn];
            squarelotron[endRow][inverseColumn] = aux;
        }
    }

    private static void printSquarelotron(int[][] squarelotron, OutputWriter outputWriter) {
        for (int row = 0; row < squarelotron.length; row++) {
            for (int column = 0; column < squarelotron[0].length; column++) {
                outputWriter.print(squarelotron[row][column]);

                if (column < squarelotron[0].length - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
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
