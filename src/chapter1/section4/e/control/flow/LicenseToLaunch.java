package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class LicenseToLaunch {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int days = scanner.nextInt();
        int minSpaceJunk = Integer.MAX_VALUE;
        int daysToWait = 0;

        for (int i = 0; i < days; i++) {
            int spaceJunk = scanner.nextInt();

            if (spaceJunk < minSpaceJunk) {
                daysToWait = i;
                minSpaceJunk = spaceJunk;
            }
        }
        System.out.println(daysToWait);
    }

}
