package chapter2.section2.c.twod.array.manipulation.easier;

import java.util.*;

/**
 * Created by Rene Argento on 03/02/21.
 */
public class LastBlood {

    private static class Submission {
        int time;
        int teamId;

        public Submission(int time, int teamId) {
            this.time = time;
            this.teamId = teamId;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int problems = scanner.nextInt();
        int teams = scanner.nextInt();
        int submissions = scanner.nextInt();

        Submission[] lastBlood = new Submission[problems];
        boolean[][] solvedProblemsByTeams = new boolean[teams + 1][problems + 1];

        for (int i = 0; i < submissions; i++) {
            int time = scanner.nextInt();
            int teamId = scanner.nextInt();
            char problem = scanner.next().charAt(0);
            int problemId = problem - 65;
            String verdict = scanner.next();

            if (verdict.equals("Yes") && !solvedProblemsByTeams[teamId][problemId]) {
                solvedProblemsByTeams[teamId][problemId] = true;
                lastBlood[problemId] = new Submission(time, teamId);
            }
        }

        for (int i = 0; i < lastBlood.length; i++) {
            char problem = (char) ('A' + i);
            System.out.print(problem + " ");
            if (lastBlood[i] != null) {
                System.out.printf("%d %d\n", lastBlood[i].time, lastBlood[i].teamId);
            } else {
                System.out.println("- -");
            }
        }
    }
}
