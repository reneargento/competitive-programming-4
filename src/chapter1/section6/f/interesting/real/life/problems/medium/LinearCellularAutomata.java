package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 06/11/20.
 */
public class LinearCellularAutomata {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] dna = new int[10];
            for (int i = 0; i < dna.length; i++) {
                dna[i] = scanner.nextInt();
            }

            if (t > 0) {
                System.out.println();
            }
            simulateCultureGrowth(dna);
        }
    }

    private static void simulateCultureGrowth(int[] dna) {
        int[] populationDensities = new int[40];
        populationDensities[19] = 1;

        printPopulation(populationDensities);

        for (int i = 0; i < 49; i++) {
            int[] newDensities = new int[40];

            for (int d = 0; d < populationDensities.length; d++) {
                int leftDensity = 0;
                if (d > 0) {
                    leftDensity = populationDensities[d - 1];
                }
                int rightDensity = 0;
                if (d < 39) {
                    rightDensity = populationDensities[d + 1];
                }

                int index = leftDensity + populationDensities[d] + rightDensity;
                newDensities[d] = dna[index];
            }
            printPopulation(newDensities);
            populationDensities = newDensities;
        }
    }

    private static void printPopulation(int[] densities) {
        for (int d = 0; d < densities.length; d++) {
            char densityCharacter;

            switch (densities[d]) {
                case 0: densityCharacter = ' '; break;
                case 1: densityCharacter = '.'; break;
                case 2: densityCharacter = 'x'; break;
                default: densityCharacter = 'W';
            }
            System.out.print(densityCharacter);
        }
        System.out.println();
    }
}
