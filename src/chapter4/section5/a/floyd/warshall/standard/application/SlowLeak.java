package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 19/03/24.
 */
public class SlowLeak {

    private static final long INFINITE = 100000000000000L;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int intersections = FastReader.nextInt();
        int roads = FastReader.nextInt();
        int[] repairStations = new int[FastReader.nextInt() + 2];
        int maxDistance = FastReader.nextInt();

        long[][] distances = new long[intersections][intersections];
        for (int intersectionID = 0; intersectionID < distances.length; intersectionID++) {
            Arrays.fill(distances[intersectionID], INFINITE);
            distances[intersectionID][intersectionID] = 0;
        }

        repairStations[0] = 0;
        repairStations[1] = intersections - 1;
        for (int i = 2; i < repairStations.length; i++) {
            repairStations[i] = FastReader.nextInt() - 1;
        }
        for (int m = 0; m < roads; m++) {
            int intersectionID1 = FastReader.nextInt() - 1;
            int intersectionID2 = FastReader.nextInt() - 1;
            int length = FastReader.nextInt();
            if (length <= maxDistance) {
                distances[intersectionID1][intersectionID2] = length;
                distances[intersectionID2][intersectionID1] = length;
            }
        }

        long minimumTravelDistance = computeMinimumTravelDistance(distances, repairStations, maxDistance);
        if (minimumTravelDistance == INFINITE) {
            outputWriter.printLine("stuck");
        } else {
            outputWriter.printLine(minimumTravelDistance);
        }
        outputWriter.flush();
    }

    private static long computeMinimumTravelDistance(long[][] distances, int[] repairStations, int maxDistance) {
        long[][] distancesOnlyStations = new long[distances.length][distances.length];
        for (int intersectionID = 0; intersectionID < distancesOnlyStations.length; intersectionID++) {
            Arrays.fill(distancesOnlyStations[intersectionID], INFINITE);
            distancesOnlyStations[intersectionID][intersectionID] = 0;
        }

        floydWarshall(distances);

        for (int intersectionID1 : repairStations) {
            for (int intersectionID2 : repairStations) {
                if (distances[intersectionID1][intersectionID2] <= maxDistance) {
                    distancesOnlyStations[intersectionID1][intersectionID2] = distances[intersectionID1][intersectionID2];
                }
            }
        }
        floydWarshall(distancesOnlyStations);

        return distancesOnlyStations[0][distancesOnlyStations.length - 1];
    }

    private static void floydWarshall(long[][] distances) {
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
