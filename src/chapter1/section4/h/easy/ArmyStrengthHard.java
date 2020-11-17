package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class ArmyStrengthHard {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int godzillaArmySize = scanner.nextInt();
            int mechaGodzillaArmySize = scanner.nextInt();

            int godzillaArmyMaxStrength = Integer.MIN_VALUE;
            int mechaGodzillaArmyMaxStrength = Integer.MIN_VALUE;

            for (int i = 0; i < godzillaArmySize; i++) {
                godzillaArmyMaxStrength = Math.max(godzillaArmyMaxStrength, scanner.nextInt());
            }

            for (int i = 0; i < mechaGodzillaArmySize; i++) {
                mechaGodzillaArmyMaxStrength = Math.max(mechaGodzillaArmyMaxStrength, scanner.nextInt());
            }

            if (mechaGodzillaArmyMaxStrength > godzillaArmyMaxStrength) {
                System.out.println("MechaGodzilla");
            } else {
                System.out.println("Godzilla");
            }
        }
    }

}
