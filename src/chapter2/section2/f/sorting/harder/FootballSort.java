package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/02/21.
 */
public class FootballSort {

    private static class Team implements Comparable<Team> {
        String name;
        int points;
        int gamesPlayed;
        int scoredGoals;
        int sufferedGoals;

        public Team(String name) {
            this.name = name;
        }

        int goalDifference() {
            return scoredGoals - sufferedGoals;
        }

        String percentageOfEarnedPoints() {
            String percentage;

            if (gamesPlayed == 0) {
                percentage = "N/A";
            } else {
                double totalPossiblePoints = gamesPlayed * 3;
                double percentageValue = points / totalPossiblePoints * 100;
                percentage = String.format("%5.2f", percentageValue);
            }
            return percentage;
        }

        boolean hasDifferentClassification(Team other) {
            return points != other.points
                    || goalDifference() != other.goalDifference()
                    || scoredGoals != other.scoredGoals;
        }

        @Override
        public int compareTo(Team other) {
            if (points != other.points) {
                return other.points - points;
            }
            if (goalDifference() != other.goalDifference()) {
                if (goalDifference() > other.goalDifference()) {
                    return -1;
                } else {
                    return 1;
                }
            }
            if (scoredGoals != other.scoredGoals) {
                return other.scoredGoals - scoredGoals;
            }
            return name.compareToIgnoreCase(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int teamsNumber = FastReader.nextInt();
        int games = FastReader.nextInt();
        int championship = 1;

        while (teamsNumber != 0 || games != 0) {
            if (championship > 1) {
                outputWriter.printLine();
            }

            Team[] teams = new Team[teamsNumber];
            Map<String, Integer> nameToIdMap = new HashMap<>();

            for (int i = 0; i < teams.length; i++) {
                String teamName = FastReader.getLine();
                teams[i] = new Team(teamName);
                nameToIdMap.put(teamName, i);
            }

            for (int i = 0; i < games; i++) {
                String game = FastReader.getLine();
                processGame(teams, game, nameToIdMap);
            }

            Arrays.sort(teams);
            printClassification(teams, outputWriter);

            teamsNumber = FastReader.nextInt();
            games = FastReader.nextInt();
            championship++;
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void processGame(Team[] teams, String game, Map<String, Integer> nameToIdMap) {
        String[] gameData = game.split(" ");
        String team1Name = gameData[0];
        int team1Score = Integer.parseInt(gameData[1]);
        String team2Name = gameData[4];
        int team2Score = Integer.parseInt(gameData[3]);

        int team1Id = nameToIdMap.get(team1Name);
        int team2Id = nameToIdMap.get(team2Name);

        Team team1 = teams[team1Id];
        Team team2 = teams[team2Id];

        team1.gamesPlayed++;
        team2.gamesPlayed++;

        team1.scoredGoals += team1Score;
        team1.sufferedGoals += team2Score;

        team2.scoredGoals += team2Score;
        team2.sufferedGoals += team1Score;

        if (team1Score > team2Score) {
            team1.points += 3;
        } else if (team2Score > team1Score) {
            team2.points += 3;
        } else {
            team1.points++;
            team2.points++;
        }
    }

    private static void printClassification(Team[] teams, OutputWriter outputWriter) {
        int rank = 1;

        for (int i = 0; i < teams.length; i++) {
            boolean printRank = i == 0;

            Team team = teams[i];
            if (i > 0 && team.hasDifferentClassification(teams[i - 1])) {
                rank = i + 1;
                printRank = true;
            }

            String rankString = "";
            if (printRank) {
                rankString = rank + ".";
            }

            String classification = String.format("%3s %15s %3d %3d %3d %3d %3d %6s",
                    rankString, team.name, team.points, team.gamesPlayed, team.scoredGoals, team.sufferedGoals,
                    team.goalDifference(), team.percentageOfEarnedPoints());
            outputWriter.printLine(classification);
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

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
