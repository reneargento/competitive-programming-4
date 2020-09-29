package chapter1.section4.a.io.sequences;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Faktor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int articles = scanner.nextInt();
        int impact = scanner.nextInt();
        System.out.println((impact - 1) * articles + 1);
    }

}
