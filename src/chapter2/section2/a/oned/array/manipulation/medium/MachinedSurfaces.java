package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/01/21.
 */
public class MachinedSurfaces {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int lines = scanner.nextInt();

        while (lines != 0) {
            scanner.nextLine();
            int maxPrintedCells = Integer.MIN_VALUE;
            int[] printedCellsPerLine = new int[lines];

            for (int l = 0; l < lines; l++) {
                String line = scanner.nextLine();
                int printedCells = 0;

                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'X') {
                        printedCells++;
                    }
                }

                printedCellsPerLine[l] = printedCells;
                maxPrintedCells = Math.max(maxPrintedCells, printedCells);
            }

            int totalVoid = 0;
            for (int printedCells : printedCellsPerLine) {
                totalVoid += maxPrintedCells - printedCells;
            }
            System.out.println(totalVoid);

            lines = scanner.nextInt();
        }
    }
}
