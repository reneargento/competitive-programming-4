package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 03/11/20.
 */
public class Parking {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int oneTruckPrice = scanner.nextInt();
        int twoTrucksPrice = scanner.nextInt();
        int threeTrucksPrice = scanner.nextInt();

        int[] time = new int[101];

        for (int i = 0; i < 3; i++) {
            int startTime = scanner.nextInt();
            int endTime = scanner.nextInt();

            for (int t = startTime; t < endTime; t++) {
                time[t]++;
            }
        }

        int cost = 0;

        for (int t = 0; t < time.length; t++) {
            if (time[t] == 1) {
                cost += oneTruckPrice;
            } else if (time[t] == 2) {
                cost += twoTrucksPrice * 2;
            } else if (time[t] == 3) {
                cost += threeTrucksPrice * 3;
            }
        }
        System.out.println(cost);
    }
}
