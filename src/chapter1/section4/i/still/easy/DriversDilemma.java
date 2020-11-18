package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/09/20.
 */
public class DriversDilemma {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double gallons = scanner.nextDouble() / 2.0;
        double fuelLeakRate = scanner.nextDouble();
        double distance = scanner.nextDouble();

        int targetSpeed = -1;

        for (int i = 0; i < 6; i++) {
            int speed = scanner.nextInt();
            double fuelEfficiency = scanner.nextDouble();

            double lostFuel = (distance / speed) * fuelLeakRate;
            double actualGallons = gallons - lostFuel;

            if (actualGallons * fuelEfficiency >= distance) {
                targetSpeed = speed;
            }
        }

        if (targetSpeed != -1) {
            System.out.println("Yes " + targetSpeed);
        } else {
            System.out.println("NO");
        }
    }

}
