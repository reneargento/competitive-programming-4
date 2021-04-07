package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/04/21.
 */
public class FoosballDynasty {

    private static class Team {
        String name1;
        String name2;

        public Team(String name1, String name2) {
            this.name1 = name1;
            this.name2 = name2;
        }
    }

    private static class Player {
        String name;
        int index;

        public Player(String name, int index) {
            this.name = name;
            this.index = index;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int playersNumber = FastReader.nextInt();

        Queue<Player> playersQueue = new LinkedList<>();
        Player whiteOffense = null;
        Player blackOffense = null;
        Player whiteDefense = null;
        Player blackDefense = null;

        for (int i = 0; i < playersNumber; i++) {
            String name = FastReader.next();
            if (i == 0) {
                whiteOffense = new Player(name, 0);
            } else if (i == 1) {
                blackOffense = new Player(name, 1);
            } else if (i == 2) {
                whiteDefense = new Player(name, 2);
            } else if (i == 3) {
                blackDefense = new Player(name, 3);
            } else {
                playersQueue.offer(new Player(name, i));
            }
        }
        String points = FastReader.next();

        List<Team> winnerTeams = playFoosball(whiteOffense, blackOffense, whiteDefense, blackDefense, playersQueue,
                points, playersNumber);
        for (Team team : winnerTeams) {
            outputWriter.printLine(String.format("%s %s", team.name1, team.name2));
        }
        outputWriter.flush();
    }

    private static List<Team> playFoosball(Player whiteOffense, Player blackOffense, Player whiteDefense,
                                           Player blackDefense, Queue<Player> playersQueue, String points,
                                           int playersNumber) {
        List<Team> winnerTeams = new ArrayList<>();
        int highestDynastyLength = 0;
        int currentDynastyLength = 0;
        char currentWinners = 'W';
        int currentPlayersIndex = playersNumber - 1;

        for (int i = 0; i < points.length(); i++) {
            char point = points.charAt(i);

            if (i == 0 || currentWinners == point) {
                currentDynastyLength++;
            } else {
                currentDynastyLength = 1;
            }

            if (currentDynastyLength > highestDynastyLength) {
                winnerTeams = new ArrayList<>();
            }
            if (currentDynastyLength >= highestDynastyLength) {
                if (point == 'W') {
                    addPlayersToWinnersList(winnerTeams, whiteOffense, whiteDefense);
                } else {
                    addPlayersToWinnersList(winnerTeams, blackOffense, blackDefense);
                }
                highestDynastyLength = currentDynastyLength;
            }

            currentWinners = point;
            currentPlayersIndex++;

            if (point == 'W') {
                Player aux = whiteOffense;
                whiteOffense = whiteDefense;
                whiteDefense = aux;

                playersQueue.offer(new Player(blackDefense.name, currentPlayersIndex));
                blackDefense = blackOffense;
                blackOffense = playersQueue.poll();
            } else {
                Player aux = blackOffense;
                blackOffense = blackDefense;
                blackDefense = aux;

                playersQueue.offer(new Player(whiteDefense.name, currentPlayersIndex));
                whiteDefense = whiteOffense;
                whiteOffense = playersQueue.poll();
            }
        }
        return winnerTeams;
    }

    private static void addPlayersToWinnersList(List<Team> winnerTeams, Player player1, Player player2) {
        String name1;
        String name2;

        if (player1.index < player2.index) {
            name1 = player1.name;
            name2 = player2.name;
        } else {
            name1 = player2.name;
            name2 = player1.name;
        }
        winnerTeams.add(new Team(name1, name2));
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
