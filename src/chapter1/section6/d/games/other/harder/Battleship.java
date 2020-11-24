package chapter1.section6.d.games.other.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/10/20.
 */
public class Battleship {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            int orders = scanner.nextInt();

            char[][] mapPlayer1 = new char[height][width];
            char[][] mapPlayer2 = new char[height][width];

            int player1Ships = readMap(scanner, mapPlayer1);
            int player2Ships = readMap(scanner, mapPlayer2);

            boolean player1Over = false;
            boolean player2Over = false;
            boolean gameOver = false;

            boolean player1 = true;
            int turn = 0;

            for (int i = 0; i < orders; i++) {
                int column = scanner.nextInt();
                int row = scanner.nextInt();

                if (gameOver) {
                    continue;
                }

                if (player1) {
                    if (mapPlayer2[row][column] == '#') {
                        player2Ships--;
                        mapPlayer2[row][column] = '_';

                        if (player2Ships == 0) {
                            player1Over = true;
                            if (turn == 1 || player2Over) {
                                gameOver = true;
                            } else {
                                turn = 1 - turn;
                                player1 = false;
                            }
                        }
                    } else if (player2Over) {
                        gameOver = true;
                    } else {
                        turn = 1 - turn;
                        player1 = false;
                    }
                } else {
                    if (mapPlayer1[row][column] == '#') {
                        player1Ships--;
                        mapPlayer1[row][column] = '_';

                        if (player1Ships == 0) {
                            player2Over = true;
                            if (turn == 1 || player1Over) {
                                gameOver = true;
                            } else {
                                turn = 1 - turn;
                                player1 = true;
                            }
                        }
                    } else if (player1Over) {
                        gameOver = true;
                    } else {
                        turn = 1 - turn;
                        player1 = true;
                    }
                }
            }

            if (player2Ships == 0 && player1Ships > 0) {
                System.out.println("player one wins");
            } else if (player2Ships > 0 && player1Ships == 0) {
                System.out.println("player two wins");
            } else {
                System.out.println("draw");
            }
        }
    }

    private static int readMap(Scanner scanner, char[][] map) {
        int ships = 0;

        for (int i = map.length - 1; i >= 0; i--) {
            String line = scanner.next();
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == '#') {
                    ships++;
                }
            }
        }
        return ships;
    }

}
