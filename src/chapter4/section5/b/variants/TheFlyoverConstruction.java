package chapter4.section5.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/03/24.
 */
public class TheFlyoverConstruction {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int places = Integer.parseInt(data[0]);
            int roads = Integer.parseInt(data[1]);
            List<Edge> edges = new ArrayList<>();

            int[][] distances = new int[places][places];
            for (int placeID = 0; placeID < distances.length; placeID++) {
                Arrays.fill(distances[placeID], INFINITE);
                distances[placeID][placeID] = 0;
            }

            for (int i = 0; i < roads; i++) {
                int placeID1 = FastReader.nextInt() - 1;
                int placeID2 = FastReader.nextInt() - 1;
                int cost = FastReader.nextInt();

                int minPlaceIndex = Math.min(placeID1, placeID2);
                int maxPlaceIndex = Math.max(placeID1, placeID2);
                distances[placeID1][placeID2] = cost;
                distances[placeID2][placeID1] = cost;
                edges.add(new Edge(minPlaceIndex, maxPlaceIndex, cost));
            }

            List<Integer> maxRoadsUsed = getMaxRoadsUsed(distances, edges);
            outputWriter.print(maxRoadsUsed.get(0));
            for (int i = 1; i < maxRoadsUsed.size(); i++) {
                outputWriter.print(" " + maxRoadsUsed.get(i));
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> getMaxRoadsUsed(int[][] distances, List<Edge> edges) {
        int[] roadFrequency = new int[edges.size()];
        int maxFrequency = 0;
        floydWarshall(distances);

        for (int placeID1 = 0; placeID1 < distances.length; placeID1++) {
            for (int placeID2 = 0; placeID2 < distances.length; placeID2++) {
                for (int edgeIndex = 0; edgeIndex < edges.size(); edgeIndex++) {
                    Edge edge = edges.get(edgeIndex);
                    if (distances[placeID1][placeID2] == distances[placeID1][edge.from()] + edge.weight
                            + distances[edge.to()][placeID2]) {
                        roadFrequency[edgeIndex]++;
                        maxFrequency = Math.max(maxFrequency, roadFrequency[edgeIndex]);
                    }
                }
            }
        }
        return getMaxRoadsUsed(roadFrequency, maxFrequency);
    }

    private static List<Integer> getMaxRoadsUsed(int[] roadFrequency, int maxFrequency) {
        List<Integer> maxRoadsUsed = new ArrayList<>();
        for (int roadIndex = 0; roadIndex < roadFrequency.length; roadIndex++) {
            if (roadFrequency[roadIndex] == maxFrequency) {
                maxRoadsUsed.add(roadIndex + 1);
            }
        }
        return maxRoadsUsed;
    }

    private static class Edge {
        private final int vertex1;
        private final int vertex2;
        private final int weight;

        public Edge(int vertex1, int vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        public int weight() {
            return weight;
        }

        public int from() {
            return vertex1;
        }

        public int to() {
            return vertex2;
        }
    }

    private static void floydWarshall(int[][] distances) {
        for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < distances.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < distances.length; vertex3++) {
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
