package chapter1.section6.h.time.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 18/11/20.
 */
public class Spavanac {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int hours = scanner.nextInt();
        int minutes = scanner.nextInt();

        if (minutes >= 45) {
            minutes -= 45;
        } else {
            int remaining = 45 - minutes;
            minutes = 60 - remaining;

            if (hours >= 1) {
                hours--;
            } else {
                hours = 23;
            }
        }
        System.out.printf("%d %d\n", hours, minutes);
    }
}
