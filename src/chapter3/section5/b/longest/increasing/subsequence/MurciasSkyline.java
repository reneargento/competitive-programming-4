package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/11/22.
 */
public class MurciasSkyline {

    private static class Building {
        int height;
        int width;

        public Building(int height, int width) {
            this.height = height;
            this.width = width;
        }

        public int compareTo(Building other) {
            return Integer.compare(height, other.height);
        }
    }

    private static class Result {
        long longestIncreasingLength;
        long longestDecreasingLength;

        public Result(long longestIncreasingLength, long longestDecreasingLength) {
            this.longestIncreasingLength = longestIncreasingLength;
            this.longestDecreasingLength = longestDecreasingLength;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int buildingsNumber = FastReader.nextInt();
            int[] heights = new int[buildingsNumber];
            int[] widths = new int[buildingsNumber];

            for (int i = 0; i < heights.length; i++) {
                heights[i] = FastReader.nextInt();
            }
            for (int i = 0; i < widths.length; i++) {
                widths[i] = FastReader.nextInt();
            }

            List<Building> buildings = new ArrayList<>();
            for (int i = 0; i < buildingsNumber; i++) {
                buildings.add(new Building(heights[i], widths[i]));
            }
            Result lengths = computeLengths(buildings);

            outputWriter.print(String.format("Case %d. ", t));
            if (lengths.longestIncreasingLength >= lengths.longestDecreasingLength) {
                outputWriter.printLine(String.format("Increasing (%d). Decreasing (%d).",
                        lengths.longestIncreasingLength, lengths.longestDecreasingLength));
            } else {
                outputWriter.printLine(String.format("Decreasing (%d). Increasing (%d).",
                        lengths.longestDecreasingLength, lengths.longestIncreasingLength));
            }
        }
        outputWriter.flush();
    }

    private static Result computeLengths(List<Building> buildings) {
        long longestIncreasingLength = longestIncreasingSubsequence(buildings);
        Collections.reverse(buildings);
        long longestDecreasingLength = longestIncreasingSubsequence(buildings);
        return new Result(longestIncreasingLength, longestDecreasingLength);
    }

    private static long longestIncreasingSubsequence(List<Building> buildings) {
        long lisLength = 0;

        long[] totalWeight = new long[buildings.size()];
        for (int i = 0; i < totalWeight.length; i++) {
            totalWeight[i] = buildings.get(i).width;
            lisLength = Math.max(lisLength, totalWeight[i]);
        }

        for (int i = 0; i < buildings.size(); i++) {
            for (int j = i + 1; j < buildings.size(); j++) {
                int currentWidth = buildings.get(j).width;

                if (buildings.get(j).compareTo(buildings.get(i)) > 0
                        && totalWeight[i] + currentWidth > totalWeight[j]) {
                    totalWeight[j] = totalWeight[i] + currentWidth;
                    lisLength = Math.max(lisLength, totalWeight[j]);
                }
            }
        }
        return lisLength;
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
