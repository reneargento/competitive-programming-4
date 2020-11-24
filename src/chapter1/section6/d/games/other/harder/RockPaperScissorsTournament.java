package chapter1.section6.d.games.other.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/10/20.
 */
public class RockPaperScissorsTournament {

    private static class Player {
        int wins;
        int losses;

        private String getAverageWin() {
            double totalGames = wins + losses;
            if (totalGames == 0) {
                return "-";
            }
            double winRatio = wins / totalGames;
            return String.format("%.3f", winRatio);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int playersNumber = FastReader.nextInt();
        int tournamentId = 1;

        while (playersNumber != 0) {
            int k = FastReader.nextInt();
            int games = k * playersNumber * (playersNumber - 1) / 2;

            Player[] players = new Player[playersNumber];
            for (int i = 0; i < players.length; i++) {
                players[i] = new Player();
            }

            for (int g = 0; g < games; g++) {
                int player1Id = FastReader.nextInt() - 1;
                String player1Move = FastReader.next();
                int player2Id = FastReader.nextInt() - 1;
                String player2Move = FastReader.next();
                play(players, player1Id, player1Move, player2Id, player2Move);
            }

            if (tournamentId > 1) {
                System.out.println();
            }

            for (Player player : players) {
                System.out.println(player.getAverageWin());
            }

            tournamentId++;
            playersNumber = FastReader.nextInt();
        }
    }

    private static void play(Player[] players, int player1Id, String player1Move, int player2Id, String player2Move) {
        if ((player1Move.equals("rock") && player2Move.equals("scissors"))
                || (player1Move.equals("scissors") && player2Move.equals("paper"))
                || (player1Move.equals("paper") && player2Move.equals("rock"))) {
            players[player1Id].wins++;
            players[player2Id].losses++;
        } else if ((player2Move.equals("rock") && player1Move.equals("scissors"))
                || (player2Move.equals("scissors") && player1Move.equals("paper"))
                || (player2Move.equals("paper") && player1Move.equals("rock"))) {
            players[player2Id].wins++;
            players[player1Id].losses++;
        }
    }

    public static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
