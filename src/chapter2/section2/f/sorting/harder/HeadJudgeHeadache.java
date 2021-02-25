package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/02/21.
 */
public class HeadJudgeHeadache {

    private static class Team implements Comparable<Team> {
        int id;
        boolean[] problemsSolved;
        int[] timeConsumed;
        int problemsSolvedCount;
        int totalTimeConsumed;

        public Team(int id) {
            this.id = id;
            problemsSolved = new boolean[8];
            timeConsumed = new int[8];
        }

        public boolean hasDifferentRank(Team other) {
            return problemsSolvedCount != other.problemsSolvedCount
                    || totalTimeConsumed != other.totalTimeConsumed;
        }

        @Override
        public int compareTo(Team other) {
            if (problemsSolvedCount != other.problemsSolvedCount) {
                return other.problemsSolvedCount - problemsSolvedCount;
            }
            if (totalTimeConsumed != other.totalTimeConsumed) {
                return totalTimeConsumed - other.totalTimeConsumed;
            }
            return id - other.id;
        }
    }

    private static class Submission implements Comparable<Submission> {
        int teamId;
        int problemId;
        int hours;
        int minutes;
        char status;

        public Submission(int teamId, int problemId, int hours, int minutes, char status) {
            this.teamId = teamId;
            this.problemId = problemId;
            this.hours = hours;
            this.minutes = minutes;
            this.status = status;
        }

        @Override
        public int compareTo(Submission other) {
            if (hours != other.hours) {
                return hours - other.hours;
            }
            if (minutes != other.minutes) {
                return minutes - other.minutes;
            }
            if (status != other.status) {
                if (status == 'Y') {
                    return 1;
                } else {
                    return -1;
                }
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int contests = FastReader.nextInt();
        FastReader.getLine();

        for (int i = 0; i < contests; i++) {
            if (i > 0) {
                outputWriter.printLine();
            }
            Team[] teams = createTeamList();
            String line = FastReader.getLine();
            List<Submission> submissions = new ArrayList<>();
            int maxTeamId = 0;

            while (line != null && !line.isEmpty()) {
                String[] data = line.split(" ");
                int teamId = Integer.parseInt(data[0]) - 1;
                char problem = data[1].charAt(0);
                String[] time = data[2].split(":");
                int hours = Integer.parseInt(time[0]);
                int minutes = Integer.parseInt(time[1]);
                char status = data[3].charAt(0);

                int problemId = problem - 'A';
                submissions.add(new Submission(teamId, problemId, hours, minutes, status));
                maxTeamId = Math.max(maxTeamId, teamId);

                line = FastReader.getLine();
            }

            Collections.sort(submissions);
            processSubmissions(teams, submissions);
            Arrays.sort(teams);
            printTeams(teams, maxTeamId, outputWriter);
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void processSubmissions(Team[] teams, List<Submission> submissions) {
        for (Submission submission : submissions) {
            Team team = teams[submission.teamId];
            int problemId = submission.problemId;

            if (!team.problemsSolved[problemId]) {
                if (submission.status == 'N') {
                    team.timeConsumed[problemId] += 20;
                } else {
                    int currentTime = submission.hours * 60 + submission.minutes;
                    team.totalTimeConsumed += currentTime + team.timeConsumed[problemId];
                    team.problemsSolvedCount++;
                    team.problemsSolved[problemId] = true;
                }
            }
        }
    }

    private static void printTeams(Team[] teams, int maxTeamId, OutputWriter outputWriter) {
        outputWriter.printLine("RANK TEAM PRO/SOLVED TIME");
        int rank = 1;

        for (int i = 0; i <= maxTeamId; i++) {
            Team team = teams[i];

            if (i == 0 || team.hasDifferentRank(teams[i - 1])) {
                rank = i + 1;
            }
            String solved = "";
            if (team.problemsSolvedCount > 0) {
                solved = String.valueOf(team.problemsSolvedCount);
            }

            String time = "";
            if (team.totalTimeConsumed > 0) {
                time = String.valueOf(team.totalTimeConsumed);
            }
            int correctTeamId = team.id + 1;
            String result;

            if (solved.isEmpty() && time.isEmpty()) {
                result = String.format("%4d %4d", rank, correctTeamId);
            } else if (time.isEmpty()) {
                result = String.format("%4d %4d %4s", rank, correctTeamId, solved);
            } else {
                result = String.format("%4d %4d %4s %10s", rank, correctTeamId, solved, time);
            }
            outputWriter.printLine(result);
        }
    }

    private static Team[] createTeamList() {
        Team[] teams = new Team[26];
        for (int i = 0; i <= 25; i++) {
            teams[i] = new Team(i);
        }
        return teams;
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
