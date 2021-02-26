package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/02/21.
 */
public class FootballAkaSoccer {

    private static class Team implements Comparable<Team> {
        String name;
        int points;
        int wins;
        int ties;
        int losses;
        int goalsScored;
        int goalsAgainst;

        public Team(String name) {
            this.name = name;
        }

        public int goalsDifference() {
            return goalsScored - goalsAgainst;
        }

        public int gamesPlayed() {
            return wins + ties + losses;
        }

        @Override
        public int compareTo(Team other) {
            if (points != other.points) {
                return other.points - points;
            }
            if (wins != other.wins) {
                return other.wins - wins;
            }
            if (goalsDifference() != other.goalsDifference()) {
                if (goalsDifference() > other.goalsDifference()) {
                    return -1;
                } else if (goalsDifference() < other.goalsDifference()) {
                    return 1;
                }
                return 0;
            }
            if (goalsScored != other.goalsScored) {
                return other.goalsScored - goalsScored;
            }
            if (gamesPlayed() != other.gamesPlayed()) {
                return gamesPlayed() - other.gamesPlayed();
            }
            return name.compareToIgnoreCase(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tournaments = FastReader.nextInt();

        for (int t = 0; t < tournaments; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            String tournamentName = FastReader.getLine();
            int teamNumber = FastReader.nextInt();

            Team[] teams = new Team[teamNumber];
            Map<String, Integer> teamNameToIdMap = new HashMap<>();

            for (int i = 0; i < teams.length; i++) {
                String teamName = FastReader.getLine();
                teams[i] = new Team(teamName);
                teamNameToIdMap.put(teamName, i);
            }

            int games = FastReader.nextInt();
            for (int i = 0; i < games; i++) {
                String game = FastReader.getLine();
                processGame(teams, teamNameToIdMap, game);
            }

            Arrays.sort(teams);
            printStandings(tournamentName, teams, outputWriter);
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void processGame(Team[] teams, Map<String, Integer> teamNameToIdMap, String game) {
        int team1NameEnd = game.indexOf("#");
        int team2NameStart = game.indexOf("#", team1NameEnd + 1);

        String team1Name = game.substring(0, team1NameEnd);
        String team2Name = game.substring(team2NameStart + 1);

        String[] goals = game.substring(team1NameEnd + 1, team2NameStart).split("@");
        int team1Goals = Integer.parseInt(goals[0]);
        int team2Goals = Integer.parseInt(goals[1]);

        int team1Index = teamNameToIdMap.get(team1Name);
        int team2Index = teamNameToIdMap.get(team2Name);

        Team team1 = teams[team1Index];
        Team team2 = teams[team2Index];

        team1.goalsScored += team1Goals;
        team2.goalsScored += team2Goals;

        team1.goalsAgainst += team2Goals;
        team2.goalsAgainst += team1Goals;

        if (team1Goals > team2Goals) {
            team1.points += 3;
            team1.wins++;
            team2.losses++;
        } else if (team2Goals > team1Goals) {
            team2.points += 3;
            team2.wins++;
            team1.losses++;
        } else {
            team1.points++;
            team2.points++;
            team1.ties++;
            team2.ties++;
        }
    }

    private static void printStandings(String tournamentName, Team[] teams, OutputWriter outputWriter) {
        outputWriter.printLine(tournamentName);

        for (int i = 0; i < teams.length; i++) {
            Team team = teams[i];
            int position = i + 1;
            String standing = String.format("%d) %s %dp, %dg (%d-%d-%d), %dgd (%d-%d)", position,
                    team.name, team.points, team.gamesPlayed(), team.wins, team.ties, team.losses, team.goalsDifference(),
                    team.goalsScored, team.goalsAgainst);
            outputWriter.printLine(standing);
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.ISO_8859_1));
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
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.ISO_8859_1)));
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
