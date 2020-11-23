package chapter1.section6.c.games.other.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 17/10/20.
 */
public class GuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int guess = scanner.nextInt();
        scanner.nextLine();
        int min = 0;
        int max = 11;

        while (guess != 0) {
            String response = scanner.nextLine();

            if (response.equals("too low")) {
                min = Math.max(guess + 1, min);
            } else if (response.equals("too high")) {
                max = Math.min(guess - 1, max);
            } else {
                if (min <= guess && guess <= max) {
                    System.out.println("Stan may be honest");
                } else {
                    System.out.println("Stan is dishonest");
                }
                min = 0;
                max = 11;
            }

            guess = scanner.nextInt();
            scanner.nextLine();
        }
    }

}
