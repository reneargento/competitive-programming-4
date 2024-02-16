package chapter4.section4.f.on.small.graph.with.negative.cycle.bellman.ford;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/01/24.
 */
public class HauntedGraveyard {

    private static class Cell {
        int row;
        int column;
        boolean isGravestone;
        Cell hauntedHoleDestination;
        int secondsDifference;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class Result {
        String status;
        long minimumTimeToCross;

        public Result(String status, long minimumTimeToCross) {
            this.status = status;
            this.minimumTimeToCross = minimumTimeToCross;
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };
    private static final String POSSIBLE = "Possible";
    private static final String IMPOSSIBLE = "Impossible";
    private static final String NEVER = "Never";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int width = FastReader.nextInt();
        int height = FastReader.nextInt();

        while (width != 0 || height != 0) {
            Cell[][] graveyard = new Cell[height][width];
            for (int row = 0; row < graveyard.length; row++) {
                for (int column = 0; column < graveyard[0].length; column++) {
                    graveyard[row][column] = new Cell(row, column);
                }
            }

            int gravestones = FastReader.nextInt();
            for (int i = 0; i < gravestones; i++) {
                int column = FastReader.nextInt();
                int row = FastReader.nextInt();
                graveyard[row][column].isGravestone = true;
            }

            int hauntedHoles = FastReader.nextInt();
            for (int i = 0; i < hauntedHoles; i++) {
                int startColumn = FastReader.nextInt();
                int startRow = FastReader.nextInt();
                int destinationColumn = FastReader.nextInt();
                int destinationRow = FastReader.nextInt();
                int secondsDifference = FastReader.nextInt();

                Cell destination = new Cell(destinationRow, destinationColumn);
                destination.secondsDifference = secondsDifference;
                graveyard[startRow][startColumn].hauntedHoleDestination = destination;
            }

            Result result = crossGraveyard(graveyard);
            if (result.status.equals(POSSIBLE)) {
                outputWriter.printLine(result.minimumTimeToCross);
            } else {
                outputWriter.printLine(result.status);
            }
            width = FastReader.nextInt();
            height = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result crossGraveyard(Cell[][] graveyard) {
        int sourceID = getVertexID(graveyard, 0, 0);
        int exitID = getVertexID(graveyard, graveyard.length - 1, graveyard[0].length - 1);
        EdgeWeightedDigraph edgeWeightedDigraph = buildGraph(graveyard, exitID);
        BellmanFord bellmanFord = new BellmanFord(edgeWeightedDigraph, sourceID);
        String status;
        long minimumTimeToCross = -1;

        if (bellmanFord.hasNegativeCycle()) {
            status = NEVER;
        } else if (bellmanFord.hasPathTo(exitID)) {
            status = POSSIBLE;
            minimumTimeToCross = bellmanFord.distTo(exitID);
        } else {
            status = IMPOSSIBLE;
        }
        return new Result(status, minimumTimeToCross);
    }

    private static EdgeWeightedDigraph buildGraph(Cell[][] graveyard, int exitID) {
        int vertices = graveyard.length * graveyard[0].length;
        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(vertices);

        for (int row = 0; row < graveyard.length; row++) {
            for (int column = 0; column < graveyard[0].length; column++) {
                if (graveyard[row][column].isGravestone) {
                    continue;
                }
                int vertexID = getVertexID(graveyard, row, column);
                if (vertexID == exitID) {
                    break;
                }

                if (graveyard[row][column].hauntedHoleDestination != null) {
                    Cell destination = graveyard[row][column].hauntedHoleDestination;
                    int destinationID = getVertexID(graveyard, destination.row, destination.column);
                    int secondsDifference = graveyard[row][column].hauntedHoleDestination.secondsDifference;
                    edgeWeightedDigraph.addEdge(new DirectedEdge(vertexID, destinationID, secondsDifference));
                } else {
                    for (int i = 0; i < neighborRows.length; i++) {
                        int neighborRow = row + neighborRows[i];
                        int neighborColumn = column + neighborColumns[i];
                        if (isValid(graveyard, neighborRow, neighborColumn)
                                && !graveyard[neighborRow][neighborColumn].isGravestone) {
                            int neighborID = getVertexID(graveyard, neighborRow, neighborColumn);
                            edgeWeightedDigraph.addEdge(new DirectedEdge(vertexID, neighborID, 1));
                        }
                    }
                }
            }
        }
        return edgeWeightedDigraph;
    }

    private static int getVertexID(Cell[][] graveyard, int row, int column) {
        return graveyard[0].length * row + column;
    }

    private static boolean isValid(Cell[][] graveyard, int row, int column) {
        return row >= 0 && row < graveyard.length && column >= 0 && column < graveyard[0].length;
    }

    private static class DirectedEdge {
        private final int vertex1;
        private final int vertex2;
        private final long weight;

        public DirectedEdge(int vertex1, int vertex2, long weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        public long weight() {
            return weight;
        }

        public int from() {
            return vertex1;
        }

        public int to() {
            return vertex2;
        }
    }

    @SuppressWarnings("unchecked")
    private static class EdgeWeightedDigraph {
        private final int vertices;
        private final List<DirectedEdge>[] adjacent;

        public EdgeWeightedDigraph(int vertices) {
            this.vertices = vertices;
            adjacent = (List<DirectedEdge>[]) new ArrayList[vertices];
            for (int vertex = 0; vertex < vertices; vertex++) {
                adjacent[vertex] = new ArrayList<>();
            }
        }

        public int vertices() {
            return vertices;
        }

        public void addEdge(DirectedEdge edge) {
            adjacent[edge.from()].add(edge);
        }

        public Iterable<DirectedEdge> adjacent(int vertex) {
            return adjacent[vertex];
        }
    }

    private static class BellmanFord {
        private final long[] distTo;           // length of path to vertex
        private final DirectedEdge[] edgeTo;   // last edge on path to vertex
        private final boolean[] onQueue;       // is this vertex on the queue?
        private final Queue<Integer> queue;    // vertices being relaxed
        private int callsToRelax;              // number of calls to relax()
        private Iterable<DirectedEdge> cycle;  // if there is a negative cycle in edgeTo[], return it

        //O(E * V), but typically runs in (E + V)
        public BellmanFord(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
            distTo = new long[edgeWeightedDigraph.vertices()];
            edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
            onQueue = new boolean[edgeWeightedDigraph.vertices()];
            queue = new LinkedList<>();

            for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                distTo[vertex] = Long.MAX_VALUE;
            }

            distTo[source] = 0;
            queue.offer(source);
            onQueue[source] = true;

            // The only vertices that could be relaxed are the ones which had their distance from the source updated
            // on the last pass.
            while (!queue.isEmpty() && !hasNegativeCycle()) {
                int vertex = queue.poll();
                onQueue[vertex] = false;
                relax(edgeWeightedDigraph, vertex);
            }
        }

        private void relax(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.to();

                if (distTo[neighbor] > distTo[vertex] + edge.weight()) {
                    distTo[neighbor] = distTo[vertex] + edge.weight();
                    edgeTo[neighbor] = edge;

                    if (!onQueue[neighbor]) {
                        queue.offer(neighbor);
                        onQueue[neighbor] = true;
                    }
                }

                // Check if there is a negative cycle after every V calls to relax()
                if (callsToRelax++ % edgeWeightedDigraph.vertices() == 0) {
                    findNegativeCycle();
                }
            }
        }

        public long distTo(int vertex) {
            return distTo[vertex];
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] != Long.MAX_VALUE;
        }

        private void findNegativeCycle() {
            int vertices = edgeTo.length;
            EdgeWeightedDigraph shortestPathsTree = new EdgeWeightedDigraph(vertices);

            for (int vertex = 0; vertex < vertices; vertex++) {
                if (edgeTo[vertex] != null) {
                    shortestPathsTree.addEdge(edgeTo[vertex]);
                }
            }

            EdgeWeightedDirectedCycle edgeWeightedCycleFinder = new EdgeWeightedDirectedCycle(shortestPathsTree);
            cycle = edgeWeightedCycleFinder.cycle();
        }

        public boolean hasNegativeCycle() {
            return cycle != null;
        }
    }

    private static class EdgeWeightedDirectedCycle {
        private final boolean[] visited;
        private final DirectedEdge[] edgeTo;
        private Deque<DirectedEdge> cycle;   // vertices on a cycle (if one exists)
        private final boolean[] onStack;     // vertices on recursive call stack

        public EdgeWeightedDirectedCycle(EdgeWeightedDigraph edgeWeightedDigraph) {
            onStack = new boolean[edgeWeightedDigraph.vertices()];
            edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
            visited = new boolean[edgeWeightedDigraph.vertices()];

            for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                if (!visited[vertex]) {
                    dfs(edgeWeightedDigraph, vertex);
                }
            }
        }

        private void dfs(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
            onStack[vertex] = true;
            visited[vertex] = true;

            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.to();

                if (hasCycle()) {
                    return;
                } else if (!visited[neighbor]) {
                    edgeTo[neighbor] = edge;
                    dfs(edgeWeightedDigraph, neighbor);
                } else if (onStack[neighbor]) {
                    cycle = new ArrayDeque<>();

                    DirectedEdge edgeInCycle = edge;

                    while (edgeInCycle.from() != neighbor) {
                        cycle.push(edgeInCycle);
                        edgeInCycle = edgeTo[edgeInCycle.from()];
                    }
                    cycle.push(edgeInCycle);
                    return;
                }
            }
            onStack[vertex] = false;
        }

        public boolean hasCycle() {
            return cycle != null;
        }

        public Iterable<DirectedEdge> cycle() {
            return cycle;
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
