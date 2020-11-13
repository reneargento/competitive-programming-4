package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class OddGnome {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int gnomes = scanner.nextInt();

        for (int i = 0; i < gnomes; i++) {
            int groupSize = scanner.nextInt();
            int previous = -1;
            int kingLocation = -1;

            for (int g = 0; g < groupSize; g++) {
                int current = scanner.nextInt();

                if (g >= 1) {
                    if (current - previous != 1 && kingLocation == -1) {
                        kingLocation = g + 1;
                    }
                }

                previous = current;
            }
            System.out.println(kingLocation);
        }
    }
}
