package chapter1.section6.d.games.other.harder;

import java.util.*;

/**
 * Created by Rene Argento on 24/10/20.
 */
public class Othello {

    private static class Cell implements Comparable<Cell> {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object other) {
            Cell cell = (Cell) other;
            return row == cell.row &&
                    column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }

        @Override
        public int compareTo(Cell other) {
            if (row != other.row) {
                return row - other.row;
            }
            return column - other.column;
        }

        @Override
        public String toString() {
            return "(" + row + "," + column + ")";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int games = scanner.nextInt();

        for (int t = 0; t < games; t++) {
            if (t > 0) {
                System.out.println();
            }

            char[][] board = new char[8][8];

            for (int i = 0; i < board.length; i++) {
                String line = scanner.next();
                board[i] = line.toCharArray();
            }

            char[] players = new char[2];
            players[0] = scanner.next().charAt(0);
            players[1] = (players[0] == 'W') ? 'B' : 'W';
            int moveIndex = 0;

            while (true) {
                char player = players[moveIndex];
                char opponent = players[1 - moveIndex];
                boolean gameOver = false;

                String move = scanner.next();
                switch (move.charAt(0)) {
                    case 'L': listPossibleMoves(board, player, opponent);
                        break;
                    case 'M':
                        int row = Character.getNumericValue(move.charAt(1)) - 1;
                        int column = Character.getNumericValue(move.charAt(2)) - 1;
                        if (getPossibleMoves(board, player, opponent).isEmpty()) {
                            moveIndex = 1 - moveIndex;
                            char aux = player;
                            player = opponent;
                            opponent = aux;
                        }
                        move(board, player, opponent, row, column);
                        int[] pieces = countPieces(board);
                        System.out.printf("Black - %2d White - %2d\n", pieces[0], pieces[1]);
                        moveIndex = 1 - moveIndex;
                        break;
                    default:
                        printBoard(board);
                        gameOver = true;
                }
                if (gameOver) {
                    break;
                }
            }
        }
    }

    private static void listPossibleMoves(char[][] board, char player, char opponent) {
        Set<Cell> moves = getPossibleMoves(board, player, opponent);
        if (moves.isEmpty()) {
            System.out.println("No legal move.");
            return;
        }
        List<Cell> movesList = new ArrayList<>(moves);

        Collections.sort(movesList);
        StringJoiner movesDescription = new StringJoiner(" ");
        for (Cell cell : movesList) {
            movesDescription.add(cell.toString());
        }
        System.out.println(movesDescription);
    }

    private static Set<Cell> getPossibleMoves(char[][] board, char player, char opponent) {
        Set<Cell> moves = new HashSet<>();

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == player) {
                    checkLine(board, opponent, r, c, moves);
                }
            }
        }
        return moves;
    }

    private static void checkLine(char[][] board, char opponent, int row, int column, Set<Cell> moves) {
        searchCell(board, opponent, row, column, moves, new int[]{1, 0}, new int[]{board.length, 9});
        searchCell(board, opponent, row, column, moves, new int[]{-1, 0}, new int[]{-1, 9});
        searchCell(board, opponent, row, column, moves, new int[]{0, 1}, new int[]{9, board[0].length});
        searchCell(board, opponent, row, column, moves, new int[]{0, -1}, new int[]{9, -1});
        searchCell(board, opponent, row, column, moves, new int[]{-1, 1}, new int[]{-1, board[0].length});
        searchCell(board, opponent, row, column, moves, new int[]{-1, -1}, new int[]{-1, -1});
        searchCell(board, opponent, row, column, moves, new int[]{1, 1}, new int[]{board.length, board[0].length});
        searchCell(board, opponent, row, column, moves, new int[]{1, -1}, new int[]{board.length, -1});
    }

    private static void searchCell(char[][] board, char opponent, int row, int column, Set<Cell> movesToAdd,
                                      int[] increments, int[] ends) {
        boolean foundOpponent = false;

        int r = row + increments[0];
        int c = column + increments[1];

        while (true) {
            if (r == ends[0] || c == ends[1]) {
                break;
            }

            if (board[r][c] == opponent) {
                foundOpponent = true;
            } else {
                if (board[r][c] == '-' && foundOpponent) {
                    movesToAdd.add(new Cell(r + 1, c + 1));
                }
                break;
            }

            r += increments[0];
            c += increments[1];
        }
    }

    private static void move(char[][] board, char player, char opponent, int row, int column, int[] increments,
                             int[] ends) {
        int r = row + increments[0];
        int c = column + increments[1];

        while (true) {
            if (r == ends[0] || c == ends[1] || board[r][c] == '-') {
                break;
            }

            if (board[r][c] == player) {
                int newRow = row + increments[0];
                int newColumn = column + increments[1];

                while (true) {
                    if (newRow == r && newColumn == c) {
                        break;
                    }

                    swapIfNeeded(board, opponent, player, newRow, newColumn);

                    newRow += increments[0];
                    newColumn += increments[1];
                }
                break;
            }

            r += increments[0];
            c += increments[1];
        }
    }

    private static void move(char[][] board, char player, char opponent, int row, int column) {
        board[row][column] = player;

        move(board, player, opponent, row, column, new int[]{0, 1}, new int[]{9, board[0].length});
        move(board, player, opponent, row, column, new int[]{0, -1}, new int[]{9, -1});
        move(board, player, opponent, row, column, new int[]{1, 0}, new int[]{board.length, 9});
        move(board, player, opponent, row, column, new int[]{-1, 0}, new int[]{-1, 9});
        move(board, player, opponent, row, column, new int[]{1, 1}, new int[]{board.length, board[0].length});
        move(board, player, opponent, row, column, new int[]{-1, 1}, new int[]{-1, board[0].length});
        move(board, player, opponent, row, column, new int[]{1, -1}, new int[]{board.length, -1});
        move(board, player, opponent, row, column, new int[]{-1, -1}, new int[]{-1, -1});
    }

    private static void swapIfNeeded(char[][] board, char opponent, char player, int row, int column) {
        if (board[row][column] == opponent) {
            board[row][column] = player;
        }
    }

    private static int[] countPieces(char[][] board) {
        int black = 0;
        int white = 0;

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 'W') {
                    white++;
                } else if (board[r][c] == 'B') {
                    black++;
                }
            }
        }
        return new int[]{black, white};
    }

    private static void printBoard(char[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(board[r][c]);
            }
            System.out.println();
        }
    }
}
