package chapter2.section4.section3;

/**
 * Created by Rene Argento on 27/06/21.
 */
public class Exercise2_FenwickTree2DRUPQ {

    private static class FenwickTree2DRUPQ {
        private final Exercise2_FenwickTree2DPURQ.FenwickTree2DPURQ fenwickTree;

        public FenwickTree2DRUPQ(int rows, int columns) {
            fenwickTree = new Exercise2_FenwickTree2DPURQ.FenwickTree2DPURQ(rows, columns);
        }

        public void rangeUpdate(int row1, int column1, int row2, int column2, int value) {
            int minRow = Math.min(row1, row2);
            int maxRow = Math.max(row1, row2);
            int minColumn = Math.min(column1, column2);
            int maxColumn = Math.max(column1, column2);

            // Rectangular region between lower row and left column x upper row and right column
            fenwickTree.pointUpdate(maxRow + 1, maxColumn + 1, value);
            fenwickTree.pointUpdate(minRow, minColumn, value);
            fenwickTree.pointUpdate(minRow, maxColumn + 1, -value);
            fenwickTree.pointUpdate(maxRow + 1, minColumn, -value);
        }

        public int pointQuery(int row, int column) {
            return fenwickTree.pointQuery(row, column);
        }
    }

    public static void main(String[] args) {
        FenwickTree2DRUPQ fenwickTree2DRUPQ = new FenwickTree2DRUPQ(3, 4);
        fenwickTree2DRUPQ.rangeUpdate(1, 2, 1, 3, 1);
        fenwickTree2DRUPQ.rangeUpdate(2, 2, 2, 3, 2);
        fenwickTree2DRUPQ.rangeUpdate(3, 2, 3, 3, 3);

        System.out.println("Fenwick Tree");
        printFenwickTree(fenwickTree2DRUPQ);

        fenwickTree2DRUPQ.rangeUpdate(1, 1, 1, 4, 5);
        fenwickTree2DRUPQ.rangeUpdate(2, 2, 3, 4, 10);

        System.out.println("\nAfter updates");
        printFenwickTree(fenwickTree2DRUPQ);
    }

    private static void printFenwickTree(FenwickTree2DRUPQ fenwickTree2DRUPQ) {
        for (int row = 1; row <= fenwickTree2DRUPQ.fenwickTree.rows; row++) {
            for (int column = 1; column <= fenwickTree2DRUPQ.fenwickTree.columns; column++) {
                 System.out.print(fenwickTree2DRUPQ.pointQuery(row, column));

                if (column != fenwickTree2DRUPQ.fenwickTree.columns) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
