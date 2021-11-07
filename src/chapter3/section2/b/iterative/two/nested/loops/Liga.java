package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/11/21.
 */
public class Liga {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int teams = FastReader.nextInt();

        for (int t = 0; t < teams; t++) {
            String games = FastReader.next();
            String wins = FastReader.next();
            String draws = FastReader.next();
            String losses = FastReader.next();
            String points = FastReader.next();

            int gamesNumber = getValue(games);
            int winsNumber = getValue(wins);
            int drawsNumber = getValue(draws);
            int lossesNumber = getValue(losses);
            int pointsNumber = getValue(points);

            int startWins = 0;
            int endWins = 100;
            int startDraws = 0;
            int endDraws = 100;

            if (winsNumber != -1) {
                startWins = winsNumber;
                endWins = winsNumber;
            }
            if (drawsNumber != -1) {
                startDraws = drawsNumber;
                endDraws = drawsNumber;
            }

            if (gamesNumber == -1 && lossesNumber == -1) {
                lossesNumber = 0;
            }

            boolean solved = false;

            for (int currentWins = startWins; currentWins <= endWins; currentWins++) {
                for (int currentDraws = startDraws; currentDraws <= endDraws; currentDraws++) {
                    int currentGames = gamesNumber;
                    int currentLosses = lossesNumber;
                    int currentPoints = pointsNumber;

                    if (gamesNumber == -1) {
                        currentGames = currentWins + currentDraws + lossesNumber;
                    } else if (lossesNumber == -1) {
                        currentLosses = gamesNumber - (currentWins + currentDraws);
                    }

                    if (pointsNumber == -1) {
                        currentPoints = (currentWins * 3) + currentDraws;
                    }

                    if ((currentGames == currentWins + currentDraws + currentLosses) &&
                            (currentPoints == (currentWins * 3) + currentDraws) &&
                            currentGames >= 0 && currentGames <= 100 &&
                            currentLosses >= 0 && currentLosses <= 100) {
                        outputWriter.printLine(String.format("%s %s %s %s %s", currentGames, currentWins,
                                currentDraws, currentLosses, currentPoints));
                        solved = true;
                        break;
                    }
                }
                if (solved) {
                    break;
                }
            }
        }
        outputWriter.flush();
    }

    private static int getValue(String gameData) {
        int value;
        if (gameData.equals("?")) {
            value = -1;
        } else {
            value = Integer.parseInt(gameData);
        }
        return value;
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
