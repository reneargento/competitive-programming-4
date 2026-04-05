package chapter5.section7;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/04/26.
 */
public class Linije {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        Graph graph = new Graph(1001);
        Set<Integer> distinctX = new HashSet<>();
        Set<Integer> distinctY = new HashSet<>();

        for (int t = 0; t < tests; t++) {
            int x = FastReader.nextInt() - 1;
            int y = 500 + FastReader.nextInt() - 1;
            distinctX.add(x);
            distinctY.add(y);
            graph.addEdge(x, y);
        }

        int realVertices = distinctX.size() + distinctY.size();
        HopcroftKarp hopcroftKarp = new HopcroftKarp(graph, realVertices);
        if (hopcroftKarp.isPerfect()) {
            outputWriter.printLine("Slavko");
        } else {
            outputWriter.printLine("Mirko");
        }
        outputWriter.flush();
    }

    private static class HopcroftKarp {
        private static final int UNMATCHED = -1;

        private final int vertices;
        private final BipartiteX bipartition;
        private int cardinality;
        private final int[] mate;
        private boolean[] marked;
        private int[] distTo;
        private final int realVertices;

        public HopcroftKarp(Graph graph, int realVertices) {
            bipartition = new BipartiteX(graph);
            if (!bipartition.isBipartite()) {
                throw new IllegalArgumentException("graph is not bipartite");
            }

            this.vertices = graph.vertices();
            this.realVertices = realVertices;
            mate = new int[vertices];
            Arrays.fill(mate, UNMATCHED);

            while (hasAugmentingPath(graph)) {
                Iterator<Integer>[] adjacent = (Iterator<Integer>[]) new Iterator[graph.vertices()];
                for (int vertex = 0; vertex < graph.vertices(); vertex++) {
                    adjacent[vertex] = graph.adjacent(vertex).iterator();
                }

                for (int source = 0; source < vertices; source++) {
                    if (isMatched(source) || !bipartition.color(source)) {
                        continue;
                    }

                    Deque<Integer> path = new ArrayDeque<>();
                    path.push(source);
                    while (!path.isEmpty()) {
                        int vertex = path.peek();

                        if (!adjacent[vertex].hasNext()) {
                            path.pop();
                        } else {
                            int w = adjacent[vertex].next();
                            if (!isLevelGraphEdge(vertex, w)) {
                                continue;
                            }
                            path.push(w);

                            if (!isMatched(w)) {
                                while (!path.isEmpty()) {
                                    int x = path.pop();
                                    int y = path.pop();
                                    mate[x] = y;
                                    mate[y] = x;
                                }
                                cardinality++;
                            }
                        }
                    }
                }
            }
        }

        private boolean isLevelGraphEdge(int vertex1, int vertex2) {
            return (distTo[vertex2] == distTo[vertex1] + 1) && isResidualGraphEdge(vertex1, vertex2);
        }

        private boolean isResidualGraphEdge(int vertex1, int vertex2) {
            if ((mate[vertex1] != vertex2) && bipartition.color(vertex1)) {
                return true;
            }
            return (mate[vertex1] == vertex2) && !bipartition.color(vertex1);
        }

        private boolean hasAugmentingPath(Graph graph) {
            marked = new boolean[vertices];
            distTo = new int[vertices];
            Arrays.fill(distTo, Integer.MAX_VALUE);

            Queue<Integer> queue = new LinkedList<>();
            for (int vertex = 0; vertex < vertices; vertex++) {
                if (bipartition.color(vertex) && !isMatched(vertex)) {
                    queue.offer(vertex);
                    marked[vertex] = true;
                    distTo[vertex] = 0;
                }
            }

            boolean hasAugmentingPath = false;

            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                for (int neighbor : graph.adjacent(vertex)) {

                    if (isResidualGraphEdge(vertex, neighbor)) {
                        if (!marked[neighbor]) {
                            distTo[neighbor] = distTo[vertex] + 1;
                            marked[neighbor] = true;
                            if (!isMatched(neighbor)) {
                                hasAugmentingPath = true;
                            }
                            if (!hasAugmentingPath) {
                                queue.offer(neighbor);
                            }
                        }
                    }
                }
            }
            return hasAugmentingPath;
        }

        private boolean isMatched(int v) {
            return mate[v] != UNMATCHED;
        }

        private boolean isPerfect() {
            return cardinality * 2 == realVertices;
        }
    }

    private static class BipartiteX {
        private static final boolean WHITE = false;
        private boolean isBipartite;
        private final boolean[] color;
        private final boolean[] marked;
        private final int[] edgeTo;
        private Queue<Integer> cycle;

        public BipartiteX(Graph graph) {
            isBipartite = true;
            color  = new boolean[graph.vertices()];
            marked = new boolean[graph.vertices()];
            edgeTo = new int[graph.vertices()];

            for (int vertex = 0; vertex < graph.vertices() && isBipartite; vertex++) {
                if (!marked[vertex]) {
                    bfs(graph, vertex);
                }
            }
        }

        private void bfs(Graph graph, int source) {
            Queue<Integer> queue = new LinkedList<>();
            color[source] = WHITE;
            marked[source] = true;
            queue.offer(source);

            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                for (int neighbor : graph.adjacent(vertex)) {
                    if (!marked[neighbor]) {
                        marked[neighbor] = true;
                        edgeTo[neighbor] = vertex;
                        color[neighbor] = !color[vertex];
                        queue.offer(neighbor);
                    } else if (color[neighbor] == color[vertex]) {
                        isBipartite = false;

                        cycle = new LinkedList<>();
                        Deque<Integer> stack = new ArrayDeque<>();
                        int x = vertex;
                        int y = neighbor;

                        while (x != y) {
                            stack.push(x);
                            cycle.offer(y);
                            x = edgeTo[x];
                            y = edgeTo[y];
                        }

                        stack.push(x);
                        while (!stack.isEmpty()) {
                            cycle.offer(stack.pop());
                        }

                        cycle.offer(neighbor);
                        return;
                    }
                }
            }
        }

        public boolean isBipartite() {
            return isBipartite;
        }

        public boolean color(int vertex) {
            return color[vertex];
        }
    }

    private static class Graph {
        private final int vertices;
        private final List<Integer>[] adjacent;
        private int edges;

        public Graph(int vertices) {
            this.vertices = vertices;
            this.edges = 0;
            adjacent = (List<Integer>[]) new ArrayList[vertices];

            for(int vertex = 0; vertex < vertices; vertex++) {
                adjacent[vertex] = new ArrayList<>();
            }
        }

        public int vertices() {
            return vertices;
        }

        public int edges() {
            return edges;
        }

        public void addEdge(int vertex1, int vertex2) {
            adjacent[vertex1].add(vertex2);
            adjacent[vertex2].add(vertex1);
            edges++;
        }

        public Iterable<Integer> adjacent(int vertex) {
            return adjacent[vertex];
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
