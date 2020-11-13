package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class EcologicalPremium {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            int farmers = scanner.nextInt();
            long premium = 0;

            for (int f = 0; f < farmers; f++) {
                int size = scanner.nextInt();
                int numberOfAnimals = scanner.nextInt();
                int environmentFriendliness = scanner.nextInt();
                premium += size * environmentFriendliness;
            }
            System.out.println(premium);
        }
    }

}
