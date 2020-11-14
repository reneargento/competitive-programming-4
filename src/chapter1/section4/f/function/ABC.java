package chapter1.section4.f.function;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class ABC {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] numbers = { scanner.nextInt(), scanner.nextInt(), scanner.nextInt() };
        Arrays.sort(numbers);

        String order = scanner.next();

        for (int i = 0; i < order.length(); i++) {
            int index = getIndex(order.charAt(i));
            System.out.print(numbers[index]);

            if (i != order.length() - 1) {
                System.out.print(" ");
            } else {
                System.out.println();
            }
        }
    }

    private static int getIndex(char character) {
        switch (character) {
            case 'A': return 0;
            case 'B': return 1;
            default: return 2;
        }
    }

}
