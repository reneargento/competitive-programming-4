package chapter2.section2.f.sorting.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 20/02/21.
 */
public class ContestScoreboard {

    private static class Team implements Comparable<Team> {
        int id;
        Set<Integer> problemsSolved;
        int penaltyTime;
        int submissions;
        int[] accumulatedPenaltyTime;

        public Team(int id) {
            this.id = id;
            problemsSolved = new HashSet<>();
            accumulatedPenaltyTime = new int[10];
        }

        @Override
        public int compareTo(Team other) {
            if (problemsSolved.size() != other.problemsSolved.size()) {
                return other.problemsSolved.size() - problemsSolved.size();
            }
            if (penaltyTime != other.penaltyTime) {
                return penaltyTime - other.penaltyTime;
            }
            return id - other.id;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
            }

            Team[] teams = initializeTeams();

            String line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(" ");
                int teamId = Integer.parseInt(data[0]);
                int problemId = Integer.parseInt(data[1]);
                int time = Integer.parseInt(data[2]);
                String result = data[3];

                Team team = teams[teamId];
                Set<Integer> problemsSolved = team.problemsSolved;
                team.submissions++;

                if (result.equals("C")) {
                    if (!problemsSolved.contains(problemId)) {
                        problemsSolved.add(problemId);
                        team.penaltyTime += time;
                        team.penaltyTime += team.accumulatedPenaltyTime[problemId];
                    }
                } else if (result.equals("I")) {
                    if (!problemsSolved.contains(problemId)) {
                        team.accumulatedPenaltyTime[problemId] += 20;
                    }
                }
                line = FastReader.getLine();
            }

            Arrays.sort(teams);
            printScoreboard(teams);
        }
    }

    private static Team[] initializeTeams() {
        Team[] teams = new Team[101];
        for (int i = 0; i < teams.length; i++) {
            teams[i] = new Team(i);
        }
        return teams;
    }

    private static void printScoreboard(Team[] teams) {
        for (Team team : teams) {
            if (team.submissions == 0) {
                continue;
            }
            System.out.printf("%d %d %d\n", team.id, team.problemsSolved.size(), team.penaltyTime);
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
