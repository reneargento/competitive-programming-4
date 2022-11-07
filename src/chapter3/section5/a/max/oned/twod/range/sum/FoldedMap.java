package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/11/22.
 */
public class FoldedMap {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null && !line.isEmpty()) {
            String[] data = line.split(" ");
            int imageHeight = Integer.parseInt(data[0]);
            int imageWidth = Integer.parseInt(data[1]);
            int tileHeight = Integer.parseInt(data[2]);
            int tileWidth = Integer.parseInt(data[3]);

            int heightOffset = tileHeight * 2;
            int widthOffset = tileWidth * 2;
            int[][] image = new int[imageHeight + heightOffset][imageWidth + widthOffset];

            for (int row = tileHeight; row < image.length - tileHeight; row++) {
                String rowData = FastReader.next();
                for (int column = tileWidth; column < image[row].length - tileWidth; column++) {
                    int charIndex = column - tileWidth;
                    image[row][column] = rowData.charAt(charIndex) == 'X' ? 1 : 0;
                }
            }

            int minimalTilesNeeded = computeMinimalTilesNeeded(image, tileHeight, tileWidth);
            outputWriter.printLine(minimalTilesNeeded);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimalTilesNeeded(int[][] image, int tileHeight, int tileWidth) {
        computePrefixSum(image);
        int minimalTilesNeeded = Integer.MAX_VALUE;

        for (int row = 0; row < tileHeight; row++) {
            for (int column = 0; column < tileWidth; column++) {
                int tilesUsed = 0;
                for (int tileRow = row; tileRow <= image.length - tileHeight; tileRow += tileHeight) {
                    for (int tileColumn = column; tileColumn <= image[tileRow].length - tileWidth; tileColumn += tileWidth) {
                        int row2 = tileRow + tileHeight - 1;
                        int column2 = tileColumn + tileWidth - 1;
                        int rangeSum = rangeSumQuery(image, tileRow, tileColumn, row2, column2);
                        tilesUsed += Math.min(rangeSum, 1);
                    }
                }
                minimalTilesNeeded = Math.min(minimalTilesNeeded, tilesUsed);
            }
        }
        return minimalTilesNeeded;
    }

    private static void computePrefixSum(int[][] image) {
        for (int row = 0; row < image.length; row++) {
            for (int column = 0; column < image[row].length; column++) {
                int value = image[row][column];
                if (row > 0) {
                    value += image[row - 1][column];
                }
                if (column > 0) {
                    value += image[row][column - 1];
                }
                if (row > 0 && column > 0) {
                    value -= image[row - 1][column - 1];
                }
                image[row][column] = value;
            }
        }
    }

    private static int rangeSumQuery(int[][] image, int row1, int column1, int row2, int column2) {
        int sum = image[row2][column2];
        if (row1 > 0) {
            sum -= image[row1 - 1][column2];
        }
        if (column1 > 0) {
            sum -= image[row2][column1 - 1];
        }
        if (row1 > 0 && column1 > 0) {
            sum += image[row1 - 1][column1 - 1];
        }
        return sum;
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
