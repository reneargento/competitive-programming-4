package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/01/23.
 */
public class Patches {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int[] distances = new int[Integer.parseInt(data[0])];
            int[] patchLengths = new int[2];
            patchLengths[0] = Integer.parseInt(data[2]);
            patchLengths[1] = Integer.parseInt(data[3]);

            for (int i = 0; i < distances.length; i++) {
                distances[i] = FastReader.nextInt();
            }
            int smallestPatchLength = computeSmallestPatchLength(distances, patchLengths);
            outputWriter.printLine(smallestPatchLength);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeSmallestPatchLength(int[] distances, int[] patchLengths) {
        // dp[holeId] = minimumLengthUsed
        int[] dp = new int[distances.length];
        Arrays.fill(dp, -1);
        return computeSmallestPatchLength(distances, patchLengths, dp, 0);
    }

    private static int computeSmallestPatchLength(int[] distances, int[] patchLengths, int[] dp, int holeId) {
        if (holeId == dp.length) {
            return 0;
        }
        if (dp[holeId] != -1) {
            return dp[holeId];
        }

        int smallestPatchLength = Integer.MAX_VALUE;
        for (int patchLength : patchLengths) {
            int newDistanceWithPatch = distances[holeId] + patchLength;
            int nextHoleIdWithPatch = holeId + 1;
            while (nextHoleIdWithPatch < distances.length && newDistanceWithPatch >= distances[nextHoleIdWithPatch]) {
                nextHoleIdWithPatch++;
            }
            int lengthUsed = patchLength +
                    computeSmallestPatchLength(distances, patchLengths, dp, nextHoleIdWithPatch);
            smallestPatchLength = Math.min(smallestPatchLength, lengthUsed);
        }
        dp[holeId] = smallestPatchLength;
        return smallestPatchLength;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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

        public void flush() {
            writer.flush();
        }
    }
}
