package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 23/12/20.
 */
public class Gerrymandering {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int precincts = scanner.nextInt();
        int districts = scanner.nextInt();
        int[][] districtVotes = new int[districts][2];

        for (int p = 0; p < precincts; p++) {
            int districtId = scanner.nextInt() - 1;
            int votesA = scanner.nextInt();
            int votesB = scanner.nextInt();
            districtVotes[districtId][0] += votesA;
            districtVotes[districtId][1] += votesB;
        }
        int totalWastedVotesA = 0;
        int totalWastedVotesB = 0;
        int totalVoters = 0;

        for (int d = 0; d < districtVotes.length; d++) {
            int votesA = districtVotes[d][0];
            int votesB = districtVotes[d][1];
            totalVoters += votesA + votesB;

            int majorityVotes = (votesA + votesB) / 2 + 1;
            int wastedVotesA;
            int wastedVotesB;
            String winner;
            if (votesA > votesB) {
                wastedVotesA = votesA - majorityVotes;
                wastedVotesB = votesB;
                winner = "A";
            } else {
                wastedVotesA = votesA;
                wastedVotesB = votesB - majorityVotes;
                winner = "B";
            }
            totalWastedVotesA += wastedVotesA;
            totalWastedVotesB += wastedVotesB;
            System.out.printf("%s %d %d\n", winner, wastedVotesA, wastedVotesB);
        }
        double efficiencyGap = Math.abs(totalWastedVotesA - totalWastedVotesB) / (double) totalVoters;
        System.out.printf("%.10f\n", efficiencyGap);
    }
}
