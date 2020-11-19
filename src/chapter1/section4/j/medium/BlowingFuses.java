package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 24/09/20.
 */
public class BlowingFuses {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int devices = scanner.nextInt();
        int operations = scanner.nextInt();
        int capacity = scanner.nextInt();
        int test = 1;

        while (devices != 0 || operations != 0 || capacity != 0) {
            int[] consumption = new int[devices];

            for (int i = 0; i < consumption.length; i++) {
                consumption[i] = scanner.nextInt();
            }

            boolean fuseBlown = false;
            boolean[] on = new boolean[devices];
            long maxConsumption = 0;
            long currentConsumption = 0;

            for (int i = 0; i < operations; i++) {
                int device = scanner.nextInt() - 1;
                if (on[device]) {
                    currentConsumption -= consumption[device];
                    on[device] = false;
                } else {
                    currentConsumption += consumption[device];
                    on[device] = true;

                    if (currentConsumption > capacity) {
                        fuseBlown = true;
                    }
                    maxConsumption = Math.max(maxConsumption, currentConsumption);
                }
            }

            System.out.printf("Sequence %d\n", test);
            if (fuseBlown) {
                System.out.println("Fuse was blown.");
            } else {
                System.out.println("Fuse was not blown.");
                System.out.printf("Maximal power consumption was %d amperes.\n", maxConsumption);
            }
            System.out.println();

            devices = scanner.nextInt();
            operations = scanner.nextInt();
            capacity = scanner.nextInt();
            test++;
        }
    }

}
