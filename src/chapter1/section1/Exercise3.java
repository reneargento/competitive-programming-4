package chapter1.section1;

import java.util.Random;

/**
 * Created by Rene Argento on 09/08/20.
 */
public class Exercise3 {

    private static class Coordinate {
        int x;
        int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double getMinimumCost(Coordinate[] coordinates) {
        boolean[] matched = new boolean[coordinates.length];
        return getMinimumCost(coordinates, 0, matched, Double.MAX_VALUE, 0);
    }

    private static double getMinimumCost(Coordinate[] coordinates, int index, boolean[] matched, double currentMinCost,
                                         double currentCost) {
        // Pruning 1
        if (currentCost >= currentMinCost) {
            return currentCost;
        }

        matched[index] = true;

        for (int i = index + 1; i < coordinates.length; i++) {
            if (!matched[i]) {
                matched[i] = true;

                double distance = distanceBetweenPoints(coordinates[index], coordinates[i]);
                currentCost += distance;

                int nextUnmatchedIndex = getNextUnmatchedIndex(matched);
                if (nextUnmatchedIndex != -1) {
                    double newCost = getMinimumCost(coordinates, nextUnmatchedIndex, matched, currentMinCost, currentCost);
                    currentMinCost = Math.min(newCost, currentMinCost);
                } else {
                    currentMinCost = Math.min(currentCost, currentMinCost);
                    matched[i] = false;
                    break;
                }

                matched[i] = false;
                currentCost -= distance;
            }
        }
        // Pruning 2 - no need to search on the left for the next pair to match
        matched[index] = false;

        return currentMinCost;
    }

    private static int getNextUnmatchedIndex(boolean[] matched) {
        for (int i = 0; i < matched.length; i++) {
            if (!matched[i]) {
                return i;
            }
        }
        return -1;
    }

    private static double distanceBetweenPoints(Coordinate coordinate1, Coordinate coordinate2) {
        return Math.sqrt(Math.pow(coordinate1.x - coordinate2.x, 2) + Math.pow(coordinate1.y - coordinate2.y, 2));
    }

    public static void main(String[] args) {
        runSampleTest();

        Coordinate[] coordinates = new Coordinate[16];
        // Random random = new Random();

        for (int i = 0; i < 16; i++) {
            int x = i * 10; // random.nextInt(1001);
            int y = i * 20; // random.nextInt(1001);
            coordinates[i] = new Coordinate(x, y);
        }

        System.out.println("\nMinimum cost: " + roundValuePrecisionDigits(getMinimumCost(coordinates), 2));
        System.out.println("Expected: 178.89");
    }

    private static void runSampleTest() {
        Coordinate[] coordinates = new Coordinate[4];
        coordinates[0] = new Coordinate(1, 1);
        coordinates[1] = new Coordinate(8, 6);
        coordinates[2] = new Coordinate(6, 8);
        coordinates[3] = new Coordinate(1, 3);

        System.out.println("Minimum cost: " + roundValuePrecisionDigits(getMinimumCost(coordinates), 2));
        System.out.println("Expected: 4.83");
    }

    private static double roundValuePrecisionDigits(double value, int digits) {
        long valueToMultiply = (long) Math.pow(10, digits);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
    }

}
