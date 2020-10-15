package chapter1.section4.c.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class JudgingMoose {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int leftTines = scanner.nextInt();
        int rightTines = scanner.nextInt();

        if (leftTines == 0 && rightTines == 0) {
            System.out.println("Not a moose");
        } else if (leftTines == rightTines) {
            System.out.println("Even " + leftTines * 2);
        } else {
            int higherNumber = Math.max(leftTines, rightTines);
            System.out.println("Odd " + higherNumber * 2);
        }
    }

}
