package chapter1.section4.c.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class QuadrantSelection {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        int quadrant;

        if (x > 0) {
            if (y > 0) {
                quadrant = 1;
            } else {
                quadrant = 4;
            }
        } else {
            if (y > 0) {
                quadrant = 2;
            } else {
                quadrant = 3;
            }
        }
        System.out.println(quadrant);
    }

}
