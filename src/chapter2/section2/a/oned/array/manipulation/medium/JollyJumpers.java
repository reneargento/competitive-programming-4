package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 15/01/21.
 */
public class JollyJumpers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String[] values = scanner.nextLine().split(" ");
            Set<Integer> differences = new HashSet<>();
            int previousValue = -1;

            for (int i = 1; i < values.length; i++) {
                int value = Integer.parseInt(values[i]);

                if (i > 1) {
                    int difference = Math.abs(value - previousValue);
                    differences.add(difference);
                }
                previousValue = value;
            }

            boolean isJollyJumper = true;
            for (int i = 1; i < values.length - 1; i++) {
                if (!differences.contains(i)) {
                    isJollyJumper = false;
                    break;
                }
            }

            System.out.println(isJollyJumper ? "Jolly" : "Not jolly");
        }
    }
}
