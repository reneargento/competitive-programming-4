package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 31/10/20.
 */
public class JumbledCompass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int direction = scanner.nextInt();
        int targetDirection = scanner.nextInt();
        int distance;
        int clockwiseDistance = 0;
        int counterClockwiseDistance = 0;

        if (targetDirection > direction) {
            clockwiseDistance = targetDirection - direction;
            counterClockwiseDistance = direction + (360 - targetDirection);
        } else if (direction > targetDirection) {
            clockwiseDistance = (360 - direction) + targetDirection;
            counterClockwiseDistance = direction - targetDirection;
        }

        if (clockwiseDistance <= counterClockwiseDistance) {
            distance = clockwiseDistance;
        } else {
            distance = -counterClockwiseDistance;
        }
        System.out.println(distance);
    }

}
