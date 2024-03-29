package chapter4.section5.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/03/24.
 */
public class TripRouting {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, Integer> cityNameToIDMap = new HashMap<>();
        String[] cityIDToName = new String[100];
        DirectedEdge[][] edgeWeightedDigraph = new DirectedEdge[100][100];

        String line = FastReader.getLine();
        while (line != null) {
            while (!line.isEmpty()) {
                String[] data = line.split(",");
                int cityID1 = getCityID(cityNameToIDMap, cityIDToName, data[0]);
                int cityID2 = getCityID(cityNameToIDMap, cityIDToName, data[1]);
                String routeName = data[2];
                int routeLength = Integer.parseInt(data[3]);

                if (edgeWeightedDigraph[cityID1][cityID2] == null
                        || routeLength < edgeWeightedDigraph[cityID1][cityID2].weight) {
                    edgeWeightedDigraph[cityID1][cityID2] = new DirectedEdge(cityID1, cityID2, routeLength, routeName);
                    edgeWeightedDigraph[cityID2][cityID1] = new DirectedEdge(cityID2, cityID1, routeLength, routeName);
                }
                line = FastReader.getLine();
            }
            line = FastReader.getLine();
            FloydWarshall floydWarshall = new FloydWarshall(edgeWeightedDigraph);

            while (line != null) {
                String[] data = line.split(",");
                int departureCityID = getCityID(cityNameToIDMap, cityIDToName, data[0]);
                int destinationCityID = getCityID(cityNameToIDMap, cityIDToName, data[1]);

                List<DirectedEdge> path = floydWarshall.path(departureCityID, destinationCityID);
                int totalPathLength = computeTotalLength(path);

                outputWriter.printLine("\n");
                outputWriter.printLine(String.format("%-20s %-20s %-10s %-5s", "From", "To", "Route", "Miles"));
                outputWriter.printLine("-------------------- -------------------- ---------- -----");
                for (DirectedEdge edge : path) {
                    outputWriter.printLine(String.format("%-20s %-20s %-10s %5s",
                            cityIDToName[edge.vertex1],
                            cityIDToName[edge.vertex2],
                            edge.name,
                            edge.weight));
                }
                outputWriter.printLine(String.format("%58s", "-----"));
                outputWriter.printLine(String.format("%41s %-10s %5d", "", "Total", totalPathLength));

                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static int computeTotalLength(List<DirectedEdge> path) {
        int totalLength = 0;
        for (DirectedEdge edge : path) {
            totalLength += edge.weight;
        }
        return totalLength;
    }

    private static int getCityID(Map<String, Integer> cityNameToIDMap, String[] cityIDToName, String cityName) {
        if (!cityNameToIDMap.containsKey(cityName)) {
            int cityID = cityNameToIDMap.size();
            cityNameToIDMap.put(cityName, cityID);
            cityIDToName[cityID] = cityName;
        }
        return cityNameToIDMap.get(cityName);
    }

    private static class DirectedEdge {
        private final int vertex1;
        private final int vertex2;
        private final int weight;
        String name;

        public DirectedEdge(int vertex1, int vertex2, int weight, String name) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
            this.name = name;
        }
    }

    private static class FloydWarshall {
        private final int[][] distances;        // length of shortest v->w path
        private final DirectedEdge[][] edgeTo;  // last edge on shortest v->w path

        public FloydWarshall(DirectedEdge[][] edgeWeightedDigraph) {
            int vertices = edgeWeightedDigraph.length;
            distances = new int[vertices][vertices];
            edgeTo = new DirectedEdge[vertices][vertices];

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    if (edgeWeightedDigraph[vertex1][vertex2] == null) {
                        distances[vertex1][vertex2] = INFINITE;
                    } else {
                        distances[vertex1][vertex2] = edgeWeightedDigraph[vertex1][vertex2].weight;
                        edgeTo[vertex1][vertex2] = edgeWeightedDigraph[vertex1][vertex2];
                    }
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

        public List<DirectedEdge> path(int source, int target) {
            Deque<DirectedEdge> pathStack = new ArrayDeque<>();
            for (DirectedEdge edge = edgeTo[source][target]; edge != null; edge = edgeTo[source][edge.vertex1]) {
                pathStack.push(edge);

                if (edge.vertex1 == source) {
                    break;
                }
            }

            List<DirectedEdge> path = new ArrayList<>();
            while (!pathStack.isEmpty()) {
                path.add(pathStack.poll());
            }
            return path;
        }
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
