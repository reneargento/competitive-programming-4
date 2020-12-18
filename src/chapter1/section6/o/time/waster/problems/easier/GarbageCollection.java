package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/12/20.
 */
public class GarbageCollection {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int weightLimit = scanner.nextInt();
            int pickUpPoints = scanner.nextInt();
            long totalDistance = 0;
            long currentWeight = 0;

            for (int i = 0; i < pickUpPoints; i++) {
                int distance = scanner.nextInt();
                int weight = scanner.nextInt();

                if (currentWeight + weight >= weightLimit || i == pickUpPoints - 1) {
                    totalDistance += distance * 2;

                    if (currentWeight + weight > weightLimit) {
                        currentWeight = weight;
                        if (i == pickUpPoints - 1) {
                            totalDistance += distance * 2;
                        }
                    } else {
                        currentWeight = 0;
                    }
                } else {
                    currentWeight += weight;
                }
            }
            System.out.println(totalDistance);
        }
    }
}
