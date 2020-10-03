package chapter1.section4.b.repetition;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Timeloop {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int magicNumber = scanner.nextInt();

        for (int i = 1; i <= magicNumber; i++) {
            System.out.println(i + " Abracadabra");
        }
    }

}
