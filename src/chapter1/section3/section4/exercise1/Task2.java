package chapter1.section3.section4.exercise1;

import java.util.Scanner;

/**
 * Created by Rene Argento on 28/08/20.
 */
public class Task2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        long valueToMultiply = (long) Math.pow(10, n);
        double roundedPi = (double) Math.round(Math.PI * valueToMultiply) / valueToMultiply;
        System.out.println(roundedPi);
    }

}
