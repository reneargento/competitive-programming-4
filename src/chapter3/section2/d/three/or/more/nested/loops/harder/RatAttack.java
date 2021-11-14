package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/11/21.
 */
public class RatAttack {

    private static class BombLocation {
        int row;
        int column;
        int ratsKilled;

        public BombLocation(int row, int column, int ratsKilled) {
            this.row = row;
            this.column = column;
            this.ratsKilled = ratsKilled;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int bombStrength = FastReader.nextInt();
            int ratPopulations = FastReader.nextInt();
            int[][] city = new int[1025][1025];

            BombLocation bestBombLocation = null;

            for (int i = 0; i < ratPopulations; i++) {
                int column = FastReader.nextInt();
                int row = FastReader.nextInt();
                int population = FastReader.nextInt();

                BombLocation bombLocation = updateCityAndCheckLocations(city, row, column, population, bombStrength);
                if (bestBombLocation == null
                        || bestBombLocation.ratsKilled < bombLocation.ratsKilled
                        || (bestBombLocation.ratsKilled == bombLocation.ratsKilled
                             && (bombLocation.column < bestBombLocation.column
                             || (bombLocation.column == bestBombLocation.column
                                 && bombLocation.row < bestBombLocation.row)))) {
                    bestBombLocation = bombLocation;
                }
            }

            outputWriter.printLine(String.format("%d %d %d", bestBombLocation.column, bestBombLocation.row,
                    bestBombLocation.ratsKilled));
        }
        outputWriter.flush();
    }

    private static BombLocation updateCityAndCheckLocations(int[][] city, int row, int column,
                                                            int population, int bombStrength) {
        int maxRatsKilled = 0;
        int bestRow = Integer.MAX_VALUE;
        int bestColumn = Integer.MAX_VALUE;

        int startRow = Math.max(0, row - bombStrength);
        int endRow = Math.min(city.length - 1, row + bombStrength);
        int startColumn = Math.max(0, column - bombStrength);
        int endColumn = Math.min(city[row].length - 1, column + bombStrength);

        for (int r = startRow; r <= endRow; r++) {
            for (int c = startColumn; c <= endColumn; c++) {
                city[r][c] += population;

                if (city[r][c] > maxRatsKilled
                        || (city[r][c] == maxRatsKilled
                             && (c < bestColumn || (c == bestColumn && r < bestRow)))) {
                    maxRatsKilled = city[r][c];
                    bestRow = r;
                    bestColumn = c;
                }
            }
        }
        return new BombLocation(bestRow, bestColumn, maxRatsKilled);
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
