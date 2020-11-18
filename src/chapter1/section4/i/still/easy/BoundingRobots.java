package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/09/20.
 */
public class BoundingRobots {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int width = scanner.nextInt();
        int length = scanner.nextInt();
        int test = 0;

        while (width != 0 || length != 0) {
            int segments = scanner.nextInt();
            int[] actual = new int[2];
            int[] imaginary = new int[2];

            for (int i = 0; i < segments; i++) {
                char direction = scanner.next().charAt(0);
                int moves = scanner.nextInt();
                move(actual, imaginary, direction, moves, width, length);
            }

            if (test > 0) {
                System.out.println();
            }
            System.out.printf("Robot thinks %d %d\n", imaginary[0], imaginary[1]);
            System.out.printf("Actually at %d %d\n", actual[0], actual[1]);

            test++;
            width = scanner.nextInt();
            length = scanner.nextInt();
        }
    }

    private static void move(int[] actual, int[] imaginary, char direction, int moves, int width, int length) {
        switch (direction) {
            case 'u':
                imaginary[1] += moves;
                actual[1] = Math.min(actual[1] + moves, length - 1);
                break;
            case 'd':
                imaginary[1] -= moves;
                actual[1] = Math.max(actual[1] - moves, 0);
                break;
            case 'r':
                imaginary[0] += moves;
                actual[0] = Math.min(actual[0] + moves, width - 1);
                break;
            case 'l':
                imaginary[0] -= moves;
                actual[0] = Math.max(actual[0] - moves, 0);
                break;
        }
    }

}
