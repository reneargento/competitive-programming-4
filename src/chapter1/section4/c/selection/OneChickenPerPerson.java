package chapter1.section4.c.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class OneChickenPerPerson {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int people = scanner.nextInt();
        int chickenPieces = scanner.nextInt();

        if (people > chickenPieces) {
            int missing = people - chickenPieces;
            String piece = missing > 1 ? "pieces" : "piece";
            System.out.println("Dr. Chaz needs " + missing + " more " + piece + " of chicken!");
        } else {
            int remaining = chickenPieces - people;
            String piece = remaining > 1 ? "pieces" : "piece";
            System.out.println("Dr. Chaz will have " + remaining + " " + piece + " of chicken left over!");
        }
    }

}
