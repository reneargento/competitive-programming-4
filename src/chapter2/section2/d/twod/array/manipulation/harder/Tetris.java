package chapter2.section2.d.twod.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 13/02/21.
 */
public class Tetris {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int columns = scanner.nextInt();
        int pieceIndex = scanner.nextInt() - 1;

        int[][][] pieceHeights = getPieceHeights();
        int[][] piece = pieceHeights[pieceIndex];
        int[] fieldHeights = new int[columns];

        for (int i = 0; i < columns; i++) {
            fieldHeights[i] = scanner.nextInt();
        }

        int waysToDropPiece = countWaysToDropPiece(fieldHeights, piece);
        System.out.println(waysToDropPiece);
    }

    private static int countWaysToDropPiece(int[] fieldHeights, int[][] pieces) {
        int waysToDrop = 0;

        for (int configuration = 0; configuration < pieces.length; configuration++) {
            for (int startColumn = 0; startColumn <= fieldHeights.length - pieces[configuration].length; startColumn++) {
                if (canDropPiece(fieldHeights, startColumn, pieces[configuration])) {
                    waysToDrop++;
                }
            }
        }
        return waysToDrop;
    }

    private static boolean canDropPiece(int[] fieldHeights, int startColumn, int[] pieceHeights) {

        for (int heightIndex = 0; heightIndex < pieceHeights.length; heightIndex++) {
            int columnToPlaceOnField = startColumn + heightIndex;

            if (heightIndex > 0) {
                int deltaPieceHeight = pieceHeights[heightIndex] - pieceHeights[heightIndex - 1];
                int deltaFieldHeight = fieldHeights[columnToPlaceOnField] - fieldHeights[columnToPlaceOnField - 1];
                if (deltaPieceHeight != deltaFieldHeight) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int[][][] getPieceHeights() {
        int[][][] pieces = new int[7][][];
        pieces[0] = new int[][] {
                {1},
                {1, 1, 1, 1}
        };
        pieces[1] = new int[][] {
                {1, 1}
        };
        pieces[2] = new int[][] {
                {1, 1, 2},
                {2, 1}
        };
        pieces[3] = new int[][] {
                {2, 1, 1},
                {1, 2}
        };
        pieces[4] = new int[][] {
                {1, 1, 1},
                {1, 2},
                {2, 1, 2},
                {2, 1}
        };
        pieces[5] = new int[][] {
                {1, 1, 1},
                {1, 1},
                {1, 2, 2},
                {3, 1}
        };
        pieces[6] = new int[][] {
                {1, 1, 1},
                {1, 3},
                {2, 2, 1},
                {1, 1}
        };
        return pieces;
    }
}
