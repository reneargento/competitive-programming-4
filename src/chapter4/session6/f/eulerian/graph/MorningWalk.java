package chapter4.session6.f.eulerian.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/08/24.
 */
@SuppressWarnings("unchecked")
public class MorningWalk {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int intersections = Integer.parseInt(data[0]);
            int roads = Integer.parseInt(data[1]);

            List<Integer>[] adjacencyList = new List[intersections];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < roads; i++) {
                int intersectionId1 = FastReader.nextInt();
                int intersectionId2 = FastReader.nextInt();

                adjacencyList[intersectionId1].add(intersectionId2);
                adjacencyList[intersectionId2].add(intersectionId1);
            }

            String isPossible = isPossible(adjacencyList, roads);
            outputWriter.printLine(isPossible);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String isPossible(List<Integer>[] adjacencyList, int roads) {
        if (roads == 0) {
            return "Not Possible";
        }
        Deque<Integer> eulerCycle = getEulerCycle(adjacencyList);
        if (eulerCycle != null) {
            return "Possible";
        } else {
            return "Not Possible";
        }
    }

    private static class Edge {
        int vertex1;
        int vertex2;
        boolean isUsed;

        Edge(int vertex1, int vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            isUsed = false;
        }

        public int otherVertex(int vertex) {
            if (vertex == vertex1) {
                return vertex2;
            } else {
                return vertex1;
            }
        }
    }

    private static Deque<Integer> getEulerCycle(List<Integer>[] adjacent) {
        int edges = 0;
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (!adjacent[vertex].isEmpty()) {
                edges += adjacent[vertex].size();
            }
        }
        edges /= 2;

        // Necessary condition: all vertices have even degree
        // (this test is needed or it might find an Eulerian path instead of an Eulerian cycle)
        // An Eulerian path has exactly 2 vertices with odd degrees
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (adjacent[vertex].size() % 2 != 0) {
                return null;
            }
        }

        // Create local view of adjacency lists, to iterate one vertex at a time
        Queue<Edge>[] adjacentCopy = (Queue<Edge>[]) new Queue[adjacent.length];
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            adjacentCopy[vertex] = new LinkedList<>();
        }

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            int selfLoops = 0;

            if (adjacent[vertex] != null) {
                for(int neighbor : adjacent[vertex]) {
                    // Handle self-loops
                    if (vertex == neighbor) {
                        if (selfLoops % 2 == 0) {
                            Edge edge = new Edge(vertex, neighbor);
                            adjacentCopy[vertex].offer(edge);
                            adjacentCopy[neighbor].offer(edge);
                        }

                        selfLoops++;
                    } else {
                        if (vertex < neighbor) {
                            Edge edge = new Edge(vertex, neighbor);
                            adjacentCopy[vertex].offer(edge);
                            adjacentCopy[neighbor].offer(edge);
                        }
                    }
                }
            }
        }

        // Start the cycle with a non-isolated vertex
        int nonIsolatedVertex = nonIsolatedVertex(adjacent);
        Deque<Integer> dfsStack = new ArrayDeque<>();
        dfsStack.push(nonIsolatedVertex);

        Deque<Integer> eulerCycle = new ArrayDeque<>();

        while (!dfsStack.isEmpty()) {
            int vertex = dfsStack.pop();

            while (!adjacentCopy[vertex].isEmpty()) {
                Edge edge = adjacentCopy[vertex].poll();
                if (edge.isUsed) {
                    continue;
                }
                edge.isUsed = true;

                dfsStack.push(vertex);
                vertex = edge.otherVertex(vertex);
            }

            // Push vertex with no more leaving edges to the Euler cycle
            eulerCycle.push(vertex);
        }

        // For each edge visited, we visited a vertex. Add 1 because the first and last vertices are the same.
        if (eulerCycle.size() == edges + 1) {
            return eulerCycle;
        } else {
            return null;
        }
    }

    private static int nonIsolatedVertex(List<Integer>[] adjacent) {
        int nonIsolatedVertex = -1;

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (adjacent[vertex] != null && !adjacent[vertex].isEmpty()) {
                nonIsolatedVertex = vertex;
                break;
            }
        }
        return nonIsolatedVertex;
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
