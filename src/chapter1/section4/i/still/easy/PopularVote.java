package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 18/09/20.
 */
public class PopularVote {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int candidates = scanner.nextInt();

            long totalVotes = 0;
            int highestVotes = Integer.MIN_VALUE;
            int winner = -1;
            boolean draw = false;

            for (int c = 0; c < candidates; c++) {
                int votes = scanner.nextInt();
                totalVotes += votes;

                if (votes > highestVotes) {
                    highestVotes = votes;
                    winner = c + 1;
                    draw = false;
                } else if (votes == highestVotes) {
                    draw = true;
                }
            }

            if (draw) {
                System.out.println("no winner");
            } else if (highestVotes > totalVotes / (double) 2) {
                System.out.printf("majority winner %d\n", winner);
            } else {
                System.out.printf("minority winner %d\n", winner);
            }
        }
    }

}
