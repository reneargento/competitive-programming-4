package chapter1.section4.b.repetition;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Tarifa {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int megabytes = scanner.nextInt();
        long megabytesAvailable = 0;
        int months = scanner.nextInt();

        for (int i = 0; i < months; i++) {
            megabytesAvailable += (megabytes - scanner.nextInt());
        }
        System.out.println(megabytesAvailable + megabytes);
    }

}
