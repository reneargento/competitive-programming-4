package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/03/24.
 */
public class TheOrcAttack {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int locations = FastReader.nextInt();
            int[][] distances = new int[locations][locations];
            for (int[] values : distances) {
                Arrays.fill(values, INFINITE);
            }
            for (int locationID = 0; locationID < distances.length; locationID++) {
                distances[locationID][locationID] = 0;
            }

            int distanceInformation = FastReader.nextInt();

            for (int i = 0; i < distanceInformation; i++) {
                int locationID1 = FastReader.nextInt() - 1;
                int locationID2 = FastReader.nextInt() - 1;
                int cost = FastReader.nextInt();
                if (cost < distances[locationID1][locationID2]) {
                    distances[locationID1][locationID2] = cost;
                    distances[locationID2][locationID1] = cost;
                }
            }

            int minimumDistance = computeMinimumDistance(distances);
            outputWriter.printLine(String.format("Map %d: %d", t, minimumDistance));
        }
        outputWriter.flush();
    }

    private static int computeMinimumDistance(int[][] distances) {
        int minimumDistance = INFINITE;
        floydWarshall(distances);

        for (int locationID = 5; locationID < distances.length; locationID++) {
            if (distances[locationID][0] == distances[locationID][1]
                    && distances[locationID][0] == distances[locationID][2]
                    && distances[locationID][0] == distances[locationID][3]
                    && distances[locationID][0] == distances[locationID][4]) {
                int farthestDistance = 0;
                for (int otherLocationID = 0; otherLocationID < distances.length; otherLocationID++) {
                    if (distances[locationID][otherLocationID] > farthestDistance) {
                        farthestDistance = distances[locationID][otherLocationID];
                    }
                }
                if (farthestDistance < minimumDistance) {
                    minimumDistance = farthestDistance;
                }
            }
        }

        if (minimumDistance == INFINITE) {
            return -1;
        }
        return minimumDistance;
    }

    private static void floydWarshall(int[][] distances) {
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
