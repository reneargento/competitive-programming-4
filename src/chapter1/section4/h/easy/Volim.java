package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class Volim {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int player = scanner.nextInt() - 1;
        int questions = scanner.nextInt();

        int totalTime = 0;

        for (int i = 0; i < questions; i++) {
            int time = scanner.nextInt();
            char result = scanner.next().charAt(0);

            if (totalTime + time > 210) {
                break;
            }

            totalTime += time;
            if (result == 'T') {
                player = (player + 1) % 8;
            }
        }
        System.out.println((player + 1));
    }

}
