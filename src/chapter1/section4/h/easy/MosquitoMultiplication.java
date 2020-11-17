package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class MosquitoMultiplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int mosquitoes = scanner.nextInt();
            int pupae = scanner.nextInt();
            int larvae = scanner.nextInt();
            int eggsLaid = scanner.nextInt();
            int larvaeSurvivalRate = scanner.nextInt();
            int pupaeSurvivalRate = scanner.nextInt();
            int weeks = scanner.nextInt();

            for (int i = 0; i < weeks; i++) {
                int newMosquitoes = pupae / pupaeSurvivalRate;
                pupae = larvae / larvaeSurvivalRate;
                larvae = mosquitoes * eggsLaid;
                mosquitoes = newMosquitoes;
            }
            System.out.println(mosquitoes);
        }
    }

}
