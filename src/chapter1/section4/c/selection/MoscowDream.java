package chapter1.section4.c.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class MoscowDream {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int easy = scanner.nextInt();
        int medium = scanner.nextInt();
        int hard = scanner.nextInt();
        int problems = scanner.nextInt();

        if (easy >= 1 && medium >= 1 && hard >= 1 && (easy + medium + hard >= problems) && problems >= 3) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

}
