package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class DeliciousBubbleTea {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] teas = new int[scanner.nextInt()];

        for (int i = 0; i < teas.length; i++) {
            teas[i] = scanner.nextInt();
        }

        int[] toppings = new int[scanner.nextInt()];

        for (int i = 0; i < toppings.length; i++) {
            toppings[i] = scanner.nextInt();
        }

        int cheapestCombination = Integer.MAX_VALUE;

        for (int tea : teas) {
            int toppingsAvailable = scanner.nextInt();

            for (int t = 0; t < toppingsAvailable; t++) {
                int toppingId = scanner.nextInt() - 1;
                int price = tea + toppings[toppingId];
                cheapestCombination = Math.min(cheapestCombination, price);
            }
        }

        int money = scanner.nextInt();

        int studentTeas = money / cheapestCombination;
        if (studentTeas > 0) {
            studentTeas--;
        }
        System.out.println(studentTeas);
    }

}
