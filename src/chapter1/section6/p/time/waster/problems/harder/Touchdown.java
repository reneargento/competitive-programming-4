package chapter1.section6.p.time.waster.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 29/12/20.
 */
public class Touchdown {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int plays = scanner.nextInt();
        int position = 20;
        int advanced = 0;
        int playsWithoutAdvancing10Yards = 0;
        boolean finished = false;

        for (int p = 0; p < plays; p++) {
            int yards = scanner.nextInt();

            position += yards;
            if (position <= 0 || position >= 100) {
                if (position >= 100) {
                    System.out.println("Touchdown");
                } else {
                    System.out.println("Safety");
                }
                finished = true;
                break;
            }

            advanced += yards;
            if (advanced >= 10) {
                advanced = 0;
                playsWithoutAdvancing10Yards = 0;
            } else {
                playsWithoutAdvancing10Yards++;
                if (playsWithoutAdvancing10Yards == 4) {
                    System.out.println("Nothing");
                    finished = true;
                    break;
                }
            }
        }
        if (!finished) {
            System.out.println("Nothing");
        }
    }
}
