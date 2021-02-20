package chapter2.section2.d.twod.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 07/02/21.
 */
public class PolyominoComposer {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int largePolyominoSize = scanner.nextInt();
        int smallPolyominoSize = scanner.nextInt();

        while (largePolyominoSize != 0 || smallPolyominoSize != 0) {
            char[][] largePolyomino = new char[largePolyominoSize][largePolyominoSize];
            for (int row = 0; row < largePolyomino.length; row++) {
                largePolyomino[row] = scanner.next().toCharArray();
            }

            char[][] smallPolyomino = new char[smallPolyominoSize][smallPolyominoSize];
            for (int row = 0; row < smallPolyomino.length; row++) {
                smallPolyomino[row] = scanner.next().toCharArray();
            }

            int canCompose = canCompose(largePolyomino, smallPolyomino);
            System.out.println(canCompose);

            largePolyominoSize = scanner.nextInt();
            smallPolyominoSize = scanner.nextInt();
        }
    }

    private static int canCompose(char[][] largePolyomino, char[][] smallPolyomino) {
        Cell smallPolyominoStart = getSmallPolyominoStart(smallPolyomino);
        int piecesUsed = 0;

        for (int row = 0; row < largePolyomino.length; row++) {
            for (int column = 0; column < largePolyomino[0].length; column++) {
                if (largePolyomino[row][column] == '*') {
                    boolean placed = placeOnTop(largePolyomino, smallPolyomino, row, column, smallPolyominoStart);
                    if (!placed) {
                        return 0;
                    }
                    piecesUsed++;
                }
            }
        }
        return piecesUsed == 2 ? 1 : 0;
    }

    private static boolean placeOnTop(char[][] largePolyomino, char[][] smallPolyomino, int largeRow, int largeColumn,
                                      Cell smallPolyominoStart) {
        int smallRowStart = smallPolyominoStart.row;
        int smallColumnStart = smallPolyominoStart.column;

        for (int row = smallRowStart; row < smallPolyomino.length; row++) {
            int startColumn = row == smallRowStart ? smallColumnStart : 0;
            for (int column = startColumn; column < smallPolyomino[0].length; column++) {
                if (smallPolyomino[row][column] == '*') {
                    int rowToPlace = largeRow + (row - smallRowStart);
                    int columnToPlace = largeColumn + (column - smallColumnStart);

                    if (rowToPlace >= largePolyomino.length || columnToPlace >= largePolyomino[0].length
                            || largePolyomino[rowToPlace][columnToPlace] != '*') {
                        return false;
                    }
                    largePolyomino[rowToPlace][columnToPlace] = '.';
                }
            }
        }
        return true;
    }

    private static Cell getSmallPolyominoStart(char[][] smallPolyomino) {
        for (int row = 0; row < smallPolyomino.length; row++) {
            for (int column = 0; column < smallPolyomino[0].length; column++) {
                if (smallPolyomino[row][column] == '*') {
                    return new Cell(row, column);
                }
            }
        }
        return null;
    }

}
