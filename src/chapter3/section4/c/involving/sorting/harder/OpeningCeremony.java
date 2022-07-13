package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/07/22.
 */
public class OpeningCeremony {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] heights = new int[FastReader.nextInt()];
        for (int i = 0; i < heights.length; i++) {
            heights[i] = FastReader.nextInt();
        }
        int minimumNumberOfCharges = computeMinimumNumberOfCharges(heights);
        outputWriter.printLine(minimumNumberOfCharges);
        outputWriter.flush();
    }

    private static int computeMinimumNumberOfCharges(int[] heights) {
        int minimumNumberOfCharges = heights.length; // Destroy all blocks 1 by 1
        Arrays.sort(heights);

        for (int i = 0; i < heights.length; i++) {
            // Destroy all floors from current building and destroy all remaining blocks 1 by 1
            int chargesBasedOnFloorI = heights[i] + (heights.length - 1 - i);
            minimumNumberOfCharges = Math.min(minimumNumberOfCharges, chargesBasedOnFloorI);
        }
        return minimumNumberOfCharges;
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
