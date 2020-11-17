package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class SevenWonders {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cards = scanner.next();

        int tablet = 0;
        int compass = 0;
        int gear = 0;

        for (int i = 0; i < cards.length(); i++) {
            switch (cards.charAt(i)) {
                case 'T': tablet++; break;
                case 'C': compass++; break;
                default: gear++; break;
            }
        }

        int completeSets = Math.min(tablet, compass);
        completeSets = Math.min(completeSets, gear);

        int points = tablet * tablet + compass * compass + gear * gear + (completeSets * 7);
        System.out.println(points);
    }

}
