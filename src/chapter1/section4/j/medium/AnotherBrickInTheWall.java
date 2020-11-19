package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/09/20.
 */
public class AnotherBrickInTheWall {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int height = scanner.nextInt();
        int width = scanner.nextInt();
        int bricks = scanner.nextInt();

        boolean possible = false;
        int totalSize = height * width;
        int currentWidth = 0;

        for (int i = 0; i < bricks; i++) {
            int brickWidth = scanner.nextInt();

            currentWidth += brickWidth;

            if (currentWidth > width) {
                break;
            } else if (currentWidth == width) {
                totalSize -= currentWidth;
                currentWidth = 0;

                if (totalSize == 0) {
                    possible = true;
                    break;
                }
            }
        }

        System.out.println(possible ? "YES" : "NO");
    }

}
