package chapter2.section4.section3;

/**
 * Created by Rene Argento on 27/06/21.
 */
public class Exercise1 {

    private static class FenwickTree {
        private long[] fenwickTree;

        // Create empty fenwick tree
        FenwickTree(int size) {
            fenwickTree = new long[size + 1];
        }

        // Create fenwick tree with values
        FenwickTree(long[] values) {
            build(values);
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

        // O(log n)
        int select(long rank) {
            int p = 1;
            while (p * 2 < fenwickTree.length) {
                p *= 2;
            }

            int index = 0;
            while (p > 0) {
                if (rank > fenwickTree[index + p]) {
                    rank -= fenwickTree[index + p];
                    index += p;
                }
                p /= 2;
            }
            return index + 1;
        }

        private int lsOne(int value) {
            return value & (-value);
        }
    }

    public static void main(String[] args) {
        long[] values = {0, 1, 2, 3, 1, 10, 11, 100};
        FenwickTree fenwickTree = new FenwickTree(values);

        System.out.println("Select 1: " + fenwickTree.select(1) + " Expected: 1");
        System.out.println("Select 2: " + fenwickTree.select(2) + " Expected: 2");
        System.out.println("Select 7: " + fenwickTree.select(7) + " Expected: 4");
        System.out.println("Select 20: " + fenwickTree.select(20) + " Expected: 6");
    }
}
