package chapter3.section4.a.classical;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/05/22.
 */
@SuppressWarnings("unchecked")
public class TheGrandDinner {

    private static class Team implements Comparable<Team> {
        int id;
        int numberOfMembers;

        public Team(int id, int numberOfMembers) {
            this.id = id;
            this.numberOfMembers = numberOfMembers;
        }

        @Override
        public int compareTo(Team other) {
            return Integer.compare(other.numberOfMembers, numberOfMembers);
        }
    }

    private static class Table implements Comparable<Table> {
        int id;
        int capacity;

        public Table(int id, int capacity) {
            this.id = id;
            this.capacity = capacity;
        }

        // Although it is possible to get AC without sorting the tables, that solution would fail on cases such as:
        // 4 5
        // 5 4 3 3
        // 3 3 3 4 5
        // 0 0
        // That algorithm would print 0 while there exists a solution. Sorting the tables solves all cases.
        @Override
        public int compareTo(Table other) {
            return Integer.compare(other.capacity, capacity);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int teamsNumber = FastReader.nextInt();
        int tablesNumber = FastReader.nextInt();

        while (teamsNumber != 0 || tablesNumber != 0) {
            Team[] teams = new Team[teamsNumber];
            Table[] tables = new Table[tablesNumber];
            for (int i = 0; i < teams.length; i++) {
                teams[i] = new Team(i, FastReader.nextInt());
            }
            for (int i = 0; i < tables.length; i++) {
                tables[i] = new Table(i, FastReader.nextInt());
            }

            int[][] teamPerSeat = new int[teams.length][];
            for (int i = 0; i < teamPerSeat.length; i++) {
                teamPerSeat[i] = new int[teams[i].numberOfMembers];
            }

            boolean possible = assignSeats(teams, tables, teamPerSeat);
            if (possible) {
                outputWriter.printLine(1);
                for (int i = 0; i < teamPerSeat.length; i++) {
                    outputWriter.print(teamPerSeat[i][0]);
                    for (int j = 1; j < teamPerSeat[i].length; j++) {
                        outputWriter.print(" " + teamPerSeat[i][j]);
                    }
                    outputWriter.printLine();
                }
            } else {
                outputWriter.printLine(0);
            }
            teamsNumber = FastReader.nextInt();
            tablesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean assignSeats(Team[] teams, Table[] tables, int[][] teamPerSeat) {
        Arrays.sort(teams);
        Arrays.sort(tables);
        Set<Integer>[] currentSeats = new Set[tables.length];
        for (int i = 0; i < currentSeats.length; i++) {
            currentSeats[i] = new HashSet<>();
        }

        for (int i = 0; i < teams.length; i++) {
            int teamId = teams[i].id;
            for (int memberId = 0; memberId < teams[i].numberOfMembers; memberId++) {
                boolean assigned = assignSeat(currentSeats, teamId, memberId, tables, teamPerSeat);
                if (!assigned) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean assignSeat(Set<Integer>[] currentSeats, int teamId, int memberId, Table[] tables,
                                      int[][] teamPerSeat) {
        for (int i = 0; i < currentSeats.length; i++) {
            if (currentSeats[i].size() < tables[i].capacity
                    && !currentSeats[i].contains(teamId)) {
                currentSeats[i].add(teamId);
                teamPerSeat[teamId][memberId] = tables[i].id + 1;
                return true;
            }
        }
        return false;
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
