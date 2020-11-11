package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class FizzBuzz {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int number = scanner.nextInt();

        for (int i = 1; i <= number; i++) {
            if (i % x == 0 && i % y == 0) {
                System.out.println("FizzBuzz");
            } else if (i % x == 0) {
                System.out.println("Fizz");
            } else if (i % y == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }

}
