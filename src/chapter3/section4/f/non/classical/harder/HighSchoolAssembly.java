package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/08/22.
 */
public class HighSchoolAssembly {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] heights = new int[FastReader.nextInt()];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = FastReader.nextInt();
            }
            int minimumMovesToSort = computeMinimumMovesToSort(heights);
            outputWriter.printLine(String.format("Case %d: %d", t, minimumMovesToSort));
        }
        outputWriter.flush();
    }

    private static int computeMinimumMovesToSort(int[] heights) {
        int minimumMovesToSort = 0;
        int[] smallestHeightToTheRight = getSmallestHeightToTheRight(heights);

        int smallestHeightMoved = Integer.MAX_VALUE;
        for (int i = 0; i < heights.length; i++) {
            if (heights[i] > smallestHeightToTheRight[i] || heights[i] > smallestHeightMoved) {
                minimumMovesToSort++;
                smallestHeightMoved = Math.min(smallestHeightMoved, heights[i]);
            }
        }
        return minimumMovesToSort;
    }

    private static int[] getSmallestHeightToTheRight(int[] heights) {
        int[] smallestHeightToTheRight = new int[heights.length];
        int smallestHeight = Integer.MAX_VALUE;

        for (int i = heights.length - 1; i >= 0; i--) {
            smallestHeight = Math.min(smallestHeight, heights[i]);
            smallestHeightToTheRight[i] = smallestHeight;
        }
        return smallestHeightToTheRight;
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