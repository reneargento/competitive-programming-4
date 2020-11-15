package chapter1.section4.g.array1d.manipulation;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class SubPrime {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int banks = scanner.nextInt();
        int debentures = scanner.nextInt();

        while (banks != 0 || debentures != 0) {
            long[] reserves = new long[banks];

            for (int i = 0; i < banks; i++) {
                reserves[i] = scanner.nextInt();
            }

            for (int i = 0; i < debentures; i++) {
                int debtor = scanner.nextInt();
                int creditor = scanner.nextInt();
                int value = scanner.nextInt();

                reserves[debtor - 1] -= value;
                reserves[creditor - 1] += value;
            }

            boolean possible = true;
            for (long reserve : reserves) {
                if (reserve < 0) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                System.out.println("S");
            } else {
                System.out.println("N");
            }

            banks = scanner.nextInt();
            debentures = scanner.nextInt();
        }
    }

}
