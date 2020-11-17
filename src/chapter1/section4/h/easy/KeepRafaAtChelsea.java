package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class KeepRafaAtChelsea {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int matches = scanner.nextInt();

            char previous2 = 'a';
            char previous1 = 'a';
            boolean lostJob = false;

            for (int i = 1; i <= matches; i++) {
                char current = scanner.next().charAt(0);

                if (i >= 3) {
                    if (previous2 != 'W' && previous1 != 'W' && current != 'W' && !lostJob) {
                        System.out.printf("Case %d: %d\n", t, i);
                        lostJob = true;
                    }
                }

                previous2 = previous1;
                previous1 = current;
            }

            if (!lostJob) {
                System.out.printf("Case %d: Yay! Mighty Rafa persists!\n", t);
            }
        }
    }

}
