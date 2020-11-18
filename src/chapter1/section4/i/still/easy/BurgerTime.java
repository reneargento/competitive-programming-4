package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 18/09/20.
 */
public class BurgerTime {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int highwayLength = scanner.nextInt();

        while (highwayLength != 0) {
            String highway = scanner.next();

            int nearestRestaurant = -1;
            int nearestDrugstore = -1;
            int shortestDistance = Integer.MAX_VALUE;

            for (int i = 0; i < highway.length(); i++) {
                switch (highway.charAt(i)) {
                    case 'R':
                        nearestRestaurant = i;
                        if (nearestDrugstore != -1) {
                            int distance = i - nearestDrugstore;
                            shortestDistance = Math.min(shortestDistance, distance);
                        }
                        break;
                    case 'D':
                        nearestDrugstore = i;
                        if (nearestRestaurant != -1) {
                            int distance = i - nearestRestaurant;
                            shortestDistance = Math.min(shortestDistance, distance);
                        }
                        break;
                    case 'Z':
                        shortestDistance = 0;
                }
            }
            System.out.println(shortestDistance);

            highwayLength = scanner.nextInt();
        }
    }

}
