package chapter1.section4.f.function;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class CombinationLock {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int initialPosition = scanner.nextInt();
        int number1 = scanner.nextInt();
        int number2 = scanner.nextInt();
        int number3 = scanner.nextInt();

        while (initialPosition != 0 || number1 != 0 || number2 != 0 || number3 != 0) {
            int totalPositions = 0;
            // Number 1
            totalPositions += 80 + getPositionMoved(number1, initialPosition);

            // Number 2
            totalPositions += 40 + getPositionMoved(number1, number2);

            // Number 3
            totalPositions += getPositionMoved(number3, number2);

            System.out.println(totalPositions * 9);

            initialPosition = scanner.nextInt();
            number1 = scanner.nextInt();
            number2 = scanner.nextInt();
            number3 = scanner.nextInt();
        }
    }

    private static int getPositionMoved(int start, int end) {
        if (end >= start) {
            return end - start;
        } else {
            return (40 - start) + end;
        }
    }

}
