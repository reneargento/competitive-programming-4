package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/06/2026.
 */
public class Stats {

    private static class Player implements Comparable<Player> {
        String id;
        int hits;
        int kills;
        int errs;
        int blocks;
        int digs;
        int gamesPlayed;

        public Player(String id) {
            this.id = id;
            hits = 0;
            kills = 0;
            errs = 0;
            blocks = 0;
            digs = 0;
            gamesPlayed = 1;
        }

        @Override
        public int compareTo(Player other) {
            return id.compareTo(other.id);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        Map<String, Player> playerMap = new HashMap<>();
        double totalGames = 0;
        while (line != null) {
            String[] data = line.trim().split("\\s+");
            if (data[0].equals("C")) {
                for (int i = 2; i < data.length; i++) {
                    String playerId = data[i];
                    if (!playerMap.containsKey(playerId)) {
                        playerMap.put(playerId, new Player(playerId));
                    } else {
                        playerMap.get(playerId).gamesPlayed++;
                    }
                }
                totalGames++;
            } else if (data[0].equals("R")) {
                generateReport(playerMap, totalGames, outputWriter);
                playerMap = new HashMap<>();
                totalGames = 0;
            } else if (playerMap.containsKey(data[1])) {
                String id = data[1];
                switch (data[0]) {
                    case "H": playerMap.get(id).hits++; break;
                    case "K": playerMap.get(id).kills++; break;
                    case "E": playerMap.get(id).errs++; break;
                    case "B": playerMap.get(id).blocks++; break;
                    case "D": playerMap.get(id).digs++; break;
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void generateReport(Map<String, Player> playerMap, double totalGames, OutputWriter outputWriter) {
        outputWriter.printLine("Player  Hit Pct    KPG      BPG      DPG");
        outputWriter.printLine("-----------------------------------------");
        String format = "%-4s%10s%9.3f%9.3f%9.3f";

        double totalKillsErrorsHitsSum = 0;
        double totalKillsMinusErrors = 0;
        double totalKills = 0;
        double totalBlocks = 0;
        double totalDigs = 0;

        List<Player> playerList = new ArrayList<>(playerMap.values());
        Collections.sort(playerList);
        for (Player player : playerList) {
            double killsErrorsHitsSum = player.kills + player.errs + player.hits;
            double hitPercent = 0;
            if (killsErrorsHitsSum != 0) {
                hitPercent = (player.kills - player.errs) / killsErrorsHitsSum;
                totalKillsMinusErrors += (player.kills - player.errs);
                totalKillsErrorsHitsSum += killsErrorsHitsSum;
            }
            String hitPercentFormatted = getHitPercentFormatted(hitPercent);
            double killsPerGame = player.kills / (double) player.gamesPlayed;
            double blocksPerGame = player.blocks / (double) player.gamesPlayed;
            double digsPerGame = player.digs / (double) player.gamesPlayed;
            outputWriter.printLine(String.format(format, player.id, hitPercentFormatted, killsPerGame, blocksPerGame,
                    digsPerGame));

            totalKills += player.kills;
            totalBlocks += player.blocks;
            totalDigs += player.digs;
        }

        double totalHitPercent = 0;
        if (totalKillsErrorsHitsSum != 0) {
            totalHitPercent = totalKillsMinusErrors / totalKillsErrorsHitsSum;
        }
        String totalHitPercentFormatted = getHitPercentFormatted(totalHitPercent);
        outputWriter.printLine(String.format(format, "team",
                totalHitPercentFormatted,
                totalKills / totalGames,
                totalBlocks / totalGames,
                totalDigs / totalGames));
        outputWriter.printLine();
    }

    private static String getHitPercentFormatted(double hitPercent) {
        String formattedHitPercent = "";
        if (hitPercent >= 0) {
            formattedHitPercent = "+";
        }
        formattedHitPercent += String.format("%.3f", hitPercent);
        return formattedHitPercent;
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