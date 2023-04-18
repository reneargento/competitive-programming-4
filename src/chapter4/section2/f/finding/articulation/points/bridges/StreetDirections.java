package chapter4.section2.f.finding.articulation.points.bridges;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 15/04/23.
 */
@SuppressWarnings("unchecked")
public class StreetDirections {

    private static class Edge {
        int vertex1;
        int vertex2;

        Edge(int vertex1, int vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            Edge edge = (Edge) other;
            return vertex1 == edge.vertex1 && vertex2 == edge.vertex2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertex1, vertex2);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int intersections = FastReader.nextInt();
        int streets = FastReader.nextInt();
        int caseNumber = 1;

        while (intersections != 0 || streets != 0) {
            List<Integer>[] adjacencyList = new List[intersections];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < streets; i++) {
                int intersectionID1 = FastReader.nextInt() - 1;
                int intersectionID2 = FastReader.nextInt() - 1;
                adjacencyList[intersectionID1].add(intersectionID2);
                adjacencyList[intersectionID2].add(intersectionID1);
            }

            List<Edge> streetDirections = computeStreetDirections(adjacencyList);
            outputWriter.printLine(caseNumber);
            outputWriter.printLine();
            for (Edge street : streetDirections) {
                outputWriter.printLine((street.vertex1 + 1) + " " + (street.vertex2 + 1));
            }
            outputWriter.printLine("#");

            caseNumber++;
            intersections = FastReader.nextInt();
            streets = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Edge> computeStreetDirections(List<Integer>[] adjacencyList) {
        List<Edge> streetDirections = new ArrayList<>();
        Set<Edge> visited = new HashSet<>();

        List<Edge> bridges = Bridges.findBridges(adjacencyList);
        Set<Edge> bridgesSet = new HashSet<>(bridges);

        for (int intersectionID = 0; intersectionID < adjacencyList.length; intersectionID++) {
            computeDirections(adjacencyList, visited, bridgesSet, streetDirections, intersectionID);
        }
        return streetDirections;
    }

    private static void computeDirections(List<Integer>[] adjacencyList, Set<Edge> visited, Set<Edge> bridgesSet,
                                          List<Edge> streetDirections, int intersectionID) {
        for (int neighborID : adjacencyList[intersectionID]) {
            Edge street1 = new Edge(intersectionID, neighborID);
            Edge street2 = new Edge(neighborID, intersectionID);

            if (visited.contains(street1) || visited.contains(street2)) {
                continue;
            }
            visited.add(street1);

            streetDirections.add(street1);
            if (bridgesSet.contains(street1) || bridgesSet.contains(street2)) {
                streetDirections.add(street2);
            }

            computeDirections(adjacencyList, visited, bridgesSet, streetDirections, neighborID);
        }
    }

    private static class Bridges {
        private static int count;
        private static int[] time;
        private static int[] low;

        private static List<Edge> findBridges(List<Integer>[] adjacencyList) {
            low = new int[adjacencyList.length];
            time = new int[adjacencyList.length];
            List<Edge> bridges = new ArrayList<>();

            Arrays.fill(low, -1);
            Arrays.fill(time, -1);

            for (int vertex = 0; vertex < adjacencyList.length; vertex++) {
                if (time[vertex] == -1) {
                    dfs(adjacencyList, vertex, vertex, bridges);
                }
            }
            return bridges;
        }

        private static void dfs(List<Integer>[] adjacencyList, int currentVertex, int sourceVertex, List<Edge> bridges) {
            time[currentVertex] = count;
            low[currentVertex] = count;
            count++;

            for (int neighbor : adjacencyList[currentVertex]) {
                if (time[neighbor] == -1) {
                    dfs(adjacencyList, neighbor, currentVertex, bridges);

                    low[currentVertex] = Math.min(low[currentVertex], low[neighbor]);

                    if (low[neighbor] > time[currentVertex]) {
                        bridges.add(new Edge(currentVertex, neighbor));
                    }
                } else if (neighbor != sourceVertex) {
                    low[currentVertex] = Math.min(low[currentVertex], time[neighbor]);
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
