package chapter1.section6.d.games.other.harder;

import java.util.*;

/**
 * Created by Rene Argento on 28/10/20.
 */
public class HiQ {

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
        int games = scanner.nextInt();
        int[][] board = createPegBoard();
        Map<Integer, Cell> pegMap = getPegMap(board);
        System.out.println("HI Q OUTPUT");

        for (int g = 0; g < games; g++) {
            Set<Integer> pegs = new HashSet<>();

            int peg = scanner.nextInt();
            while (peg != 0) {
                pegs.add(peg);
                peg = scanner.nextInt();
            }
            playGame(board, pegMap, pegs);
            System.out.println(getResult(pegs));
        }
        System.out.println("END OF OUTPUT");
    }

    private static int[][] createPegBoard() {
        return new int[][] {
                { 0, 0, 1, 2, 3, 0, 0 },
                { 0, 0, 4, 5, 6, 0, 0 },
                { 7, 8, 9, 10, 11, 12, 13 },
                { 14, 15, 16, 17, 18, 19, 20 },
                { 21, 22, 23, 24, 25, 26, 27 },
                { 0, 0, 28, 29, 30, 0, 0 },
                { 0, 0, 31, 32, 33, 0, 0 }
        };
    }

    private static Map<Integer, Cell> getPegMap(int[][] board) {
        Map<Integer, Cell> pegMap = new HashMap<>();

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] != 0) {
                    pegMap.put(board[r][c], new Cell(r, c));
                }
            }
        }
        return pegMap;
    }

    private static void playGame(int[][] board, Map<Integer, Cell> pegMap, Set<Integer> pegs) {
        int[] neighborRows = {-1, 0, 0, 1};
        int[] neighborColumns = {0, -1, 1, 0};

        while (true) {
            boolean moveTaken = false;
            int source = 0;
            int target = 0;
            int pegJumped = 0;

            for (Integer peg : pegs) {
                Cell cell = pegMap.get(peg);
                int currentSource = board[cell.row][cell.column];

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = cell.row + neighborRows[i];
                    int neighborColumn = cell.column + neighborColumns[i];

                    int targetRow = neighborRow + neighborRows[i];
                    int targetColumn = neighborColumn + neighborColumns[i];

                    if (isValid(board, neighborRow, neighborColumn)
                            && isValid(board, targetRow, targetColumn)) {

                        boolean isJump = pegs.contains(board[neighborRow][neighborColumn])
                                && !pegs.contains(board[targetRow][targetColumn]);

                        if (isJump) {
                            int pegToJump = board[neighborRow][neighborColumn];
                            int currentTarget = board[targetRow][targetColumn];

                            if (currentTarget > target
                                    || (currentTarget == target && currentSource > source)) {
                                pegJumped = pegToJump;
                                source = currentSource;
                                target = currentTarget;
                                moveTaken = true;
                            }
                        }
                    }
                }
            }

            if (moveTaken) {
                pegs.remove(pegJumped);
                pegs.remove(source);
                pegs.add(target);
            } else {
                break;
            }
        }
    }

    private static boolean isValid(int[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length && board[row][column] != 0;
    }

    private static int getResult(Set<Integer> pegs) {
        int result = 0;
        for (Integer peg : pegs) {
            result += peg;
        }
        return result;
    }

}
