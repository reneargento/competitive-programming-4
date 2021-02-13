package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/01/21.
 */
public class AutomatedCheckingMachine {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int[] connector1 = new int[5];
            for (int i = 0; i < connector1.length; i++) {
                connector1[i] = scanner.nextInt();
            }

            int[] connector2 = new int[5];
            for (int i = 0; i < connector2.length; i++) {
                connector2[i] = scanner.nextInt();
            }
            System.out.println(isCompatible(connector1, connector2) ? "Y" : "N");
        }
    }

    private static boolean isCompatible(int[] connector1, int[] connector2) {
        for (int i = 0; i < connector1.length; i++) {
            if (connector1[i] == connector2[i]) {
                return false;
            }
        }
        return true;
    }
}
