package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/09/22.
 */
public class Prozor {

    private static final int EMPTY_PIXEL = 0;
    private static final int FLY_PIXEL = 1;
    private static final int HORIZONTAL_RACKET_PIXEL = 2;
    private static final int VERTICAL_RACKET_PIXEL = 3;
    private static final int ANGLE_RACKET_PIXEL = 4;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[][] picture = new int[FastReader.nextInt()][FastReader.nextInt()];
        int racketLength = FastReader.nextInt() - 2;

        for (int i = 0; i < picture.length; i++) {
            String row = FastReader.next();
            for (int j = 0; j < row.length(); j++) {
                picture[i][j] = row.charAt(j) == '*' ? FLY_PIXEL : EMPTY_PIXEL;
            }
        }

        int maxFlies = computeMaxFliesToKill(picture, racketLength);
        outputWriter.printLine(maxFlies);
        for (int row = 0; row < picture.length; row++) {
            for (int column = 0; column < picture[0].length; column++) {
                char pixel;
                switch (picture[row][column]) {
                    case EMPTY_PIXEL: pixel = '.'; break;
                    case FLY_PIXEL: pixel = '*'; break;
                    case HORIZONTAL_RACKET_PIXEL: pixel = '-'; break;
                    case VERTICAL_RACKET_PIXEL: pixel = '|'; break;
                    default: pixel = '+';
                }
                outputWriter.print(pixel);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static int computeMaxFliesToKill(int[][] picture, int racketLength) {
        int maxFlies = 0;
        int topRow = 0;
        int leftColumn = 0;

        int[][] matrixSum = new int[picture.length][picture[0].length];
        for (int row = 1; row < picture.length; row++) {
            for (int column = 1; column < picture[0].length; column++) {
                matrixSum[row][column] = picture[row][column] + matrixSum[row - 1][column] + matrixSum[row][column - 1]
                        - matrixSum[row - 1][column - 1];
            }
        }

        for (int startRow = 1; startRow < picture.length - racketLength; startRow++) {
            int endRow = startRow + racketLength - 1;
            for (int startColumn = 1; startColumn < picture[0].length - racketLength; startColumn++) {
                int endColumn = startColumn + racketLength - 1;
                int flies = matrixSum[endRow][endColumn] - matrixSum[startRow - 1][endColumn] -
                        matrixSum[endRow][startColumn - 1] + matrixSum[startRow - 1][startColumn - 1];
                if (flies > maxFlies) {
                    maxFlies = flies;
                    topRow = startRow;
                    leftColumn = startColumn;
                }
            }
        }

        markRacketPosition(picture, racketLength, topRow - 1, leftColumn - 1);
        return maxFlies;
    }

    private static void markRacketPosition(int[][] picture, int racketLength, int topRow, int leftColumn) {
        int bottomRow = topRow + racketLength + 1;
        int rightColumn = leftColumn + racketLength + 1;

        picture[topRow][leftColumn] = ANGLE_RACKET_PIXEL;
        picture[topRow][rightColumn] = ANGLE_RACKET_PIXEL;
        picture[bottomRow][leftColumn] = ANGLE_RACKET_PIXEL;
        picture[bottomRow][rightColumn] = ANGLE_RACKET_PIXEL;

        markRacketPosition(picture, topRow, leftColumn + 1, topRow, rightColumn - 1,
                HORIZONTAL_RACKET_PIXEL);
        markRacketPosition(picture, bottomRow, leftColumn + 1, bottomRow, rightColumn - 1,
                HORIZONTAL_RACKET_PIXEL);
        markRacketPosition(picture, topRow + 1, leftColumn, bottomRow - 1, leftColumn,
                VERTICAL_RACKET_PIXEL);
        markRacketPosition(picture, topRow + 1, rightColumn, bottomRow - 1, rightColumn,
                VERTICAL_RACKET_PIXEL);
    }

    private static void markRacketPosition(int[][] picture, int startRow, int startColumn, int endRow, int endColumn,
                                           int pixel) {
        for (int row = startRow; row <= endRow; row++) {
            for (int column = startColumn; column <= endColumn; column++) {
                picture[row][column] = pixel;
            }
        }
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