package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/01/21.
 */
public class BrickGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int members = scanner.nextInt();
            int captainIndex = members / 2;
            int captainAge = -1;

            for (int i = 0; i < members; i++) {
                int age = scanner.nextInt();
                if (i == captainIndex) {
                    captainAge = age;
                }
            }
            System.out.printf("Case %d: %d\n", t, captainAge);
        }
    }
}
