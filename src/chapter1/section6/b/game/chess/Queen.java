package chapter1.section6.b.game.chess;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/10/20.
 */
public class Queen {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int startRow = scanner.nextInt();
        int startColumn = scanner.nextInt();
        int endRow = scanner.nextInt();
        int endColumn = scanner.nextInt();

        while (startRow != 0 || startColumn != 0 || endRow != 0 || endColumn != 0) {
            if (startRow == endRow && startColumn == endColumn) {
                System.out.println(0);
            } else {
                int differenceStart = startRow - startColumn;
                int differenceEnd = endRow - endColumn;
                int sumStart = startRow + startColumn;
                int sumEnd = endRow + endColumn;
                if (startRow == endRow
                        || startColumn == endColumn
                        || differenceStart == differenceEnd
                        || sumStart == sumEnd) {
                    System.out.println(1);
                } else {
                    System.out.println(2);
                }
            }
            startRow = scanner.nextInt();
            startColumn = scanner.nextInt();
            endRow = scanner.nextInt();
            endColumn = scanner.nextInt();
        }
    }
}
