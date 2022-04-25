package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/04/22.
 */
// A more optimized but less readable version of Race.
// Useful for large input with 10^5+ nodes.
@SuppressWarnings("unchecked")
public class RaceLargeInput implements Runnable {

    private static class Edge {
        int neighbor;
        int length;

        public Edge(int neighbor, int length) {
            this.neighbor = neighbor;
            this.length = length;
        }
    }

    private static class LengthAndNumberOfEdges {
        int length;
        int numberOfEdges;

        public LengthAndNumberOfEdges(int length, int numberOfEdges) {
            this.length = length;
            this.numberOfEdges = numberOfEdges;
        }
    }

    private static ArrayDeque[] adjacencyList;
    private static boolean[] deleted;
    private static int[] subtreeSizes;
    private static int[] lengthToEdges;
    private static int[] lengthByIteration;
    private static ArrayDeque<LengthAndNumberOfEdges> entriesToAdd;
    private static int iteration;
    private static int bestPathNumberOfEdges;
    private static final int MAX_VALUE = Integer.MAX_VALUE / 2;
    private static int lengthRequired;

    // Using a thread to be able to allocate more memory and avoid a stack overflow when dealing with
    // more than 10^5 vertices
    public static void main(String[] args) throws Exception {
        new Thread(null, new RaceLargeInput(), "ThreadName", (1L << 27)).start();
    }

    public void run() {
        try {
            solve();
        }
        catch (Exception e) {}
    }

    public static void solve() {
        InputReader inputReader = new InputReader(System.in);
        int cities = inputReader.readInt();
        lengthRequired = inputReader.readInt();

        adjacencyList = new ArrayDeque[cities];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayDeque<>();
        }

        for (int i = 0; i < cities - 1; i++) {
            int city1 = inputReader.readInt();
            int city2 = inputReader.readInt();
            int highwayLength = inputReader.readInt();
            adjacencyList[city1].add(new Edge(city2, highwayLength));
            adjacencyList[city2].add(new Edge(city1, highwayLength));
        }
        subtreeSizes = new int[cities];
        deleted = new boolean[cities];
        lengthToEdges = new int[lengthRequired + 1];
        lengthByIteration = new int[lengthRequired + 1];
        entriesToAdd = new ArrayDeque<>();
        bestPathNumberOfEdges = MAX_VALUE;

        centroidDecomposition(0);
        if (bestPathNumberOfEdges == MAX_VALUE) {
            bestPathNumberOfEdges = -1;
        }
        System.out.println(bestPathNumberOfEdges);
    }

    private static void centroidDecomposition(int vertex) {
        computeSubtreeSizes(vertex, -1);
        int centroid = dfsToGetCentroid(subtreeSizes[vertex], vertex, -1);
        iteration++;

        lengthByIteration[0] = iteration;
        lengthToEdges[0] = 0;
        dfsToGetPath(centroid, -1, 0, 0);

        deleted[centroid] = true;
        for (Edge edge : (ArrayDeque<Edge>) adjacencyList[centroid]) {
            if (!deleted[edge.neighbor]) {
                centroidDecomposition(edge.neighbor);
            }
        }
    }

    private static int computeSubtreeSizes(int vertex, int parent) {
        int size = 1;
        for (Edge edge : (ArrayDeque<Edge>) adjacencyList[vertex]) {
            if (edge.neighbor != parent && !deleted[edge.neighbor]) {
                size += computeSubtreeSizes(edge.neighbor, vertex);
            }
        }
        return subtreeSizes[vertex] = size;
    }

    private static int dfsToGetCentroid(int totalSize, int vertex, int parent) {
        for (Edge edge : (ArrayDeque<Edge>) adjacencyList[vertex]) {
            if (edge.neighbor != parent && !deleted[edge.neighbor] && subtreeSizes[edge.neighbor] * 2 > totalSize) {
                return dfsToGetCentroid(totalSize, edge.neighbor, vertex);
            }
        }
        return vertex;
    }

    private static void dfsToGetPath(int vertex, int parent, int currentLength, int currentNumberOfEdges) {
        entriesToAdd.offer(new LengthAndNumberOfEdges(currentLength, currentNumberOfEdges));

        int lengthRemaining = lengthRequired - currentLength;
        if (lengthByIteration[lengthRemaining] != iteration) {
            lengthByIteration[lengthRemaining] = iteration;
            lengthToEdges[lengthRemaining] = MAX_VALUE;
        }
        int numberOfEdges = currentNumberOfEdges + lengthToEdges[lengthRemaining];
        bestPathNumberOfEdges = Math.min(bestPathNumberOfEdges, numberOfEdges);

        for (Edge edge : (ArrayDeque<Edge>) adjacencyList[vertex]) {
            if (edge.neighbor != parent && !deleted[edge.neighbor]
                    && currentLength + edge.length <= lengthRequired) {
                dfsToGetPath(edge.neighbor, vertex, currentLength + edge.length, currentNumberOfEdges + 1);
            }

            if (parent == -1) {
                for (LengthAndNumberOfEdges lengthAndNumberOfEdges : entriesToAdd) {
                    int length = lengthAndNumberOfEdges.length;
                    int edges = lengthAndNumberOfEdges.numberOfEdges;
                    if (lengthByIteration[length] != iteration
                            || lengthToEdges[length] > edges) {
                        lengthByIteration[length] = iteration;
                        lengthToEdges[length] = edges;
                    }
                }
                entriesToAdd.clear();
            }
        }
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }
    }
}
