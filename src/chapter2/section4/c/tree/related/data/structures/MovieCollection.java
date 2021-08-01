package chapter2.section4.c.tree.related.data.structures;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/07/21.
 */
public class MovieCollection {

    private static class InitialStack {
        int[] moviesStack;
        int[] movieLocations;

        public InitialStack(int[] moviesStack, int[] movieLocations) {
            this.moviesStack = moviesStack;
            this.movieLocations = movieLocations;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int moviesInStack = FastReader.nextInt();
            int locateRequests = FastReader.nextInt();

            InitialStack initialStack = createInitialStack(moviesInStack);
            int[] movieLocations = initialStack.movieLocations;
            int lastIndex = moviesInStack;

            FenwickTreeRangeSum fenwickTree = new FenwickTreeRangeSum(initialStack.moviesStack);

            for (int i = 0; i < locateRequests; i++) {
                int movieSearched = FastReader.nextInt();
                int movieBoxesAbove = processQuery(fenwickTree, movieLocations, movieSearched, lastIndex);
                outputWriter.print(movieBoxesAbove);

                if (i != locateRequests - 1) {
                    outputWriter.print(" ");
                }
                if (movieBoxesAbove != 0) {
                    lastIndex++;
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static int processQuery(FenwickTreeRangeSum fenwickTree, int[] movieLocations, int movieSearched,
                                    int lastIndex) {
        int movieLocation = movieLocations[movieSearched];
        int movieBoxesAbove = fenwickTree.rangeSumQuery(movieLocation + 1, lastIndex);

        if (movieBoxesAbove != 0) {
            int newLastIndex = lastIndex + 1;
            movieLocations[movieSearched] = newLastIndex;
            fenwickTree.update(movieLocation, -1);
            fenwickTree.update(newLastIndex, 1);
        }
        return movieBoxesAbove;
    }

    private static InitialStack createInitialStack(int moviesInStack) {
        int[] moviesStack = new int[200001];
        int[] movieLocations = new int[moviesInStack + 1];

        for (int i = 1; i <= moviesInStack; i++) {
            moviesStack[i] = 1;
            movieLocations[i] = moviesInStack - i + 1;
        }
        return new InitialStack(moviesStack, movieLocations);
    }

    private static class FenwickTreeRangeSum {
        private int[] fenwickTree;

        // Create fenwick tree with values
        FenwickTreeRangeSum(int[] values) {
            build(values);
        }

        private void build(int[] values) {
            int size = values.length - 1; // values[0] should always be 0
            fenwickTree = new int[size + 1];
            for (int i = 1; i <= size; i++) {
                fenwickTree[i] += values[i];

                if (i + lsOne(i) <= size) { // i has parent
                    fenwickTree[i + lsOne(i)] += fenwickTree[i]; // Add to that parent
                }
            }
        }

        // Returns sum from 1 to index
        public int rangeSumQuery(int index) {
            int sum = 0;
            while (index > 0) {
                sum += fenwickTree[index];
                index -= lsOne(index);
            }
            return sum;
        }

        public int rangeSumQuery(int startIndex, int endIndex) {
            return rangeSumQuery(endIndex) - rangeSumQuery(startIndex - 1);
        }

        // Updates the value of element on index by value (can be positive/increment or negative/decrement)
        public void update(int index, int value) {
            while (index < fenwickTree.length) {
                fenwickTree[index] += value;
                index += lsOne(index);
            }
        }

        private int lsOne(int value) {
            return value & (-value);
        }
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
