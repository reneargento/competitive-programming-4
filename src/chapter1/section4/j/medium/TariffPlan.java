package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/09/20.
 */
public class TariffPlan {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            long mileTotal = 0;
            long juiceTotal = 0;

            int calls = scanner.nextInt();

            for (int c = 0; c < calls; c++) {
                int minutes = scanner.nextInt();
                mileTotal += (int) Math.ceil(minutes / 30.0) * 10;
                juiceTotal += (int) Math.ceil(minutes / 60.0) * 15;

                if (minutes % 30 == 0) {
                    mileTotal += 10;
                }
                if (minutes % 60 == 0) {
                    juiceTotal += 15;
                }
            }

            if (mileTotal < juiceTotal) {
                System.out.printf("Case %d: Mile %d\n", t, mileTotal);
            } else if (juiceTotal < mileTotal) {
                System.out.printf("Case %d: Juice %d\n", t, juiceTotal);
            } else {
                System.out.printf("Case %d: Mile Juice %d\n", t, mileTotal);
            }
        }
    }
}
