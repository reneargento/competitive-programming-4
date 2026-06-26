package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 24/06/2026.
 */
public class TeamArrangement {

    private static class Player implements Comparable<Player> {
        int number;
        String name;
        String role;
        int yearsOfExperience;

        public Player(int number, String name, String role, int yearsOfExperience) {
            this.number = number;
            this.name = name;
            this.role = role;
            this.yearsOfExperience = yearsOfExperience;
        }

        @Override
        public int compareTo(Player other) {
            return Integer.compare(this.number, other.number);
        }

        @Override
        public String toString() {
            return number + " " + name + " " + role;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("0")) {
            List<Player> goalkeepers = new ArrayList<>();
            List<Player> defenders = new ArrayList<>();
            List<Player> midfielders = new ArrayList<>();
            List<Player> strikers = new ArrayList<>();

            for (int i = 0; i < 22; i++) {
                String[] data = line.split(" ");
                Player player = parsePlayer(data);
                String role = data[2];
                if (role.equals("G")) {
                    goalkeepers.add(player);
                } else if (role.equals("D")) {
                    defenders.add(player);
                }  else if (role.equals("M")) {
                    midfielders.add(player);
                } else {
                    strikers.add(player);
                }
                line = FastReader.getLine();
            }
            selectAndPrintPlayers(goalkeepers, defenders, midfielders, strikers, line, outputWriter);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void selectAndPrintPlayers(List<Player> goalkeepers, List<Player> defenders,
                                              List<Player> midfielders, List<Player> strikers, String formation,
                                              OutputWriter outputWriter) {
        String[] formationNumbers = formation.split("-");
        int goalkeeperNumber = 1;
        int defendersNumber = Integer.parseInt(formationNumbers[0]);
        int midfieldersNumber = Integer.parseInt(formationNumbers[1]);
        int strikersNumber = Integer.parseInt(formationNumbers[2]);

        if (goalkeepers.size() < goalkeeperNumber
                || defenders.size() < defendersNumber
                || midfielders.size() < midfieldersNumber
                || strikers.size() < strikersNumber) {
            outputWriter.printLine("IMPOSSIBLE TO ARRANGE\n");
            return;
        }

        Collections.sort(goalkeepers);
        Collections.sort(defenders);
        Collections.sort(midfielders);
        Collections.sort(strikers);

        List<Player> selectedPlayers = new ArrayList<>();
        selectPlayers(selectedPlayers, goalkeepers, goalkeeperNumber);
        selectPlayers(selectedPlayers, defenders, defendersNumber);
        selectPlayers(selectedPlayers, midfielders, midfieldersNumber);
        selectPlayers(selectedPlayers, strikers, strikersNumber);

        Player captain = selectCaptain(selectedPlayers);
        outputWriter.printLine(captain);
        for (Player player : selectedPlayers) {
            if (player.number == captain.number) {
                continue;
            }
            outputWriter.printLine(player);
        }
        outputWriter.printLine();
    }

    private static Player selectCaptain(List<Player> selectedPlayers) {
        Player captain = null;
        for (Player player : selectedPlayers) {
            if (captain == null
                    || player.yearsOfExperience > captain.yearsOfExperience
                    || (player.yearsOfExperience == captain.yearsOfExperience
                        && player.number > captain.number)) {
                captain = player;
            }
        }
        return captain;
    }

    private static void selectPlayers(List<Player> selectedPlayers, List<Player> playersToSelect, int number) {
        for (int i = 0; i < number; i++) {
            selectedPlayers.add(playersToSelect.get(i));
        }
    }

    private static Player parsePlayer(String[] data) {
        int yearsOfExperience = 0;
        for (int i = 3; i < data.length; i++) {
            String[] years = data[i].split("-");
            int startYear = Integer.parseInt(years[0]);
            int endYear = Integer.parseInt(years[1]);
            yearsOfExperience += endYear - startYear + 1;
        }
        return new Player(Integer.parseInt(data[0]), data[1], data[2], yearsOfExperience);
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void flush() {
            writer.flush();
        }
    }
}