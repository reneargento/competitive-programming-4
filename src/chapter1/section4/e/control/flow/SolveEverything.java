package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class SolveEverything {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int problemSets = scanner.nextInt();

        for (int i = 1; i <= problemSets; i++) {
            boolean consider = true;

            for (int p = 0; p < 13; p++) {
                int problem = scanner.nextInt();
                if (problem == 0) {
                    consider = false;
                }
            }
            System.out.printf("Set #%d: ", i);
            System.out.println(consider ? "Yes" : "No");
        }
    }

}
