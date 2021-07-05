package chapter2.section4.section4;

import java.util.Arrays;

/**
 * Created by Rene Argento on 27/06/21.
 */
public class Exercise3 {

    private static class SegmentTree {
        private int size;
        long[] values;
        long[] segmentTreeMin;
        long[] segmentTreeSum;
        long[] lazyValuesMin;
        long[] lazyValuesSum;

        SegmentTree(int size) {
            values = new long[size];
            initArrays(size);
        }

        private void initArrays(int size) {
            this.size = size;
            segmentTreeMin = new long[size * 4];
            segmentTreeSum = new long[size * 4];
            lazyValuesMin = new long[size * 4];
            Arrays.fill(lazyValuesMin, -1);
            lazyValuesSum = new long[size * 4];
            Arrays.fill(lazyValuesSum, -1);
        }

        SegmentTree(long[] array) {
            initArrays(array.length);
            values = array;
            build(1, 0, size - 1);
        }

        void update(int startRange, int endRange, long value) {
            update(1, 0, size - 1, startRange, endRange, value);
        }

        long rmq(int startRange, int endRange) {
            return rmq(1, 0, size - 1, startRange, endRange);
        }

        long rsq(int startRange, int endRange) {
            return rsq(1, 0, size - 1, startRange, endRange);
        }

        int leftChild(int node) {
            return node << 1;
        }

        int rightChild(int node) {
            return (node << 1) + 1;
        }

        private long conquer(long value1, long value2) {
            if (value1 == -1) {
                return value2;
            }
            if (value2 == -1) {
                return value1;
            }
            return Math.min(value1, value2);
        }

        void build(int node, int left, int right) {
            if (left == right) {
                segmentTreeMin[node] = values[left];
                segmentTreeSum[node] = values[left];
            } else {
                int middle = (left + right) / 2;
                build(leftChild(node), left, middle);
                build(rightChild(node), middle + 1, right);
                segmentTreeMin[node] = conquer(segmentTreeMin[leftChild(node)], segmentTreeMin[rightChild(node)]);
                segmentTreeSum[node] = segmentTreeSum[leftChild(node)] + segmentTreeSum[rightChild(node)];
            }
        }

        void propagate(int node, int left, int right) {
            if (lazyValuesMin[node] != -1) {
                segmentTreeMin[node] = lazyValuesMin[node];
                segmentTreeSum[node] = lazyValuesSum[node];
                if (left != right) {
                    lazyValuesMin[leftChild(node)] = lazyValuesMin[node];
                    lazyValuesMin[rightChild(node)] = lazyValuesMin[node];

                    int rangeSize = right - left + 1;
                    long valuePerNode = lazyValuesSum[node] / rangeSize;
                    int middle = (left + right) / 2;
                    int leftSize = middle - left + 1;
                    int rightSize = rangeSize - leftSize;
                    lazyValuesSum[leftChild(node)] = valuePerNode * leftSize;
                    lazyValuesSum[rightChild(node)] = valuePerNode * rightSize;
                } else {
                    values[left] = lazyValuesMin[node];
                }
                lazyValuesMin[node] = -1;
                lazyValuesSum[node] = -1;
            }
        }

        long rmq(int node, int left, int right, int startRange, int endRange) {
            propagate(node, left, right);
            if (startRange > endRange) {
                return -1;
            }

            if (left >= startRange && right <= endRange) {
                return segmentTreeMin[node];
            }
            int middle = (left + right) / 2;
            long leftSubtreeValue = rmq(leftChild(node), left, middle, startRange, Math.min(middle, endRange));
            long rightSubtreeValue = rmq(rightChild(node), middle + 1, right, Math.max(middle + 1, startRange), endRange);
            return conquer(leftSubtreeValue, rightSubtreeValue);
        }

        long rsq(int node, int left, int right, int startRange, int endRange) {
            propagate(node, left, right);
            if (startRange > endRange) {
                return 0;
            }

            if (left >= startRange && right <= endRange) {
                return segmentTreeSum[node];
            }

            int middle = (left + right) / 2;
            long leftSubtreeSum = rsq(leftChild(node), left, middle, startRange, Math.min(middle, endRange));
            long rightSubtreeSum = rsq(rightChild(node), middle + 1, right, Math.max(middle + 1, startRange), endRange);
            return leftSubtreeSum + rightSubtreeSum;
        }

        void update(int node, int left, int right, int startRange, int endRange, long value) {
            propagate(node, left, right);
            if (startRange > endRange) {
                return;
            }

            if (left >= startRange && right <= endRange) {
                lazyValuesMin[node] = value;
                lazyValuesSum[node] = value * (right - left + 1);
                propagate(node, left, right);
            } else {
                int leftChildNode = leftChild(node);
                int rightChildNode = rightChild(node);

                int middle = (left + right) / 2;
                update(leftChildNode, left, middle, startRange, Math.min(middle, endRange), value);
                update(rightChildNode, middle + 1, right, Math.max(middle + 1, startRange), endRange, value);

                long leftSubtreeMinValue = (lazyValuesMin[leftChildNode] != -1) ? lazyValuesMin[leftChildNode] : segmentTreeMin[leftChildNode];
                long rightSubtreeMinValue = (lazyValuesMin[rightChildNode] != -1) ? lazyValuesMin[rightChildNode] : segmentTreeMin[rightChildNode];
                segmentTreeMin[node] = (leftSubtreeMinValue <= rightSubtreeMinValue) ? segmentTreeMin[leftChildNode] : segmentTreeMin[rightChildNode];

                long leftSubtreeSumValue = (lazyValuesSum[leftChildNode] != -1) ? lazyValuesSum[leftChildNode] : segmentTreeSum[leftChildNode];
                long rightSubtreeSumValue = (lazyValuesSum[rightChildNode] != -1) ? lazyValuesSum[rightChildNode] : segmentTreeSum[rightChildNode];
                segmentTreeSum[node] = leftSubtreeSumValue + rightSubtreeSumValue;
            }
        }
    }

    public static void main(String[] args) {
        long[] values = {1, 2, 3, 1, 10, 11, 100};
        SegmentTree segmentTree = new SegmentTree(values);

        System.out.println("RSQ (4, 4): " + segmentTree.rsq(4, 4) + " Expected: 10");
        System.out.println("RSQ (0, 6): " + segmentTree.rsq(0, 6) + " Expected: 128");
        segmentTree.update(3, 5, 20);
        System.out.println("RSQ (0, 6): " + segmentTree.rsq(0, 6) + " Expected: 166");
    }
}
