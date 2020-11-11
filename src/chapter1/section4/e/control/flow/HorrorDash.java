package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class HorrorDash {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 1; i <= tests; i++) {
            int students = scanner.nextInt();
            int maxSpeed = Integer.MIN_VALUE;

            for (int s = 0; s < students; s++) {
                maxSpeed = Math.max(maxSpeed, scanner.nextInt());
            }
            System.out.printf("Case %d: %d\n", i, maxSpeed);
        }
    }

}
