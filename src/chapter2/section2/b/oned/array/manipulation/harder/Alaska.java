package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 29/01/21.
 */
public class Alaska {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int chargingStationsNumber = scanner.nextInt();

        while (chargingStationsNumber != 0) {
            int[] chargingStations = new int[chargingStationsNumber];
            for (int i = 0; i < chargingStations.length; i++) {
                chargingStations[i] = scanner.nextInt();
            }

            Arrays.sort(chargingStations);
            boolean isPossible = isPossible(chargingStations);
            System.out.println(isPossible ? "POSSIBLE" : "IMPOSSIBLE");

            chargingStationsNumber = scanner.nextInt();
        }
    }

    private static boolean isPossible(int[] chargingStations) {
        for (int i = 1; i < chargingStations.length; i++) {
            if (chargingStations[i] - chargingStations[i - 1] > 200) {
                return false;
            }
        }
        return 1422 - chargingStations[chargingStations.length - 1] <= 100;
    }
}
