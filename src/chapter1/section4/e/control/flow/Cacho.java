package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Cacho {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            int previous = -1;
            boolean scale = true;

            for (int d = 0; d < 5; d++) {
                int current = scanner.nextInt();

                if (d >= 1) {
                    if (current != previous + 1) {
                        scale = false;
                    }
                }
                previous = current;
            }
            System.out.println(scale ? "Y" : "N");
        }
    }

}
