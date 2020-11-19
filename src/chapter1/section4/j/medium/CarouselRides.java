package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/09/20.
 */
public class CarouselRides {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int offers = scanner.nextInt();
        int maxTickets = scanner.nextInt();

        while (offers != 0 || maxTickets != 0) {
            int bestPrice = -1;
            int bestTickets = 0;
            double bestRatio = Integer.MAX_VALUE;

            for (int i = 0; i < offers; i++) {
                int tickets = scanner.nextInt();
                int price = scanner.nextInt();

                if (tickets <= maxTickets) {
                    double ratio = price / (double) tickets;
                    if (ratio < bestRatio
                            || (ratio == bestRatio && tickets > bestTickets)) {
                        bestPrice = price;
                        bestRatio = ratio;
                        bestTickets = tickets;
                    }
                }
            }

            if (bestRatio == Integer.MAX_VALUE) {
                System.out.println("No suitable tickets offered");
            } else {
                System.out.printf("Buy %d tickets for $%d\n", bestTickets, bestPrice);
            }

            offers = scanner.nextInt();
            maxTickets = scanner.nextInt();
        }
    }

}
