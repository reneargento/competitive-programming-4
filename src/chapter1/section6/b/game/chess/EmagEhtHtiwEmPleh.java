package chapter1.section6.b.game.chess;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/10/20.
 */
public class EmagEhtHtiwEmPleh {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][] board = createBoard();

        for (int i = 0; i < 2; i++) {
            String line = scanner.nextLine();
            String[] values = line.split(" ");
            boolean isBlack = i == 1;

            if (values.length > 1) {
                String[] cells = values[1].split(",");

                for (String cell : cells) {
                    if (cell.length() == 2) {
                        cell = "P" + cell;
                    }

                    char piece = cell.charAt(0);
                    int column = cell.charAt(1) - 'a';
                    int row = 8 - Character.getNumericValue(cell.charAt(2));

                    if (isBlack) {
                        piece = Character.toLowerCase(piece);
                    }

                    String cellChar = String.valueOf(board[row][column].charAt(0));
                    board[row][column] = cellChar + piece + cellChar;
                }
            }
        }
        printBoard(board);
    }

    private static String[][] createBoard() {
        String[][] board = new String[8][8];
        boolean whiteCell = false;

        for (int row = 0; row < board.length; row++) {
            whiteCell = !whiteCell;
            for (int column = 0; column < board[0].length; column++) {
                if (whiteCell) {
                    board[row][column] = "...";
                } else {
                    board[row][column] = ":::";
                }
                whiteCell = !whiteCell;
            }
        }
        return board;
    }

    private static void printBoard(String[][] board) {
        System.out.println("+---+---+---+---+---+---+---+---+");

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                System.out.print("|" + board[row][column]);
            }
            System.out.println("|");
            System.out.println("+---+---+---+---+---+---+---+---+");
        }
    }

}
