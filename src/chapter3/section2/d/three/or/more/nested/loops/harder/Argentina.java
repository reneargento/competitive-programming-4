package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/11/21.
 */
public class Argentina {

    private static class Player implements Comparable<Player> {
        String name;
        int attackingAbility;
        int defendingAbility;

        public Player(String name, int attackingAbility, int defendingAbility) {
            this.name = name;
            this.attackingAbility = attackingAbility;
            this.defendingAbility = defendingAbility;
        }

        @Override
        public int compareTo(Player other) {
            if (attackingAbility != other.attackingAbility) {
                return Integer.compare(other.attackingAbility, attackingAbility);
            }
            if (defendingAbility != other.defendingAbility) {
                return Integer.compare(defendingAbility, other.defendingAbility);
            }
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            Player[] players = new Player[10];
            for (int i = 0; i < players.length; i++) {
                players[i] = new Player(FastReader.next(), FastReader.nextInt(), FastReader.nextInt());
            }
            Arrays.sort(players);

            Player[] attackers = { players[0], players[1], players[2], players[3], players[4] };
            Player[] defenders = { players[5], players[6], players[7], players[8], players[9] };

            Comparator<Player> nameComparator = new Comparator<Player>() {
                @Override
                public int compare(Player player1, Player player2) {
                    return player1.name.compareTo(player2.name);
                }
            };

            Arrays.sort(attackers, nameComparator);
            Arrays.sort(defenders, nameComparator);

            outputWriter.printLine(String.format("Case %d:", t));
            outputWriter.printLine(String.format("(%s, %s, %s, %s, %s)", attackers[0].name,
                    attackers[1].name, attackers[2].name, attackers[3].name, attackers[4].name));
            outputWriter.printLine(String.format("(%s, %s, %s, %s, %s)", defenders[0].name,
                    defenders[1].name, defenders[2].name, defenders[3].name, defenders[4].name));
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
