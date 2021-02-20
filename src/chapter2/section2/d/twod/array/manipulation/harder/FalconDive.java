package chapter2.section2.d.twod.array.manipulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/02/21.
 */
public class FalconDive {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        char falconPiece = FastReader.next().charAt(1);

        char[][] frame1 = readFrame(rows, columns);
        char[][] frame2 = readFrame(rows, columns);

        char[][] resultFrame = computeNextFrame(frame1, frame2, falconPiece);
        printFrame(resultFrame);
    }

    private static char[][] computeNextFrame(char[][] frame1, char[][] frame2, char falconPiece) {
        Cell delta = getDelta(frame1, frame2, falconPiece);
        if (delta == null) {
            return frame1;
        }

        char[][] resultFrame = new char[frame2.length][frame2[0].length];

        for (int row = 0; row < resultFrame.length; row++) {
            int previousFalconRow = row - delta.row;
            for (int column = 0; column < resultFrame[0].length; column++) {
                int previousFalconColumn = column - delta.column;

                if (isValid(frame2, previousFalconRow, previousFalconColumn)
                        && frame2[previousFalconRow][previousFalconColumn] == falconPiece) {
                    resultFrame[row][column] = falconPiece;
                } else {
                    if (frame2[row][column] != falconPiece) {
                        resultFrame[row][column] = frame2[row][column];
                    } else {
                        resultFrame[row][column] = frame1[row][column];
                    }
                }
            }
        }
        return resultFrame;
    }

    private static Cell getDelta(char[][] frame1, char[][] frame2, char falconPiece) {
        Cell firstAppearance = getFirstFalconPiece(frame1, falconPiece);
        Cell secondAppearance = getFirstFalconPiece(frame2, falconPiece);

        if (firstAppearance == null || secondAppearance == null) {
            return null;
        }

        int rowDelta = secondAppearance.row - firstAppearance.row;
        int columnDelta = secondAppearance.column - firstAppearance.column;
        return new Cell(rowDelta, columnDelta);
    }

    private static Cell getFirstFalconPiece(char[][] frame, char falconPiece) {
        for (int row = 0; row < frame.length; row++) {
            for (int column = 0; column < frame[0].length; column++) {
                if (frame[row][column] == falconPiece) {
                    return new Cell(row, column);
                }
            }
        }
        return null;
    }

    private static char[][] readFrame(int rows, int columns) throws IOException {
        char[][] frame = new char[rows][columns];
        for (int row = 0; row < frame.length; row++) {
            frame[row] = FastReader.next().toCharArray();
        }
        return frame;
    }

    private static void printFrame(char[][] frame) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        for (int row = 0; row < frame.length; row++) {
            for (int column = 0; column < frame[0].length; column++) {
                outputWriter.print(frame[row][column]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static boolean isValid(char[][] frame, int row, int column) {
        return row >= 0 && row < frame.length && column >= 0 && column < frame[0].length;
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
            for (int i = 0; i < objects.length; i++) {
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
