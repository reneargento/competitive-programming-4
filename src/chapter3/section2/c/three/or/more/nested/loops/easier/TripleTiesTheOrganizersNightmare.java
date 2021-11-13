package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/11/21.
 */
public class TripleTiesTheOrganizersNightmare {

    private static class TripleTie implements Comparable<TripleTie> {
        int player1;
        int player2;
        int player3;

        public TripleTie(int player1, int player2, int player3) {
            this.player1 = player1;
            this.player2 = player2;
            this.player3 = player3;
        }

        @Override
        public int compareTo(TripleTie other) {
            if (player1 != other.player1) {
                return Integer.compare(player1, other.player1);
            }
            if (player2 != other.player2) {
                return Integer.compare(player2, other.player2);
            }
            return Integer.compare(player3, other.player3);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int players = Integer.parseInt(line);

            int[][] games = new int[players][players];
            for (int row = 0; row < games.length; row++) {
                for (int column = 0; column < games[0].length; column++) {
                    games[row][column] = FastReader.nextInt();
                }
            }

            List<TripleTie> tripleTies = getTripleTies(games);
            outputWriter.printLine(tripleTies.size());
            for (TripleTie tripleTie : tripleTies) {
                outputWriter.printLine(String.format("%d %d %d", tripleTie.player1, tripleTie.player2,
                        tripleTie.player3));
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<TripleTie> getTripleTies(int[][] games) {
        List<TripleTie> tripleTies = new ArrayList<>();

        for (int player1 = 0; player1 < games.length; player1++) {
            for (int player2 = 0; player2 < games.length; player2++) {
                if (player1 == player2) {
                    continue;
                }

                for (int player3 = 0; player3 < games.length; player3++) {
                    if (player1 == player3 || player2 == player3) {
                        continue;
                    }

                    if (((games[player1][player2] == 0
                            && games[player1][player3] == 0
                            && games[player2][player1] == 0
                            && games[player2][player3] == 0
                            && games[player3][player1] == 0
                            && games[player3][player2] == 0
                            && (player1 < player2 && player2 < player3))
                            || (games[player1][player2] == 1
                            && games[player2][player3] == 1
                            && games[player3][player1] == 1))
                            && isValidTripleTie(player1, player2, player3)
                    ) {
                        tripleTies.add(new TripleTie(player1 + 1, player2 + 1, player3 + 1));
                    }
                }
            }
        }
        Collections.sort(tripleTies);
        return tripleTies;
    }

    private static boolean isValidTripleTie(int player1, int player2, int player3) {
        return (player1 < player2 && player2 < player3)
                || (player1 > player2 && player2 > player3);
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
