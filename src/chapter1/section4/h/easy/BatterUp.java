package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class BatterUp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int atBats = scanner.nextInt();

        double officialAtBats = 0;
        double sum = 0;

        for (int i = 0; i < atBats; i++) {
            int number = scanner.nextInt();

            if (number != -1) {
                officialAtBats++;
                sum += number;
            }
        }
        System.out.println(sum / officialAtBats);
    }

}
