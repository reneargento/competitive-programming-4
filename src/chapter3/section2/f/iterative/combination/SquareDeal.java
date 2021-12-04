package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/12/21.
 */
public class SquareDeal {

    private static class Rectangle {
        int width;
        int height;

        public Rectangle(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public Rectangle rotate() {
            return new Rectangle(height, width);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Rectangle[] rectangles = new Rectangle[3];
        for (int i = 0; i < rectangles.length; i++) {
            rectangles[i] = new Rectangle(FastReader.nextInt(), FastReader.nextInt());
        }

        outputWriter.printLine(canGlueTogether(rectangles) ? "YES" : "NO");
        outputWriter.flush();
    }

    private static boolean canGlueTogether(Rectangle[] rectangles) {
        Rectangle[] selectedRectangles = new Rectangle[rectangles.length];
        return canGlueTogether(rectangles, selectedRectangles, 0);
    }

    private static boolean canGlueTogether(Rectangle[] rectangles, Rectangle[] selectedRectangles, int index) {
        if (index == rectangles.length) {
            return tryToGlue(selectedRectangles);
        }

        // Do not rotate
        selectedRectangles[index] = rectangles[index];
        if (canGlueTogether(rectangles, selectedRectangles, index + 1)) {
            return true;
        }

        // Rotate
        Rectangle rotatedRectangle = rectangles[index].rotate();
        selectedRectangles[index] = rotatedRectangle;
        return canGlueTogether(rectangles, selectedRectangles, index + 1);
    }

    private static boolean tryToGlue(Rectangle[] rectangles) {
        int heightSum = rectangles[0].height + rectangles[1].height + rectangles[2].height;

        if (rectangles[0].width == rectangles[1].width
                && rectangles[1].width == rectangles[2].width
                && rectangles[0].width == heightSum) {
            return true;
        }
        return canPlaceTwoHorizontalAndOneVertical(rectangles[0], rectangles[1], rectangles[2]) ||
                canPlaceTwoHorizontalAndOneVertical(rectangles[0], rectangles[2], rectangles[1]) ||
                canPlaceTwoHorizontalAndOneVertical(rectangles[1], rectangles[0], rectangles[2]) ||
                canPlaceTwoHorizontalAndOneVertical(rectangles[1], rectangles[2], rectangles[0]) ||
                canPlaceTwoHorizontalAndOneVertical(rectangles[2], rectangles[0], rectangles[1]) ||
                canPlaceTwoHorizontalAndOneVertical(rectangles[2], rectangles[1], rectangles[0]);
    }

    private static boolean canPlaceTwoHorizontalAndOneVertical(Rectangle rectangle1, Rectangle rectangle2,
                                                               Rectangle rectangle3) {
        return rectangle1.width == rectangle2.width
                && (rectangle2.width + rectangle3.width == rectangle3.height)
                && (rectangle1.height + rectangle2.height == rectangle3.height);
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
