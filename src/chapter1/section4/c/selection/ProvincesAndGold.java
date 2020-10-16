package chapter1.section4.c.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class ProvincesAndGold {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int gold = scanner.nextInt();
        int silver = scanner.nextInt();
        int copper = scanner.nextInt();

        int buyingPower = gold * 3 + silver * 2 + copper;

        String victory = "";
        String treasure = "";

        if (buyingPower >= 8) {
            victory = "Province";
        } else if (buyingPower >= 5) {
            victory = "Duchy";
        } else if (buyingPower >= 2) {
            victory = "Estate";
        }

        if (buyingPower >= 6) {
            treasure = "Gold";
        } else if (buyingPower >= 3) {
            treasure = "Silver";
        } else {
            treasure = "Copper";
        }

        if (!victory.equals("")) {
            System.out.print(victory + " or ");
        }
        System.out.println(treasure);
    }

}
