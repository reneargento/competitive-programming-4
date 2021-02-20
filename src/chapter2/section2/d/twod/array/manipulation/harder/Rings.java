package chapter2.section2.d.twod.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 08/02/21.
 */
public class Rings {

    private static class RingNumbersData {
        int[][] ringNumbers;
        int maxRingNumber;

        public RingNumbersData(int[][] ringNumbers, int maxRingNumber) {
            this.ringNumbers = ringNumbers;
            this.maxRingNumber = maxRingNumber;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        char[][] tree = new char[rows][columns];

        for (int row = 0; row < tree.length; row++) {
            tree[row] = scanner.next().toCharArray();
        }

        RingNumbersData ringNumbersData = getRingNumbers(tree);
        printRingNumbers(ringNumbersData);
    }

    private static RingNumbersData getRingNumbers(char[][] tree) {
        int[][] ringNumbers = new int[tree.length][tree[0].length];
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };
        int ringNumber = 0;

        while (computeRingNumber(tree, ringNumbers, ringNumber, neighborRows, neighborColumns)) {
            ringNumber++;
        }
        return new RingNumbersData(ringNumbers, ringNumber);
    }

    private static boolean computeRingNumber(char[][] tree, int[][] ringNumbers, int ringNumber, int[] neighborRows,
                                             int[] neighborColumns) {
        boolean updated = false;

        for (int row = 0; row < tree.length; row++) {
            for (int column = 0; column < tree[0].length; column++) {
                if (tree[row][column] != 'T' || ringNumbers[row][column] != 0) {
                    continue;
                }

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row + neighborRows[i];
                    int neighborColumn = column + neighborColumns[i];

                    if (!isValid(tree, neighborRow, neighborColumn)
                            || (ringNumbers[neighborRow][neighborColumn] == ringNumber
                            && !(tree[neighborRow][neighborColumn] == 'T' && ringNumber == 0))) {
                        ringNumbers[row][column] = ringNumber + 1;
                        updated = true;
                        break;
                    }
                }
            }
        }
        return updated;
    }

    private static void printRingNumbers(RingNumbersData ringNumbersData) {
        int[][] ringNumbers = ringNumbersData.ringNumbers;
        boolean useThreeCells = ringNumbersData.maxRingNumber >= 10;

        for (int row = 0; row < ringNumbers.length; row++) {
            for (int column = 0; column < ringNumbers[0].length; column++) {
                String ringNumberDescription = String.valueOf(ringNumbers[row][column]);
                boolean appendSpace = (useThreeCells && ringNumberDescription.length() == 1);
                String gridSquare = appendSpace ? ".." : ".";
                String ringNumber = ringNumbers[row][column] == 0 ? "." : ringNumberDescription;

                System.out.print(gridSquare + ringNumber);
            }
            System.out.println();
        }
    }

    private static boolean isValid(char[][] tree, int row, int column) {
        return row >= 0 && row < tree.length && column >= 0 && column < tree[0].length;
    }
}
