package chapter1.section6.c.games.other.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 20/10/20.
 */
public class Trik {

    private static class Game {
        int leftCup = 1;
        int middleCup;
        int rightCup;

        private void moveA() {
            int aux = leftCup;
            leftCup = middleCup;
            middleCup = aux;
        }

        private void moveB() {
            int aux = middleCup;
            middleCup = rightCup;
            rightCup = aux;
        }

        private void moveC() {
            int aux = leftCup;
            leftCup = rightCup;
            rightCup = aux;
        }

        private int getCupWithBall() {
            if (leftCup == 1) {
               return 1;
            } else if (middleCup == 1) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String moves = scanner.next();
        Game game = new Game();

        for (int i = 0; i < moves.length(); i++) {
            switch (moves.charAt(i)) {
                case 'A': game.moveA(); break;
                case 'B': game.moveB(); break;
                default: game.moveC();
            }
        }
        System.out.println(game.getCupWithBall());
    }

}
