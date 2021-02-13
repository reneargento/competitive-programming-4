package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/01/21.
 */
public class PermutationArrays {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
                scanner.nextLine();
            }

            String[] permutationArrayValues = scanner.nextLine().split(" ");
            String[] values = scanner.nextLine().split(" ");

            String[] permutedValues = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                int position = Integer.parseInt(permutationArrayValues[i]) - 1;
                permutedValues[position] = values[i];
            }

            for (String value : permutedValues) {
                System.out.println(value);
            }
        }
    }
}
