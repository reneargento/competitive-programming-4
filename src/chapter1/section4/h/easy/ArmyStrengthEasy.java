package chapter1.section4.h.easy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class ArmyStrengthEasy {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int godzillaArmySize = scanner.nextInt();
            int mechaGodzillaArmySize = scanner.nextInt();

            int[] godzillaArmy = new int[godzillaArmySize];
            int[] mechaGodzillaArmy = new int[mechaGodzillaArmySize];

            for (int i = 0; i < godzillaArmySize; i++) {
                godzillaArmy[i] = scanner.nextInt();
            }

            for (int i = 0; i < mechaGodzillaArmySize; i++) {
                mechaGodzillaArmy[i] = scanner.nextInt();
            }

            Arrays.sort(godzillaArmy);
            Arrays.sort(mechaGodzillaArmy);

            int godzillaIndex = 0;
            int mechaGodzillaIndex = 0;

            while (godzillaIndex < godzillaArmy.length && mechaGodzillaIndex < mechaGodzillaArmy.length) {
                if (godzillaArmy[godzillaIndex] < mechaGodzillaArmy[mechaGodzillaIndex]) {
                    godzillaIndex++;
                } else {
                    mechaGodzillaIndex++;
                }
            }

            if (godzillaIndex == godzillaArmy.length) {
                System.out.println("MechaGodzilla");
            } else {
                System.out.println("Godzilla");
            }
        }
    }

}
