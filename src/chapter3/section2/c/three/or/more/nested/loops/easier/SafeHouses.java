package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/11/21.
 */
public class SafeHouses {

    private static class Block {
        int row;
        int column;

        public Block(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cityLength = FastReader.nextInt();
        String[][] city = new String[cityLength][cityLength];

        for (int row = 0; row < city.length; row++) {
            city[row] = FastReader.next().split("");
        }

        List<Block> spyLocations = new ArrayList<>();
        List<Block> safeHouseLocations = new ArrayList<>();

        for (int row = 0; row < city.length; row++) {
            for (int column = 0; column < city[0].length; column++) {
                if (city[row][column].equals("S")) {
                    spyLocations.add(new Block(row, column));
                } else if (city[row][column].equals("H")) {
                    safeHouseLocations.add(new Block(row, column));
                }
            }
        }
        int maximumManhattanDistance = getMaximumManhattanDistance(spyLocations, safeHouseLocations);
        outputWriter.printLine(maximumManhattanDistance);

        outputWriter.flush();
    }

    private static int getMaximumManhattanDistance(List<Block> spyLocations, List<Block> safeHouseLocations) {
        int maximumManhattanDistance = 0;

        for (Block spyLocation : spyLocations) {
            int manhattanDistance = Integer.MAX_VALUE;

            for (Block safeHouseLocation : safeHouseLocations) {
                int distance = Math.abs(spyLocation.row - safeHouseLocation.row)
                        + Math.abs(spyLocation.column - safeHouseLocation.column);
                manhattanDistance = Math.min(manhattanDistance, distance);
            }
            maximumManhattanDistance = Math.max(maximumManhattanDistance, manhattanDistance);
        }
        return maximumManhattanDistance;
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
