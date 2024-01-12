package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/01/24.
 */
public class RouteFinding {

    private static class Station {
        int id;
        String name;
        Set<Edge> adjacent;

        public Station(int id, String name) {
            this.id = id;
            this.name = name;
            adjacent = new HashSet<>();
        }
    }

    private static class Edge {
        private final int vertex1;
        private final int vertex2;
        private final int time;

        public Edge(int vertex1, int vertex2, int time) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return vertex1 == edge.vertex1 && vertex2 == edge.vertex2 && time == edge.time;
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertex1, vertex2, time);
        }
    }

    private static final int MAX_STATIONS = 676;
    private static final int CONNECTION_TIME = 3;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int routes = FastReader.nextInt();
        Map<String, Integer> stationNameToIDMap = new HashMap<>();
        Station[] idToStationMap = new Station[MAX_STATIONS];

        for (int r = 0; r < routes; r++) {
            String route = FastReader.getLine();
            String routeName = String.valueOf(route.charAt(0));

            for (int i = 2; i < route.length(); i++) {
                String stationName = routeName + route.charAt(i);
                int stationID = getStationID(stationName, idToStationMap, stationNameToIDMap);

                if (i != route.length() - 1) {
                    if (route.charAt(i + 1) != '=') {
                        connectStations(idToStationMap, stationNameToIDMap, routeName, route, i, stationID);
                    } else {
                        List<String> connections = new ArrayList<>();
                        connections.add(stationName);
                        while (i != route.length() - 1 && route.charAt(i + 1) == '=') {
                            String nextStationName = String.valueOf(route.charAt(i + 2)) + route.charAt(i + 3);
                            connections.add(nextStationName);
                            i += 3;
                        }
                        addConnections(idToStationMap, stationNameToIDMap, connections);
                        if (i != route.length() - 1) {
                            connectStations(idToStationMap, stationNameToIDMap, routeName, route, i, stationID);
                        }
                    }
                }
            }
        }

        String line = FastReader.getLine();
        while (!line.equals("#")) {
            String stationName1 = line.substring(0, 2);
            String stationName2 = line.substring(2);
            int stationID1 = stationNameToIDMap.get(stationName1);
            int stationID2 = stationNameToIDMap.get(stationName2);

            Dijkstra dijkstra = new Dijkstra(idToStationMap, stationID1, stationID2);
            int time = dijkstra.distTo[stationID2];
            String path = dijkstra.pathTo(idToStationMap, stationID2);
            outputWriter.printLine(String.format("%3d: %s", time, path));

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void connectStations(Station[] idToStationMap, Map<String, Integer> stationNameToIDMap,
                                        String routeName, String route, int index, int stationID) {
        String nextStationName = routeName + route.charAt(index + 1);
        int nextStationID = getStationID(nextStationName, idToStationMap, stationNameToIDMap);
        idToStationMap[stationID].adjacent.add(new Edge(stationID, nextStationID, 1));
        idToStationMap[nextStationID].adjacent.add(new Edge(nextStationID, stationID, 1));
    }

    private static void addConnections(Station[] idToStationMap, Map<String, Integer> stationNameToIDMap,
                                       List<String> connections) {
        for (int i = 0; i < connections.size(); i++) {
            String stationName = connections.get(i);
            int stationID = getStationID(stationName, idToStationMap, stationNameToIDMap);

            for (int j = i + 1; j < connections.size(); j++) {
                String nextStationName = connections.get(j);
                int nextStationID = getStationID(nextStationName, idToStationMap, stationNameToIDMap);

                idToStationMap[stationID].adjacent.add(new Edge(stationID, nextStationID, CONNECTION_TIME));
                idToStationMap[nextStationID].adjacent.add(new Edge(nextStationID, stationID, CONNECTION_TIME));

                addIndirectConnections(idToStationMap, stationID, nextStationID);
                addIndirectConnections(idToStationMap, nextStationID, stationID);
            }
        }
    }

    private static int getStationID(String stationName, Station[] idToStationMap,
                                    Map<String, Integer> stationNameToIDMap) {
        if (!stationNameToIDMap.containsKey(stationName)) {
            int stationID = stationNameToIDMap.size();
            stationNameToIDMap.put(stationName, stationID);
            idToStationMap[stationID] = new Station(stationID, stationName);
        }
        return stationNameToIDMap.get(stationName);
    }

    private static void addIndirectConnections(Station[] idToStationMap, int stationID, int stationID2) {
        for (Edge edge : idToStationMap[stationID2].adjacent) {
            if (edge.time == CONNECTION_TIME && edge.vertex2 != stationID) {
                int otherStationID = edge.vertex2;
                idToStationMap[stationID].adjacent.add(new Edge(stationID, otherStationID, CONNECTION_TIME));
                idToStationMap[otherStationID].adjacent.add(new Edge(otherStationID, stationID, CONNECTION_TIME));
            }
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            long distance;

            public Vertex(int id, long distance) {
                this.id = id;
                this.distance = distance;
            }

            @Override
            public int compareTo(Vertex other) {
                return Long.compare(distance, other.distance);
            }
        }

        private final Edge[] edgeTo;
        private final int[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;

        public Dijkstra(Station[] idToStationMap, int source, int target) {
            edgeTo = new Edge[idToStationMap.length];
            distTo = new int[idToStationMap.length];
            priorityQueue = new PriorityQueue<>(idToStationMap.length);

            Arrays.fill(distTo, Integer.MAX_VALUE);
            distTo[source] = 0;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == target) {
                    break;
                }
                relax(idToStationMap, vertex);
            }
        }

        private void relax(Station[] idToStationMap, Vertex vertex) {
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : idToStationMap[vertex.id].adjacent) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.time) {
                    distTo[neighbor] = distTo[vertex.id] + edge.time;
                    edgeTo[neighbor] = edge;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                }
            }
        }

        public String pathTo(Station[] idToStationMap, int stationID) {
            StringBuilder path = new StringBuilder();
            Edge firstEdge = null;

            for (Edge edge = edgeTo[stationID]; edge != null; edge = edgeTo[edge.vertex1]) {
                Station station = idToStationMap[edge.vertex2];

                if (edge.time == CONNECTION_TIME) {
                    path.append(station.name.charAt(1)).append(station.name.charAt(0)).append("=");
                } else {
                    path.append(station.name.charAt(1));
                }
                firstEdge = edge;
            }
            Station firstStation = idToStationMap[firstEdge.vertex1];
            path.append(firstStation.name.charAt(1)).append(firstStation.name.charAt(0));

            path = path.reverse();
            return path.toString();
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
