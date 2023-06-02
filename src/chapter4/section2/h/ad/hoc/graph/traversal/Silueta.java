package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/05/23.
 */
public class Silueta {

    private static final int LAST_ROW = 1000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int skyscrapers = FastReader.nextInt();
        char[][] silhouette = new char[1001][1000];
        int[] heights = new int[1000];

        int minColumn = LAST_ROW;
        int minRow = LAST_ROW;
        int maxColumn = 0;
        initPainting(silhouette);

        for (int i = 0; i < skyscrapers; i++) {
            int leftColumn = FastReader.nextInt() - 1;
            int rightColumn = FastReader.nextInt() - 2;
            int height = FastReader.nextInt();
            for (int column = leftColumn; column <= rightColumn; column++) {
                heights[column] = Math.max(heights[column], height);
            }

            int topRow = LAST_ROW - height;
            minRow = Math.min(minRow, topRow);
            minColumn = Math.min(minColumn, leftColumn);
            maxColumn = Math.max(maxColumn, rightColumn);
        }
        maxColumn++;

        int perimeter = computePerimeter(heights, minColumn, maxColumn);
        outputWriter.printLine(perimeter);

        for (int column = minColumn; column <= maxColumn; column++) {
            int previousHeight = 1;
            if (column != minColumn) {
                previousHeight = heights[column - 1];
            }

            if (heights[column] >= previousHeight) {
                for (int height = previousHeight; height <= heights[column]; height++) {
                    silhouette[LAST_ROW - height][column] = '#';
                }
            } else {
                silhouette[LAST_ROW - heights[column]][column] = '#';
                for (int height = heights[column]; height <= heights[column - 1]; height++) {
                    silhouette[LAST_ROW - height][column - 1] = '#';
                }
            }
        }
        Arrays.fill(silhouette[LAST_ROW], '*');

        for (int row = minRow; row < silhouette.length; row++) {
            for (int column = minColumn; column < maxColumn; column++) {
                outputWriter.print(silhouette[row][column]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static void initPainting(char[][] silhouette) {
        int lastRow = silhouette.length - 1;
        for (int row = 0; row < lastRow; row++) {
            Arrays.fill(silhouette[row], '.');
        }
    }

    private static int computePerimeter(int[] heights, int minColumn, int maxColumn) {
        int perimeter = 0;
        for (int column = minColumn; column <= maxColumn; column++) {
            if (heights[column] > 0) {
                perimeter++;
            }

            int previousHeight = 0;
            if (column != minColumn) {
                previousHeight = heights[column - 1];
            }
            perimeter += Math.abs(heights[column] - previousHeight);
        }
        return perimeter;
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
