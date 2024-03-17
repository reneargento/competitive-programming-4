package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/03/24.
 */
public class NewToBangladesh {

    private static final long INFINITE = 100000000000L;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            int places = FastReader.nextInt();
            int roads = FastReader.nextInt();

            long[][] distances = buildRoadData(places);
            long[][] times = buildRoadData(places);

            for (int r = 0; r < roads; r++) {
                int placeID1 = FastReader.nextInt() - 1;
                int placeID2 = FastReader.nextInt() - 1;
                int time = FastReader.nextInt();
                int length = FastReader.nextInt();

                if (time < times[placeID1][placeID2]) {
                    distances[placeID1][placeID2] = length;
                    distances[placeID2][placeID1] = length;
                    times[placeID1][placeID2] = time;
                    times[placeID2][placeID1] = time;
                } else if (time == times[placeID1][placeID2]
                        && length < distances[placeID1][placeID2]) {
                    distances[placeID1][placeID2] = length;
                    distances[placeID2][placeID1] = length;
                }
            }
            FloydWarshall floydWarshall = new FloydWarshall(distances, times);

            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int source = FastReader.nextInt() - 1;
                int destination = FastReader.nextInt() - 1;

                if (!floydWarshall.hasPath(source, destination)) {
                    outputWriter.printLine("No Path.");
                } else {
                    outputWriter.printLine(String.format("Distance and time to reach destination is %d & %d.",
                            floydWarshall.distance(source, destination), floydWarshall.time(source, destination)));
                }
            }

        }
        outputWriter.flush();
    }

    private static long[][] buildRoadData(int places) {
        long[][] data = new long[places][places];
        for (long[] values : data) {
            Arrays.fill(values, INFINITE);
        }
        for (int i = 0; i < data.length; i++) {
            data[i][i] = 0;
        }
        return data;
    }

    private static class FloydWarshall {
        private final long[][] distances;
        private final long[][] times;

        public FloydWarshall(long[][] distances, long[][] times) {
            this.distances = distances;
            this.times = times;
            int vertices = distances.length;

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                        if (times[vertex2][vertex3] == times[vertex2][vertex1] + times[vertex1][vertex3] &&
                                distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                            times[vertex2][vertex3] = times[vertex2][vertex1] + times[vertex1][vertex3];
                        } else if (times[vertex2][vertex3] > times[vertex2][vertex1] + times[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                            times[vertex2][vertex3] = times[vertex2][vertex1] + times[vertex1][vertex3];
                        }
                    }
                }
            }
        }

        public long distance(int source, int target) {
            return distances[source][target];
        }

        public long time(int source, int target) {
            return times[source][target];
        }

        public boolean hasPath(int source, int target) {
            return distances[source][target] != INFINITE;
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
