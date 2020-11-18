package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class EventPlanning {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int people = scanner.nextInt();
            int budget = scanner.nextInt();
            int hotels = scanner.nextInt();
            int weeks = scanner.nextInt();

            int cheapestPrice = Integer.MAX_VALUE;

            for (int h = 0; h < hotels; h++) {
                int hotelPrice = scanner.nextInt();
                int price = hotelPrice * people;

                for (int w = 0; w < weeks; w++) {
                    int beds = scanner.nextInt();

                    if (beds >= people && price < cheapestPrice) {
                        cheapestPrice = price;
                    }
                }
            }

            if (cheapestPrice <= budget) {
                System.out.println(cheapestPrice);
            } else {
                System.out.println("stay home");
            }
        }
    }

}
