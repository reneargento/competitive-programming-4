package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/01/24.
 */
@SuppressWarnings("unchecked")
public class PassingSecrets {

    private static final int MAX_STUDENTS = 5000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            Map<String, Integer> nameToIDMap = new HashMap<>();
            String[] idToNameMap = new String[MAX_STUDENTS];
            List<Edge>[] adjacencyList = new List[MAX_STUDENTS];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            String[] data = line.split(" ");
            int sourceID = getPersonID(nameToIDMap, idToNameMap, data[0]);
            int destinationID = getPersonID(nameToIDMap, idToNameMap, data[1]);

            int groups = FastReader.nextInt();
            for (int g = 0; g < groups; g++) {
                String[] groupNames = FastReader.getLine().split(" ");
                int risk = groupNames.length - 2;
                for (int i = 0; i < groupNames.length; i++) {
                    int personID1 = getPersonID(nameToIDMap, idToNameMap, groupNames[i]);

                    for (int j = i + 1; j < groupNames.length; j++) {
                        int personID2 = getPersonID(nameToIDMap, idToNameMap, groupNames[j]);
                        adjacencyList[personID1].add(new Edge(personID1, personID2, risk));
                        adjacencyList[personID2].add(new Edge(personID2, personID1, risk));
                    }
                }
            }

            Dijkstra dijkstra = new Dijkstra(adjacencyList, sourceID, destinationID);
            List<String> minimumRiskPath = dijkstra.pathTo(destinationID, idToNameMap);
            if (minimumRiskPath == null) {
                outputWriter.printLine("impossible");
            } else {
                outputWriter.print(dijkstra.distTo[destinationID]);
                for (String name : minimumRiskPath) {
                    outputWriter.print(" " + name);
                }
                outputWriter.printLine();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int getPersonID(Map<String, Integer> nameToIDMap, String[] idToNameMap, String name) {
        if (!nameToIDMap.containsKey(name)) {
            int personID = nameToIDMap.size();
            nameToIDMap.put(name, personID);
            idToNameMap[personID] = name;
        }
        return nameToIDMap.get(name);
    }

    private static class Edge {
        private final int vertex1;
        private final int vertex2;
        private final long distance;

        public Edge(int vertex1, int vertex2, long distance) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.distance = distance;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            long distance;
            int lastPersonID;

            public Vertex(int id, long distance, int lastPersonID) {
                this.id = id;
                this.distance = distance;
                this.lastPersonID = lastPersonID;
            }

            @Override
            public int compareTo(Vertex other) {
                return Long.compare(distance, other.distance);
            }
        }

        private final Edge[] edgeTo;
        private final long[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final long MAX_VALUE = 10000000000000000L;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int target) {
            edgeTo = new Edge[adjacencyList.length];
            distTo = new long[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = 0;
            priorityQueue.offer(new Vertex(source, 0, 0));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == target) {
                    break;
                }
                relax(adjacencyList, vertex, target);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex, int target) {
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;
                int risk = (edge.vertex2 == target) ? 0 : 1;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance + risk) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance + risk;
                    edgeTo[neighbor] = edge;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor], vertex.id));
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] != MAX_VALUE;
        }

        public List<String> pathTo(int vertex, String[] idToNameMap) {
            if (!hasPathTo(vertex)) {
                return null;
            }

            List<String> path = new ArrayList<>();
            for (Edge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.vertex1]) {
                String name = idToNameMap[edge.vertex2];
                path.add(name);

                if (edgeTo[edge.vertex1] == null) {
                    String sourceName = idToNameMap[edge.vertex1];
                    path.add(sourceName);
                }
            }
            Collections.reverse(path);
            return path;
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
