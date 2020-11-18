package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class LaserSculpture {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int height = scanner.nextInt();
        int length = scanner.nextInt();

        while (height != 0 || length != 0) {
            int timesUsed = 0;
            int previousHeight = 0;
            int currentHeight = height;

            for (int i = 0; i < length; i++) {
                currentHeight = scanner.nextInt();

                if (i > 0 && currentHeight > previousHeight) {
                    timesUsed += currentHeight - previousHeight;
                }
                previousHeight = currentHeight;
            }

            timesUsed += height - currentHeight;

            System.out.println(timesUsed);

            height = scanner.nextInt();
            length = scanner.nextInt();
        }
    }

}
