package chapter4.session6.f.eulerian.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/08/24.
 */
@SuppressWarnings("unchecked")
public class TheNecklace {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int beads = FastReader.nextInt();
            List<Integer>[] adjacent = new List[51];
            for (int i = 0; i < adjacent.length; i++) {
                adjacent[i] = new ArrayList<>();
            }

            for (int i = 0; i < beads; i++) {
                int color1 = FastReader.nextInt();
                int color2 = FastReader.nextInt();
                adjacent[color1].add(color2);
                adjacent[color2].add(color1);
            }

            Deque<Integer> eulerCycle = getEulerCycle(adjacent);

            if (t > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Case #%d", t));
            if (eulerCycle == null) {
                outputWriter.printLine("some beads may be lost");
            } else {
                while (eulerCycle.size() > 1) {
                    int bead1 = eulerCycle.pop();
                    int bead2 = eulerCycle.peek();
                    outputWriter.printLine(String.format("%d %d", bead1, bead2));
                }
            }
        }
        outputWriter.flush();
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
        // A graph with no edges is considered to have an Eulerian cycle
        int edges = 0;
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (adjacent[vertex] != null && !adjacent[vertex].isEmpty()) {
                edges += adjacent[vertex].size();
            }
        }
        edges /= 2;
        if (edges == 0) {
            return new ArrayDeque<>();
        }

        // Necessary condition: all vertices have even degree
        // (this test is needed or it might find an Eulerian path instead of an Eulerian cycle)
        // An Eulerian path has exactly 2 vertices with odd degrees
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (adjacent[vertex] != null && adjacent[vertex].size() % 2 != 0) {
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
