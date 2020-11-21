package chapter1.section6.b.game.chess;

import java.util.*;

/**
 * Created by Rene Argento on 15/10/20.
 */
public class MoveTheBishop {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();

        for (int t = 0; t < testCases; t++) {
            int tests = scanner.nextInt();
            int boardDimension = scanner.nextInt();

            for (int i = 0; i < tests; i++) {
                int startRow = scanner.nextInt();
                int startColumn = scanner.nextInt();
                int endRow = scanner.nextInt();
                int endColumn = scanner.nextInt();

                if (isPossible(startRow, startColumn, endRow, endColumn)) {
                    if (startRow == endRow && startColumn == endColumn) {
                        System.out.println(0);
                    } else {
                        int differenceStart = startRow - startColumn;
                        int differenceEnd = endRow - endColumn;
                        int sumStart = startRow + startColumn;
                        int sumEnd = endRow + endColumn;
                        if (differenceStart == differenceEnd || sumStart == sumEnd) {
                            System.out.println(1);
                        } else {
                            System.out.println(2);
                        }
                    }
                } else {
                    System.out.println("no move");
                }
            }
        }
    }

    private static boolean isBlack(int row, int column) {
        return (row % 2 == 0 && column % 2 == 0) || (row % 2 != 0 && column % 2 != 0);
    }

    private static boolean isPossible(int startRow, int startColumn, int endRow, int endColumn) {
        return isBlack(startRow, startColumn) == isBlack(endRow, endColumn);
    }
}
