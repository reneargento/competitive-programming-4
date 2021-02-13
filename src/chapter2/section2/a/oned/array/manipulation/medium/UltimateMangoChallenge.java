package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/01/21.
 */
public class UltimateMangoChallenge {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int typesOfMangoes = scanner.nextInt();
            int limit = scanner.nextInt();
            int eaten = 0;

            int[] mangoes = new int[typesOfMangoes];
            for (int i = 0; i < mangoes.length; i++) {
                mangoes[i] = scanner.nextInt();
                eaten += mangoes[i];
            }

            boolean wonChallenge = eaten <= limit;

            for (int i = 0; i < mangoes.length; i++) {
                int limitOfCurrentType = scanner.nextInt();
                if (mangoes[i] > limitOfCurrentType) {
                    wonChallenge = false;
                }
            }

            System.out.printf("Case %d: ", t);
            System.out.println(wonChallenge ? "Yes" : "No");
        }
    }
}
