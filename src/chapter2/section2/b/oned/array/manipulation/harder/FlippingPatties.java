package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 30/01/21.
 */
public class FlippingPatties {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int orders = scanner.nextInt();
        int[] actions = new int[43201];

        for (int i = 0; i < orders; i++) {
            int cookTime = scanner.nextInt();
            int serveTime = scanner.nextInt();

            int placeTime = serveTime - (cookTime * 2);
            int flipTime = serveTime - cookTime;
            actions[placeTime]++;
            actions[flipTime]++;
            actions[serveTime]++;
        }

        int cooksToHire = 0;
        for (int action : actions) {
            int cooksNeeded = (int) Math.ceil(action / 2.0);
            cooksToHire = Math.max(cooksToHire, cooksNeeded);
        }
        System.out.println(cooksToHire);
    }
}
