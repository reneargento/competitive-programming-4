package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/06/21.
 */
@SuppressWarnings("unchecked")
public class FantasyDraft {

    private static class Player implements Comparable<Player> {
        String name;
        int priority;

        public Player(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public int compareTo(Player other) {
            return Integer.compare(priority, other.priority);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int owners = FastReader.nextInt();
        int teamSizes = FastReader.nextInt();

        LinkedList<String>[] ownerPreferences = new LinkedList[owners];

        for (int i = 0; i < ownerPreferences.length; i++) {
            int listSize = FastReader.nextInt();
            ownerPreferences[i] = new LinkedList<>();

            for (int p = 0; p < listSize; p++) {
                ownerPreferences[i].add(FastReader.next());
            }
        }

        int playersInDraftNumber = FastReader.nextInt();
        Map<String, Player> nameToPlayerMap = new TreeMap<>();
        TreeSet<Player> playersInDraft = new TreeSet<>();

        for (int priority = 0; priority < playersInDraftNumber; priority++) {
            String playerName = FastReader.next();
            Player player = new Player(playerName, priority);

            playersInDraft.add(player);
            nameToPlayerMap.put(playerName, player);
        }
        List<String>[] teams = buildTeams(playersInDraft, nameToPlayerMap, ownerPreferences, teamSizes);
        printTeams(teams);
    }

    private static List<String>[] buildTeams(TreeSet<Player> playersInDraft, Map<String, Player> nameToPlayerMap,
                                             LinkedList<String>[] ownerPreferences, int teamSizes) {
        List<String>[] teams = new ArrayList[ownerPreferences.length];
        for (int i = 0; i < teams.length; i++) {
            teams[i] = new ArrayList<>();
        }

        for (int i = 0; i < teamSizes; i++) {
            for (int ownerIndex = 0; ownerIndex < ownerPreferences.length; ownerIndex++) {
                LinkedList<String> preferences = ownerPreferences[ownerIndex];
                while (!preferences.isEmpty() && !nameToPlayerMap.containsKey(preferences.peekFirst())) {
                    preferences.pollFirst();
                }

                if (!preferences.isEmpty()) {
                    String selectedPlayerName = preferences.pollFirst();
                    Player selectedPlayer = nameToPlayerMap.get(selectedPlayerName);
                    teams[ownerIndex].add(selectedPlayerName);
                    playersInDraft.remove(selectedPlayer);
                    nameToPlayerMap.remove(selectedPlayerName);
                } else {
                    Player selectedPlayer = playersInDraft.pollFirst();
                    teams[ownerIndex].add(selectedPlayer.name);
                    nameToPlayerMap.remove(selectedPlayer.name);
                }
            }
        }
        return teams;
    }

    private static void printTeams(List<String>[] teams) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        for (int team = 0; team < teams.length; team++) {
            List<String> playerSelected = teams[team];

            for (int i = 0; i < playerSelected.size(); i++) {
                outputWriter.print(playerSelected.get(i));

                if (i < playerSelected.size() - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
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
