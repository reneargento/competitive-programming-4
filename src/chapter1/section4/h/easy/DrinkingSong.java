package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class DrinkingSong {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int bottles = scanner.nextInt();
        String beverage = scanner.next();

        for (int i = bottles; i >= 1; i--) {
            if (i >= 3) {
                System.out.printf("%d bottles of %s on the wall, %d bottles of %s.\n" +
                        "Take one down, pass it around, %d bottles of %s on the wall.\n\n", i, beverage, i, beverage,
                        i - 1, beverage);
            } else if (i == 2) {
                System.out.printf("%d bottles of %s on the wall, %d bottles of %s.\n" +
                        "Take one down, pass it around, %d bottle of %s on the wall.\n\n", i, beverage, i, beverage,
                        i - 1, beverage);

            } else {
                System.out.printf("%d bottle of %s on the wall, %d bottle of %s.\n" +
                        "Take it down, pass it around, no more bottles of %s.\n", i, beverage, i, beverage, beverage);
            }
        }
    }

}
