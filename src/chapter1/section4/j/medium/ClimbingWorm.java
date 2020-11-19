package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/09/20.
 */
public class ClimbingWorm {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int climb = scanner.nextInt();
        int fall = scanner.nextInt();
        int height = scanner.nextInt();

        int climbs = 0;
        int location = 0;

        while (true) {
            climbs++;
            location += climb;

            if (location >= height) {
                break;
            }

            location -= fall;
        }

        System.out.println(climbs);
    }

}
