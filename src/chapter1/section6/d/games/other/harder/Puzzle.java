package chapter1.section6.d.games.other.harder;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 25/10/20.
 */
public class Puzzle {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        int game = 1;

        while (line.charAt(0) != 'Z') {
            char[][] puzzle = new char[5][5];
            puzzle[0] = line.toCharArray();
            int emptyRow = 0;
            int emptyColumn = line.indexOf(' ');

            for (int i = 1; i <= 4; i++) {
                String nextLine = scanner.nextLine();
                if (nextLine.contains(" ")) {
                    emptyRow = i;
                    emptyColumn = nextLine.indexOf(' ');
                    puzzle[i] = nextLine.toCharArray();
                } else if (nextLine.length() == 4) {
                    emptyRow = i;
                    emptyColumn = 4;

                    for (int c = 0; c < 4; c++) {
                        puzzle[i][c] = nextLine.charAt(c);
                    }
                    puzzle[i][4] = ' ';
                } else {
                    puzzle[i] = nextLine.toCharArray();
                }
            }

            boolean possible = true;

            while (true) {
                boolean finishedMoves = false;
                String moves = scanner.nextLine();

                for (int i = 0; i < moves.length(); i++) {
                    char move = moves.charAt(i);
                    if (move == '0') {
                        finishedMoves = true;
                        break;
                    }

                    if (!possible) {
                        continue;
                    }

                    switch (move) {
                        case 'A':
                            if (emptyRow == 0) {
                                possible = false;
                            } else {
                                puzzle[emptyRow][emptyColumn] = puzzle[emptyRow - 1][emptyColumn];
                                puzzle[emptyRow - 1][emptyColumn] = ' ';
                                emptyRow--;
                            }
                            break;
                        case 'B':
                            if (emptyRow == 4) {
                                possible = false;
                            } else {
                                puzzle[emptyRow][emptyColumn] = puzzle[emptyRow + 1][emptyColumn];
                                puzzle[emptyRow + 1][emptyColumn] = ' ';
                                emptyRow++;
                            }
                            break;
                        case 'L':
                            if (emptyColumn == 0) {
                                possible = false;
                            } else {
                                puzzle[emptyRow][emptyColumn] = puzzle[emptyRow][emptyColumn - 1];
                                puzzle[emptyRow][emptyColumn - 1] = ' ';
                                emptyColumn--;
                            }
                            break;
                        default:
                            if (emptyColumn == 4) {
                                possible = false;
                            } else {
                                puzzle[emptyRow][emptyColumn] = puzzle[emptyRow][emptyColumn + 1];
                                puzzle[emptyRow][emptyColumn + 1] = ' ';
                                emptyColumn++;
                            }
                            break;
                    }

                }
                if (finishedMoves) {
                    break;
                }
            }

            if (game > 1) {
                System.out.println();
            }
            System.out.printf("Puzzle #%d:\n", game);
            if (!possible) {
                System.out.println("This puzzle has no final configuration.");
            } else {
                for (int r = 0; r < puzzle.length; r++) {
                    StringJoiner puzzleLine = new StringJoiner(" ");

                    for (int c = 0; c < puzzle[0].length; c++) {
                        puzzleLine.add(String.valueOf(puzzle[r][c]));

                    }
                    System.out.println(puzzleLine);
                }

            }
            game++;
            line = scanner.nextLine();
        }
    }

}
