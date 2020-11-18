package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/09/20.
 */
public class RequestForProposal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int requirements = scanner.nextInt();
        int proposals = scanner.nextInt();
        int rfp = 1;

        while (requirements != 0 || proposals != 0) {
            String bestProposal = null;
            int mostRequirementsMet = Integer.MIN_VALUE;
            double bestCost = Double.MAX_VALUE;

            for (int i = 0; i <= requirements; i++) {
                scanner.nextLine();
            }

            for (int i = 0; i < proposals; i++) {
                String proposal = scanner.nextLine();
                double cost = scanner.nextDouble();
                int requirementsMet = scanner.nextInt();

                for (int r = 0; r <= requirementsMet; r++) {
                    scanner.nextLine();
                }

                if (requirementsMet > mostRequirementsMet
                        || (requirementsMet == mostRequirementsMet && cost < bestCost)) {
                    mostRequirementsMet = requirementsMet;
                    bestCost = cost;
                    bestProposal = proposal;
                }
            }

            if (rfp > 1) {
                System.out.println();
            }

            System.out.printf("RFP #%d\n", rfp);
            System.out.println(bestProposal);

            requirements = scanner.nextInt();
            proposals = scanner.nextInt();
            rfp++;
        }

    }

}
