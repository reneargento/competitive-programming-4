package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Parking {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            int stores = scanner.nextInt();
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for (int s = 0; s < stores; s++) {
                int location = scanner.nextInt();
                min = Math.min(min, location);
                max = Math.max(max, location);
            }
            int distance = (max - min) * 2;
            System.out.println(distance);
        }
    }

}
