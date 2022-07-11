package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/07/22.
 */
public class HippoCircus {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] heights = new int[FastReader.nextInt()];
            int doorHeight = FastReader.nextInt();
            int aloneBowTime = FastReader.nextInt();
            int carryingBowTime = FastReader.nextInt();

            for (int i = 0; i < heights.length; i++) {
                heights[i] = FastReader.nextInt();
            }
            long minimumTime = computeMinimumTime(heights, doorHeight, aloneBowTime, carryingBowTime);
            outputWriter.printLine(String.format("Case %d: %d", t, minimumTime));
        }
        outputWriter.flush();
    }

    private static long computeMinimumTime(int[] heights, int doorHeight, int aloneBowTime, int carryingBowTime) {
        if (carryingBowTime >= aloneBowTime * 2) {
            return heights.length * aloneBowTime;
        }

        Arrays.sort(heights);
        long minimumTime = 0;

        int startIndex = 0;
        int endIndex = heights.length - 1;
        while (startIndex <= endIndex) {
            if (startIndex == endIndex) {
                minimumTime += aloneBowTime;
                break;
            } else {
                if (heights[startIndex] + heights[endIndex] < doorHeight) {
                    minimumTime += carryingBowTime;
                    startIndex++;
                } else {
                    minimumTime += aloneBowTime;
                }
                endIndex--;
            }
        }
        return minimumTime;
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
