package chapter2.section2.section3;

import java.util.Scanner;

/**
 * Created by Rene Argento on 18/01/21.
 */
public class Exercise7 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int bits = scanner.nextInt(); // not used
            int k = scanner.nextInt();
            System.out.println(k ^ (k >> 1));
        }
    }
}
