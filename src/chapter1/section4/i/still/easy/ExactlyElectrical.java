package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/09/20.
 */
public class ExactlyElectrical {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int startX = scanner.nextInt();
        int startY = scanner.nextInt();
        int endX = scanner.nextInt();
        int endY = scanner.nextInt();
        int batteryCharge = scanner.nextInt();

        int distance = Math.abs(startX - endX) + Math.abs(startY - endY);

        if (distance <= batteryCharge && distance % 2 == batteryCharge % 2) {
            System.out.println("Y");
        } else {
            System.out.println("N");
        }
    }

}
