package chapter1.section4.a.io.sequences;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class R2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int r1 = scanner.nextInt();
        int s = scanner.nextInt();
        int difference = s - r1;
        System.out.println(s + difference);
    }

}
