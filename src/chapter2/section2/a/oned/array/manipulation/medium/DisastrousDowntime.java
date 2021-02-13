package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/01/21.
 */
public class DisastrousDowntime {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int requests = scanner.nextInt();
        double simultaneousRequests = scanner.nextInt();
        int lastTime = 100002;
        FenwickTreeRangeUpdate fenwickTree = new FenwickTreeRangeUpdate(lastTime);

        for (int i = 0; i < requests; i++) {
            int timestamp = scanner.nextInt() + 1; // Add 1 to avoid value 0 in fenwick tree
            fenwickTree.rangeUpdate(timestamp, timestamp + 999, 1);
        }

        double serversNeeded = 0;
        for (int i = 0; i < lastTime; i++) {
            double servers = Math.ceil(fenwickTree.pointQuery(i) / simultaneousRequests);
            serversNeeded = Math.max(serversNeeded, servers);
        }
        System.out.println((int) serversNeeded);
    }

    private static class FenwickTreeRangeSum {
        private long[] fenwickTree;

        // Create empty fenwick tree
        FenwickTreeRangeSum(int size) {
            fenwickTree = new long[size + 1];
        }

        private void build(long[] values) {
            int size = values.length - 1; // values[0] should always be 0
            fenwickTree = new long[size + 1];
            for (int i = 1; i <= size; i++) {
                fenwickTree[i] += values[i];

                if (i + lsOne(i) <= size) { // i has parent
                    fenwickTree[i + lsOne(i)] += fenwickTree[i]; // Add to that parent
                }
            }
        }

        // Returns sum from 1 to index
        public long rangeSumQuery(int index) {
            long sum = 0;
            while (index > 0) {
                sum += fenwickTree[index];
                index -= lsOne(index);
            }
            return sum;
        }

        public long rangeSumQuery(int startIndex, int endIndex) {
            return rangeSumQuery(endIndex) - rangeSumQuery(startIndex - 1);
        }

        // Updates the value of element on index by value (can be positive/increment or negative/decrement)
        public void update(int index, long value) {
            while (index < fenwickTree.length) {
                fenwickTree[index] += value;
                index += lsOne(index);
            }
        }

        int lsOne(int value) {
            return value & (-value);
        }
    }

    private static class FenwickTreeRangeUpdate {
        private FenwickTreeRangeSum fenwickTree;

        FenwickTreeRangeUpdate(int size) {
            fenwickTree = new FenwickTreeRangeSum(size);
        }

        public void rangeUpdate(int startIndex, int endIndex, int value) {
            fenwickTree.update(startIndex, value); // [startIndex, startIndex + 1, ..., size] + value
            fenwickTree.update(endIndex + 1, -value); // [endIndex + 1, endIndex + 2, ..., size] - value
        }

        public long pointQuery(int index) {
            return fenwickTree.rangeSumQuery(index);
        }
    }
}
