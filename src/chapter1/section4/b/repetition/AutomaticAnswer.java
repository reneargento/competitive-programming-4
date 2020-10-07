package chapter1.section4.b.repetition;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class AutomaticAnswer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            int number = scanner.nextInt();
            int result = ((((number * 567 / 9 + 7492) * 235 / 47) - 498) % 100) / 10;
            System.out.println(Math.abs(result));
        }
    }

}
