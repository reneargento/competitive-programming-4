package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/03/22.
 */
public class RoomPainting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] canSizes = new int[FastReader.nextInt()];
        int[] colorMicrolitres = new int[FastReader.nextInt()];

        for (int i = 0; i < canSizes.length; i++) {
            canSizes[i] = FastReader.nextInt();
        }
        for (int i = 0; i < colorMicrolitres.length; i++) {
            colorMicrolitres[i] = FastReader.nextInt();
        }
        long minimumWaste = computeMinimumWaste(canSizes, colorMicrolitres);
        outputWriter.printLine(minimumWaste);

        outputWriter.flush();
    }

    private static long computeMinimumWaste(int[] canSizes, int[] colorMicrolitres) {
        long waste = 0;
        Arrays.sort(canSizes);

        for (int colorMicrolitre : colorMicrolitres) {
            int canIndex = binarySearchUpperBound(canSizes, colorMicrolitre);
            waste += canSizes[canIndex] - colorMicrolitre;
        }
        return waste;
    }

    private static int binarySearchUpperBound(int[] values, int target) {
        int low = 0;
        int high = values.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (values[middle] == target) {
                return middle;
            } else if (values[middle] < target) {
                low = middle + 1;
            } else {
                result = middle;
                high = middle - 1;
            }
        }
        return result;
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
