package chapter1.section4.g.array1d.manipulation;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class Cetiri {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] numbers = { scanner.nextInt(), scanner.nextInt(), scanner.nextInt() };

        Arrays.sort(numbers);

        int difference1 = numbers[1] - numbers[0];
        int difference2 = numbers[2] - numbers[1];

        if (difference1 == difference2) {
            int missing = numbers[2] + difference1;
            System.out.println(missing);
        } else {
            int smallestDifference = Math.min(difference1, difference2);
            int missing;

            if (difference1 == smallestDifference) {
                missing = numbers[1] + smallestDifference;
            } else {
                missing = numbers[0] + smallestDifference;
            }

            System.out.println(missing);
        }
    }

}
