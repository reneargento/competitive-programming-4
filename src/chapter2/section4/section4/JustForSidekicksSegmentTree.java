package chapter2.section4.section4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/07/21.
 */
public class JustForSidekicksSegmentTree {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int[] gems = new int[FastReader.nextInt() + 1];
        int queries = FastReader.nextInt();
        long[] gemValues = new long[7];

        for (int i = 1; i < gemValues.length; i++) {
            gemValues[i] = FastReader.nextInt();
        }

        String gemString = FastReader.next();
        for (int i = 1; i < gems.length; i++) {
            gems[i] = Character.getNumericValue(gemString.charAt(i - 1));
        }
        answerQueries(gems, gemValues, queries);
    }

    private static void answerQueries(int[] gems, long[] gemValues, int queries) throws IOException {
        OutputWriter outputWriter = new OutputWriter(System.out);
        SegmentTree segmentTree = new SegmentTree(gems);

        for (int q = 0; q < queries; q++) {
            int queryType = FastReader.nextInt();
            int parameter1 = FastReader.nextInt();
            int parameter2 = FastReader.nextInt();

            if (queryType == 1) {
                int oldGemType = (int) segmentTree.rsqGemType(parameter1, parameter1);
                segmentTree.update(parameter1, parameter1, oldGemType, parameter2);
            } else if (queryType == 2) {
                gemValues[parameter1] = parameter2;
            } else {
                long totalValue = segmentTree.rsq(parameter1, parameter2, gemValues);
                outputWriter.printLine(totalValue);
            }
        }
        outputWriter.flush();
    }

    private static class SegmentTree {
        private int size;
        int[] values;
        long[][] segmentTreeSum;

        private void initArrays(int size) {
            this.size = size;
            segmentTreeSum = new long[size * 4][7];
        }

        SegmentTree(int[] array) {
            initArrays(array.length);
            values = array;
            build(1, 0, size - 1);
        }

        void update(int startRange, int endRange, int oldGemType, int newGemType) {
            update(1, 0, size - 1, startRange, endRange, oldGemType, newGemType);
        }

        long rsq(int startRange, int endRange, long[] gemValues) {
            return rsq(1, 0, size - 1, startRange, endRange, gemValues);
        }

        long rsqGemType(int startRange, int endRange) {
            return rsqGemType(1, 0, size - 1, startRange, endRange);
        }

        int leftChild(int node) {
            return node << 1;
        }

        int rightChild(int node) {
            return (node << 1) + 1;
        }

        void build(int node, int left, int right) {
            if (left == right) {
                segmentTreeSum[node][values[left]]++;
            } else {
                int middle = (left + right) / 2;
                build(leftChild(node), left, middle);
                build(rightChild(node), middle + 1, right);
                updateNodeGems(node, leftChild(node), rightChild(node));
            }
        }

        private void updateNodeGems(int node, int leftChild, int rightChild) {
            for (int i = 1; i < segmentTreeSum[node].length; i++) {
                segmentTreeSum[node][i] = segmentTreeSum[leftChild][i] + segmentTreeSum[rightChild][i];
            }
        }

        long rsq(int node, int left, int right, int startRange, int endRange, long[] gemValues) {
            if (startRange > endRange) {
                return 0;
            }

            if (left >= startRange && right <= endRange) {
                long sum = 0;
                for (int i = 0; i < segmentTreeSum[node].length; i++) {
                    sum += segmentTreeSum[node][i] * gemValues[i];
                }
                return sum;
            }

            int middle = (left + right) / 2;
            long leftSubtreeSum = rsq(leftChild(node), left, middle, startRange, Math.min(middle, endRange), gemValues);
            long rightSubtreeSum = rsq(rightChild(node), middle + 1, right, Math.max(middle + 1, startRange), endRange, gemValues);
            return leftSubtreeSum + rightSubtreeSum;
        }

        long rsqGemType(int node, int left, int right, int startRange, int endRange) {
            if (startRange > endRange) {
                return 0;
            }

            if (left >= startRange && right <= endRange) {
                long sum = 0;
                for (int i = 1; i < segmentTreeSum[node].length; i++) {
                    sum += segmentTreeSum[node][i] * i;
                }
                return sum;
            }

            int middle = (left + right) / 2;
            long leftSubtreeSum = rsqGemType(leftChild(node), left, middle, startRange, Math.min(middle, endRange));
            long rightSubtreeSum = rsqGemType(rightChild(node), middle + 1, right, Math.max(middle + 1, startRange), endRange);
            return leftSubtreeSum + rightSubtreeSum;
        }

        void update(int node, int left, int right, int startRange, int endRange, int oldGemType, int newGemType) {
            if (startRange > endRange) {
                return;
            }

            if (left >= startRange && right <= endRange) {
                segmentTreeSum[node][oldGemType]--;
                segmentTreeSum[node][newGemType]++;
            } else {
                int leftChildNode = leftChild(node);
                int rightChildNode = rightChild(node);

                int middle = (left + right) / 2;
                update(leftChildNode, left, middle, startRange, Math.min(middle, endRange), oldGemType, newGemType);
                update(rightChildNode, middle + 1, right, Math.max(middle + 1, startRange), endRange, oldGemType, newGemType);
                updateNodeGems(node, leftChildNode, rightChildNode);
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
