package chapter1.section6.b.game.chess;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/10/20.
 */
public class Bijele {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%d %d %d %d %d %d\n",
                1 - scanner.nextInt(),
                1 - scanner.nextInt(),
                2 - scanner.nextInt(),
                2 - scanner.nextInt(),
                2 - scanner.nextInt(),
                8 - scanner.nextInt());
    }

}
