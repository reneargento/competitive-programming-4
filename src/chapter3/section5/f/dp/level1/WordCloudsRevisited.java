package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/01/23.
 */
public class WordCloudsRevisited {

    private static class Entry {
        int width;
        int height;

        public Entry(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Entry[] entries = new Entry[FastReader.nextInt()];
        int maxWidth = FastReader.nextInt();

        for (int i = 0; i < entries.length; i++) {
            entries[i] = new Entry(FastReader.nextInt(), FastReader.nextInt());
        }
        int minimumHeight = computeMinimumHeight(entries, maxWidth);
        outputWriter.printLine(minimumHeight);
        outputWriter.flush();
    }

    private static int computeMinimumHeight(Entry[] entries, int maxWidth) {
        // dp[entryId][currentWidth] = minimumHeight
        int[][] dp = new int[entries.length][maxWidth + 1];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeMinimumHeight(entries, dp, 0, 0, 0);
    }

    private static int computeMinimumHeight(Entry[] entries, int[][] dp, int entryId, int currentWidth, int currentRowHeight) {
        if (entryId == dp.length) {
            return currentRowHeight;
        }
        if (dp[entryId][currentWidth] != -1) {
            return dp[entryId][currentWidth];
        }

        Entry entry = entries[entryId];
        int heightWithNewRow = currentRowHeight +
                computeMinimumHeight(entries, dp, entryId + 1, entry.width, entry.height);
        int heightWithSameRow = INFINITE;
        if (entry.width + currentWidth < dp[0].length) {
            currentRowHeight = Math.max(currentRowHeight, entry.height);
            heightWithSameRow = computeMinimumHeight(entries, dp, entryId + 1,
                    currentWidth + entry.width, currentRowHeight);
        }
        dp[entryId][currentWidth] = Math.min(heightWithNewRow, heightWithSameRow);
        return dp[entryId][currentWidth];
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
