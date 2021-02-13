package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 15/01/21.
 */
public class BoxOfBricks {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int stacks = scanner.nextInt();
        int set = 1;

        while (stacks != 0) {
            int[] heights = new int[stacks];
            int sum = 0;

            for (int i = 0; i < stacks; i++) {
                heights[i] = scanner.nextInt();
                sum += heights[i];
            }

            int moves = 0;
            int targetHeight = sum / stacks;

            for (int height : heights) {
                if (height > targetHeight) {
                    moves += height - targetHeight;
                }
            }

            System.out.printf("Set #%d\n", set);
            System.out.printf("The minimum number of moves is %d.\n\n", moves);

            stacks = scanner.nextInt();
            set++;
        }
    }
}
