package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 15/01/21.
 */
public class PolePosition {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cars = scanner.nextInt();

        while (cars != 0) {
            int[] startPositions = new int[cars];
            boolean possible = true;

            for (int i = 0; i < cars; i++) {
                int carNumber = scanner.nextInt();
                int delta = scanner.nextInt();

                int startPosition = i + delta;

                if (startPosition < 0 || startPosition >= startPositions.length
                        || startPositions[startPosition] != 0) {
                    possible = false;
                } else {
                    startPositions[startPosition] = carNumber;
                }
            }

            if (possible) {
                StringJoiner startPositionsDescription = new StringJoiner(" ");
                for (int position : startPositions) {
                    startPositionsDescription.add(String.valueOf(position));
                }
                System.out.println(startPositionsDescription);
            } else {
                System.out.println("-1");
            }
            cars = scanner.nextInt();
        }
    }
}
