package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/03/24.
 */
public class BearWithMeAgain {

    private static class Island {
        int x;
        int y;
        int radius;

        public Island(int x, int y, int radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int maxDaysAtSea = Integer.parseInt(data[0]);
            int rowSpeed = Integer.parseInt(data[1]);

            Island currentIsland = new Island(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            Island homeIsland = new Island(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            Island[] islands = new Island[FastReader.nextInt() + 2];
            islands[0] = currentIsland;
            islands[1] = homeIsland;

            for (int i = 2; i < islands.length; i++) {
                islands[i] = new Island(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            }

            boolean canEscape = canEscape(islands, maxDaysAtSea, rowSpeed);
            outputWriter.printLine(canEscape ? "Larry and Ryan will escape!" : "Larry and Ryan will be eaten to death.");
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean canEscape(Island[] islands, int maxDaysAtSea, int rowSpeed) {
        int maxDistance = maxDaysAtSea * rowSpeed;
        double[][] distances = new double[islands.length][islands.length];
        for (double[] values : distances) {
            Arrays.fill(values, Double.POSITIVE_INFINITY);
        }
        for (int islandID = 0; islandID < distances.length; islandID++) {
            distances[islandID][islandID] = 0;
        }

        for (int islandID1 = 0; islandID1 < distances.length; islandID1++) {
            for (int islandID2 = islandID1 + 1; islandID2 < distances.length; islandID2++) {
                double distance = distanceBetweenIslands(islands[islandID1], islands[islandID2]);
                if (distance <= maxDistance) {
                    distances[islandID1][islandID2] = distance;
                    distances[islandID2][islandID1] = distance;
                }
            }
        }

        floydWarshall(distances);
        return distances[0][1] != Double.POSITIVE_INFINITY;
    }

    private static double distanceBetweenIslands(Island island1, Island island2) {
        return Math.sqrt(Math.pow(island1.x - island2.x, 2) + Math.pow(island1.y - island2.y, 2))
                - island1.radius - island2.radius;
    }

    private static void floydWarshall(double[][] distances) {
        int vertices = distances.length;

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                    }
                }
            }
        }
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

        public void flush() {
            writer.flush();
        }
    }
}
