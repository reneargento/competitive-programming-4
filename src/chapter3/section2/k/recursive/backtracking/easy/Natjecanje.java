package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/01/22.
 */
public class Natjecanje {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int teams = FastReader.nextInt();
        int[] damagedKayakTeams = new int[FastReader.nextInt()];
        int[] reserveKayakTeams = new int[FastReader.nextInt()];

        for (int i = 0; i < damagedKayakTeams.length; i++) {
            damagedKayakTeams[i] = FastReader.nextInt();
        }
        for (int i = 0; i < reserveKayakTeams.length; i++) {
            reserveKayakTeams[i] = FastReader.nextInt();
        }
        int minimumTeamsThatCannotStart = computeMinimumTeamsThatCannotStart(teams, damagedKayakTeams,
                reserveKayakTeams, 0, 0);
        outputWriter.printLine(minimumTeamsThatCannotStart);
        outputWriter.flush();
    }

    private static int computeMinimumTeamsThatCannotStart(int teams, int[] damagedKayakTeams, int[] reserveKayakTeams,
                                                          int maskReserveKayaksTaken, int currentDamagedTeamId) {
        if (currentDamagedTeamId == damagedKayakTeams.length) {
            int kayaksBorrowed = countKayaksBorrowed(maskReserveKayaksTaken);
            return damagedKayakTeams.length - kayaksBorrowed;
        }
        int minimumTeamsThatCannotStart;

        int leftTeamId = damagedKayakTeams[currentDamagedTeamId] - 1;
        minimumTeamsThatCannotStart = computeMinimumTeamsBorrowingAndNot(teams, damagedKayakTeams,
                reserveKayakTeams, maskReserveKayaksTaken, currentDamagedTeamId, leftTeamId);

        int currentTeamId = damagedKayakTeams[currentDamagedTeamId];
        int teamsThatCannotStartCurrent = computeMinimumTeamsBorrowingAndNot(teams, damagedKayakTeams,
                reserveKayakTeams, maskReserveKayaksTaken, currentDamagedTeamId, currentTeamId);
        minimumTeamsThatCannotStart = Math.min(minimumTeamsThatCannotStart, teamsThatCannotStartCurrent);

        int rightTeamId = damagedKayakTeams[currentDamagedTeamId] + 1;
        if (rightTeamId <= teams) {
            int teamsThatCannotStartRight = computeMinimumTeamsBorrowingAndNot(teams, damagedKayakTeams,
                    reserveKayakTeams, maskReserveKayaksTaken, currentDamagedTeamId, rightTeamId);
            minimumTeamsThatCannotStart = Math.min(minimumTeamsThatCannotStart, teamsThatCannotStartRight);
        }
        return minimumTeamsThatCannotStart;
    }

    private static int computeMinimumTeamsBorrowingAndNot(int teams, int[] damagedKayakTeams, int[] reserveKayakTeams,
                                                          int maskReserveKayaksTaken, int currentDamagedTeamId,
                                                          int teamId) {
        int teamsThatCannotStartBorrowing = Integer.MAX_VALUE;
        if (canTakeKayak(teamId, reserveKayakTeams, maskReserveKayaksTaken)) {
            int newMask = maskReserveKayaksTaken | (1 << teamId);
            teamsThatCannotStartBorrowing = computeMinimumTeamsThatCannotStart(teams, damagedKayakTeams,
                    reserveKayakTeams, newMask, currentDamagedTeamId + 1);
        }

        int teamsThatCannotStartWithoutBorrowing = computeMinimumTeamsThatCannotStart(teams, damagedKayakTeams,
                reserveKayakTeams, maskReserveKayaksTaken, currentDamagedTeamId + 1);
        return Math.min(teamsThatCannotStartBorrowing, teamsThatCannotStartWithoutBorrowing);
    }

    private static boolean canTakeKayak(int teamId, int[] reserveKayakTeams, int maskReserveKayaksTaken) {
        if ((maskReserveKayaksTaken & (1 << teamId)) > 0) {
            return false;
        }

        for (int reserveKayakTeam : reserveKayakTeams) {
            if (reserveKayakTeam == teamId) {
                return true;
            }
        }
        return false;
    }

    private static int countKayaksBorrowed(int mask) {
        int kayaksBorrowed = 0;

        while (mask > 0) {
            kayaksBorrowed++;
            mask = mask & (mask - 1);
        }
        return kayaksBorrowed;
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
