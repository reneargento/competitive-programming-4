package chapter2.section4.section4;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/07/21.
 */
public class MovieCollectionSegmentTree {

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

            SegmentTree segmentTree = new SegmentTree(initialStack.moviesStack);

            for (int i = 0; i < locateRequests; i++) {
                int movieSearched = FastReader.nextInt();
                int movieBoxesAbove = processQuery(segmentTree, movieLocations, movieSearched, lastIndex);
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

    private static int processQuery(SegmentTree segmentTree, int[] movieLocations, int movieSearched,
                                    int lastIndex) {
        int movieLocation = movieLocations[movieSearched];
        int movieBoxesAbove = segmentTree.rsq(movieLocation + 1, lastIndex);

        if (movieBoxesAbove != 0) {
            int newLastIndex = lastIndex + 1;
            movieLocations[movieSearched] = newLastIndex;
            segmentTree.update(movieLocation, movieLocation,0);
            segmentTree.update(newLastIndex, newLastIndex,1);
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

    private static class SegmentTree {
        private int size;
        int[] values;
        int[] segmentTreeSum;
        int[] lazyValuesSum;

        private void initArrays(int size) {
            this.size = size;
            segmentTreeSum = new int[size * 4];
            lazyValuesSum = new int[size * 4];
            Arrays.fill(lazyValuesSum, -1);
        }

        SegmentTree(int[] array) {
            initArrays(array.length);
            values = array;
            build(1, 0, size - 1);
        }

        void update(int startRange, int endRange, int value) {
            update(1, 0, size - 1, startRange, endRange, value);
        }

        int rsq(int startRange, int endRange) {
            return rsq(1, 0, size - 1, startRange, endRange);
        }

        int leftChild(int node) {
            return node << 1;
        }

        int rightChild(int node) {
            return (node << 1) + 1;
        }

        void build(int node, int left, int right) {
            if (left == right) {
                segmentTreeSum[node] = values[left];
            } else {
                int middle = (left + right) / 2;
                build(leftChild(node), left, middle);
                build(rightChild(node), middle + 1, right);
                segmentTreeSum[node] = segmentTreeSum[leftChild(node)] + segmentTreeSum[rightChild(node)];
            }
        }

        void propagate(int node, int left, int right) {
            if (lazyValuesSum[node] != -1) {
                segmentTreeSum[node] = lazyValuesSum[node];
                if (left != right) {
                    int rangeSize = right - left + 1;
                    int valuePerNode = lazyValuesSum[node] / rangeSize;
                    int middle = (left + right) / 2;
                    int leftSize = middle - left + 1;
                    int rightSize = rangeSize - leftSize;
                    lazyValuesSum[leftChild(node)] = valuePerNode * leftSize;
                    lazyValuesSum[rightChild(node)] = valuePerNode * rightSize;
                } else {
                    values[left] = lazyValuesSum[node];
                }
                lazyValuesSum[node] = -1;
            }
        }

        int rsq(int node, int left, int right, int startRange, int endRange) {
            propagate(node, left, right);
            if (startRange > endRange) {
                return 0;
            }

            if (left >= startRange && right <= endRange) {
                return segmentTreeSum[node];
            }

            int middle = (left + right) / 2;
            int leftSubtreeSum = rsq(leftChild(node), left, middle, startRange, Math.min(middle, endRange));
            int rightSubtreeSum = rsq(rightChild(node), middle + 1, right, Math.max(middle + 1, startRange), endRange);
            return leftSubtreeSum + rightSubtreeSum;
        }

        void update(int node, int left, int right, int startRange, int endRange, int value) {
            propagate(node, left, right);
            if (startRange > endRange) {
                return;
            }

            if (left >= startRange && right <= endRange) {
                lazyValuesSum[node] = value * (right - left + 1);
                propagate(node, left, right);
            } else {
                int leftChildNode = leftChild(node);
                int rightChildNode = rightChild(node);

                int middle = (left + right) / 2;
                update(leftChildNode, left, middle, startRange, Math.min(middle, endRange), value);
                update(rightChildNode, middle + 1, right, Math.max(middle + 1, startRange), endRange, value);

                int leftSubtreeSumValue = (lazyValuesSum[leftChildNode] != -1) ? lazyValuesSum[leftChildNode] : segmentTreeSum[leftChildNode];
                int rightSubtreeSumValue = (lazyValuesSum[rightChildNode] != -1) ? lazyValuesSum[rightChildNode] : segmentTreeSum[rightChildNode];
                segmentTreeSum[node] = leftSubtreeSumValue + rightSubtreeSumValue;
            }
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
