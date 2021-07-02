package chapter2.section4.section3;

/**
 * Created by Rene Argento on 27/06/21.
 */
public class Exercise4 {

    private static class FenwickTree {
        private long[] fenwickTree;

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

        public void delete(int index) {
            long value = rangeSumQuery(index, index);
            update(index, -value);
        }

        // Assumption: 1 <= index <= size
        public void insert(int index, int value) {
            long currentValue = rangeSumQuery(index, index);
            if (currentValue != 0) {
                // Value already exists
                return;
            }
            update(index, value);
        }

        private int lsOne(int value) {
            return value & (-value);
        }
    }

    public static void main(String[] args) {
        long[] values = {0, 1, 2, 3, 1, 10, 11, 100};
        FenwickTree fenwickTree = new FenwickTree(values);

        System.out.println("RSQ (5, 5): " + fenwickTree.rangeSumQuery(5, 5) + " Expected: 10");
        System.out.println("RSQ (1, 7): " + fenwickTree.rangeSumQuery(1, 7) + " Expected: 128");

        fenwickTree.delete(5);
        System.out.println("\nRSQ (5, 5) (after delete): " + fenwickTree.rangeSumQuery(5, 5) + " Expected: 0");
        System.out.println("RSQ (1, 7) (after delete): " + fenwickTree.rangeSumQuery(1, 7) + " Expected: 118");

        fenwickTree.insert(5, 999);
        System.out.println("\nRSQ (5, 5) (after insert): " + fenwickTree.rangeSumQuery(5, 5) + " Expected: 999");
        System.out.println("RSQ (1, 7) (after insert): " + fenwickTree.rangeSumQuery(1, 7) + " Expected: 1117");
    }
}
