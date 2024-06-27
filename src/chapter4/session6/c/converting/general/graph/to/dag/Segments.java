package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/06/24.
 */
public class Segments {

    private static class Segment {
        int left;
        int right;

        public Segment(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    private static class DpKey {
        int row;
        int column;

        public DpKey(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DpKey dpKey = (DpKey) o;
            return row == dpKey.row && column == dpKey.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dimension = FastReader.nextInt();

        while (dimension != 0) {
            Segment[] segments = new Segment[dimension];
            for (int i = 0; i < segments.length; i++) {
                segments[i] = new Segment(FastReader.nextInt() - 1, FastReader.nextInt() - 1);
            }

            int shortestPath = computeShortestPath(segments);
            outputWriter.printLine(shortestPath);
            dimension = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeShortestPath(Segment[] segments) {
        // dp[current row][current column]
        Map<DpKey, Integer> dp = new HashMap<>();
        return computeShortestPath(segments, dp, 0, 0);
    }

    private static int computeShortestPath(Segment[] segments, Map<DpKey, Integer> dp, int row, int column) {
        if (row == segments.length) {
            return (segments.length - 1) - column - 1;
        }
        DpKey dpKey = new DpKey(row, column);
        if (dp.containsKey(dpKey)) {
            return dp.get(dpKey);
        }

        Segment segment = segments[row];

        // Go left
        int pathLengthLeft = INFINITE;
        int nextColumnLeft = column;
        if (segment.left <= column) {
            pathLengthLeft = column - segments[row].left;
            nextColumnLeft = segment.left;

            if (segment.right > column) {
                pathLengthLeft += (segment.right - segment.left);
                nextColumnLeft = segment.right;
            }
        }

        // Go right
        int pathLengthRight = INFINITE;
        int nextColumnRight = column;
        if (segment.right >= column) {
            pathLengthRight = segments[row].right - column;
            nextColumnRight = segment.right;

            if (segment.left < column) {
                pathLengthRight += (segment.right - segment.left);
                nextColumnRight = segment.left;
            }
        }

        int pathGoingDownAndLeft = 1 + pathLengthLeft + computeShortestPath(segments, dp, row + 1, nextColumnLeft);
        int pathGoingDownAndRight = 1 + pathLengthRight + computeShortestPath(segments, dp, row + 1, nextColumnRight);

        int shortestPath = Math.min(pathGoingDownAndLeft, pathGoingDownAndRight);
        dp.put(dpKey, shortestPath);
        return shortestPath;
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
