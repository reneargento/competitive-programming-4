package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 01/11/20.
 */
public class BanningBalconing {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int poolLength = scanner.nextInt();
            int horizontalDistance = scanner.nextInt();
            int height = scanner.nextInt();
            int horizontalSpeed = scanner.nextInt();

            int leftEdgeStart = horizontalDistance - 500;
            int leftEdgeEnd = horizontalDistance + 500;

            int rightEdgeStart = (horizontalDistance + poolLength) - 500;
            int rightEdgeEnd = (horizontalDistance + poolLength) + 500;

            // H = 1/2 * g * t * t
            // t = sqrt(H / (1/2 * g))
            double time = Math.sqrt(height / (0.5 * 9810));
            // d = V * t
            double distance = horizontalSpeed * time;

            if ((leftEdgeStart <= distance && distance <= leftEdgeEnd)
                    || (rightEdgeStart <= distance && distance <= rightEdgeEnd)) {
                System.out.println("EDGE");
            } else if (leftEdgeEnd < distance && distance < rightEdgeStart) {
                System.out.println("POOL");
            } else {
                System.out.println("FLOOR");
            }
        }
    }
}
