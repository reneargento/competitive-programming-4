package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/09/20.
 */
public class ClimbingStairs {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int stepsToRegister = scanner.nextInt();
        int stepsFromGroundFloorToRegisterDesk = scanner.nextInt();
        int stepsFromGroundFloorToOffice = scanner.nextInt();

        int steps = 0;

        int distance = Math.abs(stepsFromGroundFloorToRegisterDesk - stepsFromGroundFloorToOffice);

        if (stepsFromGroundFloorToOffice <= stepsFromGroundFloorToRegisterDesk) {
            if (stepsToRegister > stepsFromGroundFloorToRegisterDesk) {
                if (stepsToRegister % 2 != stepsFromGroundFloorToRegisterDesk % 2) {
                    steps++;
                }
            }

            steps += Math.max(stepsToRegister, stepsFromGroundFloorToRegisterDesk) + stepsFromGroundFloorToRegisterDesk;
        } else {
            if (stepsToRegister > stepsFromGroundFloorToOffice + distance) {
                if (stepsToRegister % 2 != stepsFromGroundFloorToRegisterDesk % 2) {
                    steps++;
                }
            }
            steps += Math.max(stepsToRegister, stepsFromGroundFloorToOffice + distance) + stepsFromGroundFloorToRegisterDesk;
        }

        System.out.println(steps);
    }

}
