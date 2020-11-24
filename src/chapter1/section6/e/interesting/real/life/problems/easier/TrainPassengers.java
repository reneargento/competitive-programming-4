package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 31/10/20.
 */
public class TrainPassengers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long capacity = scanner.nextInt();
        int stations = scanner.nextInt();
        long passengers = 0;
        boolean possible = true;

        for (int s = 0; s < stations; s++) {
            int left = scanner.nextInt();
            int entered = scanner.nextInt();
            int waited = scanner.nextInt();

            passengers -= left;
            if (passengers < 0) {
                possible = false;
                break;
            }

            passengers += entered;
            if (passengers > capacity) {
                possible = false;
                break;
            }

            if (waited > 0 && passengers < capacity) {
                possible = false;
                break;
            }

            if (s == stations - 1 && waited > 0) {
                possible = false;
                break;
            }
        }
        if (passengers != 0) {
            possible = false;
        }
        System.out.println(possible ? "possible" : "impossible");
    }

}
