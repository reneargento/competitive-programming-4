package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/09/20.
 */
public class MissingNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numbers = scanner.nextInt();
        int current = 1;
        boolean missing = false;

        for (int i = 0; i < numbers; i++) {
            int number = scanner.nextInt();

            while (current != number) {
                missing = true;
                System.out.println(current);
                current++;
            }
            current++;
        }

        if (!missing) {
            System.out.println("good job");
        }
    }

}
