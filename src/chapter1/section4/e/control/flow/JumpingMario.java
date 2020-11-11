package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class JumpingMario {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 1; i <= tests; i++) {
            int highJumps = 0;
            int lowJumps = 0;

            int walls = scanner.nextInt();
            int previousWall = 0;

            for (int w = 0; w < walls; w++) {
                int currentWall = scanner.nextInt();

                if (w > 0) {
                    if (currentWall > previousWall) {
                        highJumps++;
                    } else if (currentWall < previousWall) {
                        lowJumps++;
                    }
                }
                previousWall = currentWall;
            }
            System.out.printf("Case %d: %d %d\n", i, highJumps, lowJumps);
        }
    }

}
