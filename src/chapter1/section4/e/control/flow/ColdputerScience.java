package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class ColdputerScience {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int days = scanner.nextInt();
        int belowZero = 0;

        for (int i = 0; i < days; i++) {
            if (scanner.nextInt() < 0) {
                belowZero++;
            }
        }
        System.out.println(belowZero);
    }

}
