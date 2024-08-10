package chapter4.session6.e.bipartite.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/07/24.
 */
@SuppressWarnings("unchecked")
public class BookClub {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int people = FastReader.nextInt();
        Graph graph = new Graph(people * 2);

        int declarationOfInterest = FastReader.nextInt();

        for (int i = 0; i < declarationOfInterest; i++) {
            int memberA = FastReader.nextInt();
            int bookId = people + FastReader.nextInt();
            graph.addEdge(memberA, bookId);
        }

        HopcroftKarp hopcroftKarp = new HopcroftKarp(graph);
        outputWriter.printLine(hopcroftKarp.isPerfect() ? "YES" : "NO");
        outputWriter.flush();
    }

    private static class HopcroftKarp {
        private static final int UNMATCHED = -1;

        private final int vertices;          // number of vertices in the graph
        private BipartiteX bipartition;      // the bipartition
        private int cardinality;             // cardinality of current matching
        private int[] mate;                  // mate[v] =  w if v-w is an edge in current matching
                                             //         = -1 if v is not in current matching
        private boolean[] inMinVertexCover;  // inMinVertexCover[v] = true iff v is in min vertex cover
        private boolean[] marked;            // marked[v] = true iff v is reachable via alternating path
        private int[] distTo;                // distTo[v] = number of edges on shortest path to v

        /**
         * Determines a maximum matching (and a minimum vertex cover) in a bipartite graph.
         *
         * @param graph the bipartite graph
         * @throws IllegalArgumentException if {@code graph} is not bipartite
         */
        public HopcroftKarp(Graph graph) {
            bipartition = new BipartiteX(graph);
            if (!bipartition.isBipartite()) {
                throw new IllegalArgumentException("graph is not bipartite");
            }

            // Initialize empty matching
            this.vertices = graph.vertices();
            mate = new int[vertices];
            Arrays.fill(mate, UNMATCHED);

            // The call to hasAugmentingPath() provides enough information to reconstruct level graph
            while (hasAugmentingPath(graph)) {

                // to be able to iterate over each adjacency list, keeping track of which
                // vertex in each adjacency list needs to be explored next
                Iterator<Integer>[] adjacent = (Iterator<Integer>[]) new Iterator[graph.vertices()];
                for (int vertex = 0; vertex < graph.vertices(); vertex++) {
                    adjacent[vertex] = graph.adjacent(vertex).iterator();
                }

                // For each unmatched vertex source on one side of bipartition
                for (int source = 0; source < vertices; source++) {
                    if (isMatched(source) || !bipartition.color(source)) {
                        continue;   // or use distTo[source] == 0
                    }

                    // Find augmenting path from source using nonrecursive DFS
                    Deque<Integer> path = new ArrayDeque<>();
                    path.push(source);
                    while (!path.isEmpty()) {
                        int vertex = path.peek();

                        // Retreat, no more edges in level graph leaving vertex
                        if (!adjacent[vertex].hasNext()) {
                            path.pop();
                        } else { // Advance
                            // Process edge vertex-w only if it is an edge in level graph
                            int w = adjacent[vertex].next();
                            if (!isLevelGraphEdge(vertex, w)) {
                                continue;
                            }

                            // Add w to augmenting path
                            path.push(w);

                            // Augmenting path found: update the matching
                            if (!isMatched(w)) {
                                // System.out.println("augmenting path: " + toString(path));

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

            // Also find a min vertex cover
            inMinVertexCover = new boolean[vertices];
            for (int vertex = 0; vertex < vertices; vertex++) {
                if (bipartition.color(vertex) && !marked[vertex]) {
                    inMinVertexCover[vertex] = true;
                }
                if (!bipartition.color(vertex) && marked[vertex]) {
                    inMinVertexCover[vertex] = true;
                }
            }
        }

        // Is the edge vertex1-vertex2 in the level graph?
        private boolean isLevelGraphEdge(int vertex1, int vertex2) {
            return (distTo[vertex2] == distTo[vertex1] + 1) && isResidualGraphEdge(vertex1, vertex2);
        }

        // Is the edge vertex1-vertex2 a forward edge not in the matching or a reverse edge in the matching?
        private boolean isResidualGraphEdge(int vertex1, int vertex2) {
            if ((mate[vertex1] != vertex2) && bipartition.color(vertex1)) {
                return true;
            }
            if ((mate[vertex1] == vertex2) && !bipartition.color(vertex1)) {
                return true;
            }
            return false;
        }

        /*
         * Is there an augmenting path?
         *   - if so, upon termination adj[] contains the level graph;
         *   - if not, upon termination marked[] specifies those vertices reachable via an alternating
         *     path from one side of the bipartition
         *
         * An alternating path is a path whose edges belong alternately to the matching and not
         * to the matching.
         *
         * An augmenting path is an alternating path that starts and ends at unmatched vertices.
         */
        private boolean hasAugmentingPath(Graph graph) {

            // Shortest path distances
            marked = new boolean[vertices];
            distTo = new int[vertices];
            Arrays.fill(distTo, Integer.MAX_VALUE);

            // Breadth-first search (starting from all unmatched vertices on one side of bipartition)
            Queue<Integer> queue = new LinkedList<>();
            for (int vertex = 0; vertex < vertices; vertex++) {
                if (bipartition.color(vertex) && !isMatched(vertex)) {
                    queue.offer(vertex);
                    marked[vertex] = true;
                    distTo[vertex] = 0;
                }
            }

            // Run BFS until an augmenting path is found
            // (and keep going until all vertices at that distance are explored)
            boolean hasAugmentingPath = false;

            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                for (int neighbor : graph.adjacent(vertex)) {

                    // Forward edge not in matching or backwards edge in matching
                    if (isResidualGraphEdge(vertex, neighbor)) {
                        if (!marked[neighbor]) {
                            distTo[neighbor] = distTo[vertex] + 1;
                            marked[neighbor] = true;
                            if (!isMatched(neighbor)) {
                                hasAugmentingPath = true;
                            }

                            // Stop enqueuing vertices once an alternating path has been discovered
                            // (no vertex on same side will be marked if its shortest path distance longer)
                            if (!hasAugmentingPath) {
                                queue.offer(neighbor);
                            }
                        }
                    }
                }
            }
            return hasAugmentingPath;
        }

        /**
         * Returns the vertex to which the specified vertex is matched in
         * the maximum matching computed by the algorithm.
         *
         * @param v the vertex
         * @return the vertex to which vertex {@code v} is matched in the
         * maximum matching; {@code -1} if the vertex is not matched
         * @throws IllegalArgumentException unless {@code 0 <= v < vertices}
         */
        public int mate(int v) {
            return mate[v];
        }

        /**
         * Returns true if the specified vertex is matched in the maximum matching
         * computed by the algorithm.
         *
         * @param v the vertex
         * @return {@code true} if vertex {@code v} is matched in maximum matching;
         * {@code false} otherwise
         * @throws IllegalArgumentException unless {@code 0 <= v < vertices}
         */
        public boolean isMatched(int v) {
            return mate[v] != UNMATCHED;
        }

        /**
         * Returns the number of edges in any maximum matching.
         *
         * @return the number of edges in any maximum matching
         */
        public int size() {
            return cardinality;
        }

        /**
         * Returns true if the graph contains a perfect matching.
         * That is, the number of edges in a maximum matching is equal to one half
         * of the number of vertices in the graph (so that every vertex is matched).
         *
         * @return {@code true} if the graph contains a perfect matching;
         * {@code false} otherwise
         */
        public boolean isPerfect() {
            return cardinality * 2 == vertices;
        }
    }

    private static class Graph {

        private final int vertices;
        private int edges;
        private List<Integer>[] adjacent;

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

        public List<Integer>[] getAdjacencyList() {
            return adjacent;
        }

        public void updateAdjacencyList(int vertex, List adjacencyList) {
            adjacent[vertex] = adjacencyList;
        }

        public Iterable<Integer> adjacent(int vertex) {
            return adjacent[vertex];
        }

        public int degree(int vertex) {
            return adjacent[vertex].size();
        }
    }

    private static class BipartiteX {

        private static final boolean WHITE = false;

        private boolean isBipartite;   // is the graph bipartite?
        private boolean[] color;       // color[v] gives vertices on one side of bipartition
        private boolean[] marked;      // marked[v] = true if v has been visited in DFS
        private int[] edgeTo;          // edgeTo[v] = last edge on path to v
        private Queue<Integer> cycle;  // odd-length cycle

        /**
         * Determines whether an undirected graph is bipartite and finds either a
         * bipartition or an odd-length cycle.
         *
         * @param  graph the graph
         */
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

                        // to form odd cycle, consider s-vertex path and s-neighbor path
                        // and let x be closest node to vertex and neighbor common to two paths
                        // then (neighbor-x path) + (x-vertex path) + (edge vertex-neighbor) is an odd-length cycle
                        // Note: distTo[vertex] == distTo[neighbor];
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

        /**
         * Returns the side of the bipartite that vertex {@code vertex} is on.
         *
         * @param  vertex the vertex
         * @return the side of the bipartition that vertex {@code vertex} is on; two vertices
         *         are in the same side of the bipartition if and only if they have the
         *         same color
         * @throws IllegalArgumentException unless {@code 0 <= vertex < vertices}
         * @throws UnsupportedOperationException if this method is called when the graph
         *         is not bipartite
         */
        public boolean color(int vertex) {
            validateVertex(vertex);
            if (!isBipartite) {
                throw new UnsupportedOperationException("Graph is not bipartite");
            }
            return color[vertex];
        }

        // throw an IllegalArgumentException unless {@code 0 <= vertex < vertices}
        private void validateVertex(int vertex) {
            int vertices = marked.length;
            if (vertex < 0 || vertex >= vertices) {
                throw new IllegalArgumentException("vertex " + vertex + " is not between 0 and " + (vertices-1));
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
