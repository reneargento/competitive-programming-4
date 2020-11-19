package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 26/09/20.
 */
public class ShatteredCake {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cakeWidth = scanner.nextInt();
        int pieces = scanner.nextInt();
        long area = 0;

        for (int i = 0; i < pieces; i++) {
            int width = scanner.nextInt();
            int length = scanner.nextInt();
            area += width * length;
        }
        System.out.println(area / cakeWidth);
    }

}
