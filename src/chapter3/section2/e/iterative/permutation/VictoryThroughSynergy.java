package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/12/21.
 */
@SuppressWarnings("unchecked")
public class VictoryThroughSynergy {

    private static class Player {
        String name;
        String nation;
        String league;
        String team;

        public Player(String name, String nation, String league, String team) {
            this.name = name;
            this.nation = nation;
            this.league = league;
            this.team = team;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int edgesNumber = FastReader.nextInt();
        List<Integer>[] adjacencyList = new ArrayList[10];

        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int e = 0; e < edgesNumber; e++) {
            int node1 = FastReader.nextInt();
            int node2 = FastReader.nextInt();
            adjacencyList[node1].add(node2);
            adjacencyList[node2].add(node1);
        }

        Player[] players = new Player[10];
        for (int p = 0; p < players.length; p++) {
            players[p] = new Player(FastReader.next(), FastReader.next(), FastReader.next(), FastReader.next());
        }

        boolean canMakePerfectTeam = canMakePerfectTeam(adjacencyList, players);
        outputWriter.printLine(canMakePerfectTeam ? "yes" : "no");
        outputWriter.flush();
    }

    private static boolean canMakePerfectTeam(List<Integer>[] adjacencyList, Player[] players) {
        Player[] team = new Player[players.length];
        return canMakePerfectTeam(adjacencyList, players, team, 0, 0);
    }

    private static boolean canMakePerfectTeam(List<Integer>[] adjacencyList, Player[] players, Player[] team,
                                              int index, int mask) {
        if (mask == (1 << players.length) - 1) {
            return isTeamPerfect(adjacencyList, team);
        }

        for (int i = 0; i < players.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                team[index] = players[i];
                if (canMakePerfectTeam(adjacencyList, players, team, index + 1, newMask)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isTeamPerfect(List<Integer>[] adjacencyList, Player[] team) {
        for (int playerId = 0; playerId < team.length; playerId++) {
            List<Integer> playerEdges = adjacencyList[playerId];
            int nodeDegree = playerEdges.size();
            int totalSynergy = 0;
            Player player = team[playerId];

            for (int otherPlayerId : playerEdges) {
                Player otherPlayer = team[otherPlayerId];
                totalSynergy += computeSynergy(player, otherPlayer);
            }

            if (totalSynergy < nodeDegree) {
                return false;
            }
        }
        return true;
    }

    private static int computeSynergy(Player player1, Player player2) {
        if (player1.nation.equals(player2.nation)) {
            if (player1.team.equals(player2.team)) {
                return 3;
            } else if (player1.league.equals(player2.league)) {
                return 2;
            }
            return 1;
        } else if (player1.team.equals(player2.team)) {
            return 2;
        } else if (player1.league.equals(player2.league)) {
            return 1;
        }
        return 0;
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
