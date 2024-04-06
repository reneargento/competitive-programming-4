package chapter4.section5.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/04/24.
 */
public class CheapestWay {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int maps = FastReader.nextInt();

        for (int m = 1; m <= maps; m++) {
            int stations = FastReader.nextInt();
            Map<String, Integer> stationNameToIDMap = new HashMap<>();
            String[] stationIDToName = new String[stations];
            int[] traversalCosts = new int[stations];
            int[][] costs = new int[stations][stations];
            for (int[] values : costs) {
                Arrays.fill(values, INFINITE);
            }

            for (int s = 0; s < stations; s++) {
                String stationName = FastReader.next();
                int stationID = getStationID(stationNameToIDMap, stationIDToName, stationName);
                int costToPass = FastReader.nextInt();
                traversalCosts[stationID] = costToPass;
            }

            int paths = FastReader.nextInt();
            for (int p = 0; p < paths; p++) {
                int stationID1 = getStationID(stationNameToIDMap, stationIDToName, FastReader.next());
                int stationID2 = getStationID(stationNameToIDMap, stationIDToName, FastReader.next());
                int cost = FastReader.nextInt() * 2;
                costs[stationID1][stationID2] = cost;
                costs[stationID2][stationID1] = cost;
            }
            FloydWarshall floydWarshall = new FloydWarshall(costs, traversalCosts);

            if (m > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine("Map #" + m);
            int queries = FastReader.nextInt();
            for (int q = 1; q <= queries; q++) {
                int stationID1 = getStationID(stationNameToIDMap, stationIDToName, FastReader.next());
                int stationID2 = getStationID(stationNameToIDMap, stationIDToName, FastReader.next());
                int numberOfPassengers = FastReader.nextInt();

                outputWriter.printLine("Query #" + q);
                List<String> path = floydWarshall.path(stationID1, stationID2, stationIDToName);
                outputWriter.print(path.get(0));
                for (int i = 1; i < path.size(); i++) {
                    outputWriter.print(" " + path.get(i));
                }
                outputWriter.printLine();

                double totalCost = traversalCosts[stationID1] + floydWarshall.distance(stationID1, stationID2);
                double passengerCost = (totalCost * 1.1) / numberOfPassengers;
                double formattedPassengerCost = roundValuePrecisionDigits(passengerCost, 2);
                outputWriter.printLine(String.format("Each passenger has to pay : %.2f taka", formattedPassengerCost));
            }
        }
        outputWriter.flush();
    }

    private static int getStationID(Map<String, Integer> stationNameToIDMap, String[] stationIDToName,
                                    String stationName) {
        if (!stationNameToIDMap.containsKey(stationName)) {
            int stationID = stationNameToIDMap.size();
            stationNameToIDMap.put(stationName, stationID);
            stationIDToName[stationID] = stationName;
        }
        return stationNameToIDMap.get(stationName);
    }

    private static double roundValuePrecisionDigits(double value, int digits) {
        long valueToMultiply = (long) Math.pow(10, digits);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
    }

    private static class DirectedEdge {
        private final int vertex1;
        private final int vertex2;
        private final int weight;

        public DirectedEdge(int vertex1, int vertex2, int weight) {
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

    private static class FloydWarshall {
        private final int[][] distances;        // length of shortest v->w path
        private final DirectedEdge[][] edgeTo;  // last edge on shortest v->w path

        public FloydWarshall(int[][] edgeWeightedDigraph, int[] traversalCosts) {
            int vertices = edgeWeightedDigraph.length;
            distances = new int[vertices][vertices];
            edgeTo = new DirectedEdge[vertices][vertices];

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    int distance = edgeWeightedDigraph[vertex1][vertex2] + traversalCosts[vertex2];
                    distances[vertex1][vertex2] = distance;
                    DirectedEdge edge = new DirectedEdge(vertex1, vertex2, distance);
                    edgeTo[vertex1][vertex2] = edge;
                }
            }

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    if (edgeTo[vertex2][vertex1] == null) {
                        continue;  // optimization
                    }

                    for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                        if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                            edgeTo[vertex2][vertex3] = edgeTo[vertex1][vertex3];
                        }
                    }
                }
            }
        }

        public double distance(int source, int target) {
            return distances[source][target];
        }

        public List<String> path(int source, int target, String[] stationIDToName) {
            if (!hasPath(source, target)) {
                return null;
            }

            Deque<Integer> pathStack = new ArrayDeque<>();
            for (DirectedEdge edge = edgeTo[source][target]; edge != null; edge = edgeTo[source][edge.from()]) {
                pathStack.push(edge.to());

                if (edge.from() == source) {
                    break;
                }
            }
            pathStack.push(source);

            List<String> path = new ArrayList<>();
            while (!pathStack.isEmpty()) {
                int stationID = pathStack.poll();
                path.add(stationIDToName[stationID]);
            }
            return path;
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
