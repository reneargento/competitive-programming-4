package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/09/20.
 */
public class BottledUpFeelings {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int oil = scanner.nextInt();
        int largeBottleVolume = scanner.nextInt();
        int smallBottleVolume = scanner.nextInt();

        boolean filled = false;

        for (int i = oil / largeBottleVolume; i >= 0; i--) {
            int remaining = oil - (i * largeBottleVolume);

            if (remaining % smallBottleVolume == 0) {
                System.out.printf("%d %d\n", i, remaining / smallBottleVolume);
                filled = true;
                break;
            }
        }

        if (!filled) {
            System.out.println("Impossible");
        }
    }

}
