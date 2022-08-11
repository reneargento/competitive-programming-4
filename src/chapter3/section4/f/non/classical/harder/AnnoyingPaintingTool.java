package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/08/22.
 */
public class AnnoyingPaintingTool {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int matrixRows = FastReader.nextInt();
        int matrixColumns = FastReader.nextInt();
        int paintRows = FastReader.nextInt();
        int paintColumns = FastReader.nextInt();

        while (matrixRows != 0 || matrixColumns != 0 || paintRows != 0 || paintColumns != 0) {
            char[][] picture = new char[matrixRows][matrixColumns];
            for (int i = 0; i < picture.length; i++) {
                picture[i] = FastReader.getLine().toCharArray();
            }
            int minimumOperations = countMinimumOperations(picture, paintRows, paintColumns);
            outputWriter.printLine(minimumOperations);

            matrixRows = FastReader.nextInt();
            matrixColumns = FastReader.nextInt();
            paintRows = FastReader.nextInt();
            paintColumns = FastReader.nextInt();
        }

        outputWriter.flush();
    }

    private static int countMinimumOperations(char[][] picture, int paintRows, int paintColumns) {
        int operations = 0;
        char[][] currentPicture = createEmptyPicture(picture.length, picture[0].length);

        for (int row = 0; row < currentPicture.length; row++) {
            for (int column = 0; column < currentPicture[0].length; column++) {
                if (currentPicture[row][column] != picture[row][column]) {
                    if (row + paintRows <= currentPicture.length
                            && column + paintColumns <= currentPicture[0].length) {
                        // Paint
                        for (int r = row; r < row + paintRows; r++) {
                            for (int c = column; c < column + paintColumns; c++) {
                                if (currentPicture[r][c] == '0') {
                                    currentPicture[r][c] = '1';
                                } else {
                                    currentPicture[r][c] = '0';
                                }
                            }
                        }
                    }
                    operations++;
                }
            }
        }

        if (arePicturesEqual(currentPicture, picture)) {
            return operations;
        } else {
            return -1;
        }
    }

    private static char[][] createEmptyPicture(int rows, int columns) {
        char[][] picture = new char[rows][columns];
        for (int row = 0; row < picture.length; row++) {
            for (int column = 0; column < picture[0].length; column++) {
                picture[row][column] = '0';
            }
        }
        return picture;
    }

    private static boolean arePicturesEqual(char[][] picture1, char[][] picture2) {
        for (int row = 0; row < picture1.length; row++) {
            for (int column = 0; column < picture1[0].length; column++) {
                if (picture1[row][column] != picture2[row][column]) {
                    return false;
                }
            }
        }
        return true;
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
