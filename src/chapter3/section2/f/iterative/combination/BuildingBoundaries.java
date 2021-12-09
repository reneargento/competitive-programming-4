package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/12/21.
 */
public class BuildingBoundaries {

    private static class Rectangle {
        long width;
        long height;

        public Rectangle(long width, long height) {
            this.width = width;
            this.height = height;
        }

        Rectangle rotate() {
            return new Rectangle(height, width);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Rectangle[] rectangles = new Rectangle[3];
            for (int i = 0; i < rectangles.length; i++) {
                rectangles[i] = new Rectangle(FastReader.nextInt(), FastReader.nextInt());
            }

            long minimumArea = getMinimumArea(rectangles);
            outputWriter.printLine(minimumArea);
        }
        outputWriter.flush();
    }

    private static long getMinimumArea(Rectangle[] rectangles) {
        int[] rectanglesIndex = { 0, 1, 2 };
        return getMinimumArea(rectangles, rectanglesIndex, 0, 0);
    }

    private static long getMinimumArea(Rectangle[] rectangles, int[] rectanglesIndex, int mask,
                                       int permutationIndex) {
        if (permutationIndex == rectanglesIndex.length) {
            Rectangle[] selectedRectangles = selectRectangles(rectangles, rectanglesIndex);
            Rectangle[] currentRectangles = new Rectangle[rectangles.length];
            return getMinimumArea(selectedRectangles, currentRectangles, 0);
        }

        long minimumArea = Long.MAX_VALUE;
        for (int i = 0; i < rectangles.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                rectanglesIndex[permutationIndex] = i;
                long area = getMinimumArea(rectangles, rectanglesIndex, newMask, permutationIndex + 1);
                minimumArea = Math.min(minimumArea, area);
            }
        }
        return minimumArea;
    }

    private static Rectangle[] selectRectangles(Rectangle[] rectangles, int[] rectanglesIndex) {
        Rectangle[] selectedRectangles = new Rectangle[rectangles.length];
        for (int i = 0; i < rectanglesIndex.length; i++) {
            int index = rectanglesIndex[i];
            selectedRectangles[i] = rectangles[index];
        }
        return selectedRectangles;
    }

    private static long getMinimumArea(Rectangle[] rectangles, Rectangle[] currentRectangles,
                                       int combinationIndex) {
        if (combinationIndex == rectangles.length) {
            return getArea(currentRectangles);
        }

        Rectangle originalRectangle = rectangles[combinationIndex];
        Rectangle rotatedRectangle = originalRectangle.rotate();

        currentRectangles[combinationIndex] = originalRectangle;
        long area1 = getMinimumArea(rectangles, currentRectangles, combinationIndex + 1);

        currentRectangles[combinationIndex] = rotatedRectangle;
        long area2 = getMinimumArea(rectangles, currentRectangles, combinationIndex + 1);
        return Math.min(area1, area2);
    }

    private static long getArea(Rectangle[] rectangles) {
        long maxHeight = Math.max(rectangles[0].height, rectangles[1].height);
        maxHeight = Math.max(maxHeight, rectangles[2].height);

        // Buildings one on the side of the other
        long area1 = (rectangles[0].width + rectangles[1].width + rectangles[2].width) * maxHeight;

        // Building 1 on top of building 0 and building 2 on the side
        long maxHeightWithRectanglesOnSides = Math.max((rectangles[0].height + rectangles[1].height),
                rectangles[2].height);
        long maxWidthWithRectanglesOnSides = rectangles[0].width + rectangles[2].width;
        if (rectangles[1].width > rectangles[0].width) {
            if (rectangles[2].height > rectangles[0].height) {
                maxWidthWithRectanglesOnSides = Math.max(maxWidthWithRectanglesOnSides,
                        rectangles[1].width + rectangles[2].width);
            } else {
                maxWidthWithRectanglesOnSides = Math.max(maxWidthWithRectanglesOnSides, rectangles[1].width);
            }
        }
        long area2 = maxHeightWithRectanglesOnSides * maxWidthWithRectanglesOnSides;

        return Math.min(area1, area2);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
