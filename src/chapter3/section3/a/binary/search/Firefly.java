package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/03/22.
 */
public class Firefly {

    private static class Solution {
        int obstacles;
        int distinctLevels;

        public Solution(int obstacles, int distinctLevels) {
            this.obstacles = obstacles;
            this.distinctLevels = distinctLevels;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int length = FastReader.nextInt();
        int height = FastReader.nextInt();
        int[] stalagmites = new int[length / 2];
        int[] stalactites = new int[length / 2];
        int index = 0;

        for (int i = 0; i < length; i++) {
            if (i % 2 == 0) {
                stalagmites[index] = FastReader.nextInt();
            } else {
                stalactites[index] = height - FastReader.nextInt() + 1;
                index++;
            }
        }
        Solution solution = planFlight(height, stalagmites, stalactites);
        outputWriter.printLine(solution.obstacles + " " + solution.distinctLevels);
        outputWriter.flush();
    }

    private static Solution planFlight(int height, int[] stalagmites, int[] stalactites) {
        Arrays.sort(stalagmites);
        Arrays.sort(stalactites);

        int minimumObstacles = Integer.MAX_VALUE;
        int distinctLevels = 0;

        for (int h = 1; h <= height; h++) {
            int lowerBound = binarySearch(stalagmites, h, true) + 1;
            int stalagmitesBroken = 0;
            if (lowerBound != 0) {
                stalagmitesBroken = stalagmites.length - lowerBound + 1;
            }

            int stalactitesBroken = binarySearch(stalactites, h, false) + 1;

            int obstacles = stalagmitesBroken + stalactitesBroken;
            if (obstacles < minimumObstacles) {
                minimumObstacles = obstacles;
                distinctLevels = 1;
            } else if (obstacles == minimumObstacles) {
                distinctLevels++;
            }
        }
        return new Solution(minimumObstacles, distinctLevels);
    }

    private static int binarySearch(int[] values, int target, boolean isLowerBound) {
        int low = 0;
        int high = values.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (isLowerBound) {
                if (values[middle] < target) {
                    low = middle + 1;
                } else {
                    result = middle;
                    high = middle - 1;
                }
            } else {
                if (values[middle] > target) {
                    high = middle - 1;
                } else {
                    result = middle;
                    low = middle + 1;
                }
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
