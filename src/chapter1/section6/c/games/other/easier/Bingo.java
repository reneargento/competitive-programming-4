package chapter1.section6.c.games.other.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 20/10/20.
 */
public class Bingo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numbers = scanner.nextInt();
        int ballsNumber = scanner.nextInt();

        while (numbers != 0 || ballsNumber != 0) {
            int[] balls = new int[ballsNumber];

            for (int i = 0; i < ballsNumber; i++) {
                balls[i] = scanner.nextInt();
            }

            boolean possible = true;

            for (int number = 0; number <= numbers; number++) {
                boolean found = false;

                for (int i = 0; i < balls.length; i++) {
                    if (balls[i] == number) {
                        found = true;
                        break;
                    }
                    for (int j = 0; j < balls.length; j++) {
                        if (i == j) {
                            continue;
                        }
                        if (balls[i] - balls[j] == number) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }

                if (!found) {
                    possible = false;
                    break;
                }
            }

            System.out.println(possible ? "Y" : "N");
            numbers = scanner.nextInt();
            ballsNumber = scanner.nextInt();
        }
    }

}
