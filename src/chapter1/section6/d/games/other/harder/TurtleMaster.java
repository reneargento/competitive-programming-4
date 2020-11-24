package chapter1.section6.d.games.other.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 23/10/20.
 */
public class TurtleMaster {

    private enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] board = new char[8][8];

        for (int i = board.length - 1; i >= 0; i--) {
            String line = scanner.next();
            board[i] = line.toCharArray();
        }

        String instructions = scanner.next();
        int row = 0;
        int column = 0;
        Direction direction = Direction.RIGHT;
        boolean bug = false;

        for (int i = 0; i < instructions.length(); i++) {
            char instruction = instructions.charAt(i);
            switch (instruction) {
                case 'F':
                    int[] nextPosition = getNextPosition(row, column, direction);
                    int nextRow = nextPosition[0];
                    int nextColumn = nextPosition[1];

                    if (!isValidMove(board, nextRow, nextColumn)) {
                        bug = true;
                        break;
                    }
                    row = nextRow;
                    column = nextColumn;
                    break;
                case 'R': direction = rotate(direction, true);
                    break;
                case 'L': direction = rotate(direction, false);
                    break;
                default:
                    boolean result = fireLaser(board, row, column, direction);
                    if (!result) {
                        bug = true;
                        break;
                    }
                    break;
            }

            if (bug) {
                break;
            }
        }

        boolean foundDiamond = !bug && board[row][column] == 'D';
        System.out.println(foundDiamond ? "Diamond!" : "Bug!");
    }

    private static int[] getNextPosition(int row, int column, Direction direction) {
        switch (direction) {
            case UP: row++; break;
            case DOWN: row--; break;
            case RIGHT: column++; break;
            case LEFT: column--;
        }
        return new int[]{row, column};
    }

    private static boolean isValidMove(char[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length
                && board[row][column] != 'C' && board[row][column] != 'I';
    }

    private static Direction rotate(Direction currentDirection, boolean right) {
        switch (currentDirection) {
            case RIGHT: return right ? Direction.DOWN : Direction.UP;
            case LEFT: return right ? Direction.UP : Direction.DOWN;
            case UP: return right ? Direction.RIGHT : Direction.LEFT;
            default: return right ? Direction.LEFT : Direction.RIGHT;
        }
    }

    private static boolean fireLaser(char[][] board, int row, int column, Direction direction) {
        int[] nextPosition = getNextPosition(row, column, direction);
        row = nextPosition[0];
        column = nextPosition[1];

        boolean isIceCastle = row >= 0 && row < board.length && column >= 0 && column < board[0].length
                && board[row][column] == 'I';
        if (isIceCastle) {
            board[row][column] = '.';
        }
        return isIceCastle;
    }

}
