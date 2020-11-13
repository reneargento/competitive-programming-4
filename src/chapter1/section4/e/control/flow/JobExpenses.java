package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class JobExpenses {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numbers = scanner.nextInt();
        long total = 0;

        for (int i = 0; i < numbers; i++) {
            int value = scanner.nextInt();
            total += value < 0 ? value : 0;
        }
        System.out.println(Math.abs(total));
    }

}
