package chapter2.section2.e.sorting.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 13/02/21.
 */
public class FallingAnts {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int antsNumber = scanner.nextInt();

        while (antsNumber != 0) {
            int maxVolume = -1;
            int maxHeight = -1;

            for (int a = 0; a < antsNumber; a++) {
                int length = scanner.nextInt();
                int width = scanner.nextInt();
                int height = scanner.nextInt();
                int volume = length * width * height;

                if (height > maxHeight) {
                    maxHeight = height;
                    maxVolume = volume;
                } else if (height == maxHeight) {
                    maxVolume = Math.max(maxVolume, volume);
                }
            }
            System.out.println(maxVolume);

            antsNumber = scanner.nextInt();
        }
    }
}
