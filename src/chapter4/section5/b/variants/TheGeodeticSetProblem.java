package chapter4.section5.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/03/24.
 */
public class TheGeodeticSetProblem {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int vertices = FastReader.nextInt();
        int[][] distances = new int[vertices][vertices];
        for (int vertexID = 0; vertexID < distances.length; vertexID++) {
            Arrays.fill(distances[vertexID], INFINITE);
            distances[vertexID][vertexID] = 0;
        }

        for (int vertexID1 = 0; vertexID1 < distances.length; vertexID1++) {
            String[] data = FastReader.getLine().split(" ");
            for (int i = 0; i < data.length; i++) {
                int vertexID2 = Integer.parseInt(data[i]) - 1;
                distances[vertexID1][vertexID2] = 1;
            }
        }

        FloydWarshall floydWarshall = new FloydWarshall(distances);
        long geodeticSetBitset = computeGeodeticSetBitset(vertices);
        Long[][] allPaths = floydWarshall.allPathVertices(geodeticSetBitset);
        Map<Long, Boolean> dp = new HashMap<>();

        int tests = FastReader.nextInt();
        for (int t = 0; t < tests; t++) {
            String[] data = FastReader.getLine().split(" ");
            List<Integer> subsetD = new ArrayList<>();

            for (int i = 0; i < data.length; i++) {
                int vertexID = Integer.parseInt(data[i]) - 1;
                subsetD.add(vertexID);
            }
            outputWriter.printLine(isGeodeticSet(allPaths, dp, geodeticSetBitset, subsetD) ? "yes" : "no");
        }
        outputWriter.flush();
    }

    private static long computeGeodeticSetBitset(int vertices) {
        long geodeticSetBitset = 0L;
        for (int vertexID = 0; vertexID < vertices; vertexID++) {
            geodeticSetBitset |= (1L << vertexID);
        }
        return geodeticSetBitset;
    }

    private static boolean isGeodeticSet(Long[][] allPaths, Map<Long, Boolean> dp, long geodeticSetBitset,
                                         List<Integer> subsetD) {
        long hashcode = getHashcode(subsetD);
        if (dp.containsKey(hashcode)) {
            return dp.get(hashcode);
        }

        long verticesInPaths = 0L;

        for (int vertexID1 = 0; vertexID1 < subsetD.size(); vertexID1++) {
            for (int vertexID2 = 0; vertexID2 < subsetD.size(); vertexID2++) {
                int startVertex = subsetD.get(vertexID1);
                int endVertex = subsetD.get(vertexID2);

                verticesInPaths |= allPaths[startVertex][endVertex];

                if (verticesInPaths == geodeticSetBitset) {
                    break;
                }
            }
            if (verticesInPaths == geodeticSetBitset) {
                break;
            }
        }

        boolean isGeodeticSet = verticesInPaths == geodeticSetBitset;
        dp.put(hashcode, isGeodeticSet);
        return isGeodeticSet;
    }

    private static long getHashcode(List<Integer> subsetD) {
        long hashcode = 0;
        for (int vertexID : subsetD) {
            hashcode |= (1L << vertexID);
        }
        return hashcode;
    }

    private static  class DirectedEdge {
        private final int vertex1;
        private final int vertex2;
        private final int weight;

        public DirectedEdge(int vertex1, int vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        public int weight() {
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
    private static class FloydWarshall {
        private final int[][] distances;              // length of shortest v->w path
        private final List<DirectedEdge>[][] edgeTo;  // last edge on shortest v->w path

        public FloydWarshall(int[][] edgeWeightedDigraph) {
            int vertices = edgeWeightedDigraph.length;
            distances = new int[vertices][vertices];
            edgeTo = new List[vertices][vertices];

            for (int vertexID1 = 0; vertexID1 < edgeTo.length; vertexID1++) {
                for (int vertexID2 = 0; vertexID2 < edgeTo.length; vertexID2++) {
                    edgeTo[vertexID1][vertexID2] = new ArrayList<>();
                }
            }

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    int distance = edgeWeightedDigraph[vertex1][vertex2];
                    distances[vertex1][vertex2] = distance;

                    if (vertex1 == vertex2) {
                        continue;
                    }
                    DirectedEdge edge = new DirectedEdge(vertex1, vertex2, distance);
                    edgeTo[vertex1][vertex2].add(edge);
                }
            }

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                        if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                            edgeTo[vertex2][vertex3] = new ArrayList<>();
                            edgeTo[vertex2][vertex3].addAll(edgeTo[vertex1][vertex3]);
                        } else if (distances[vertex2][vertex3] ==
                                distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                            edgeTo[vertex2][vertex3].addAll(edgeTo[vertex1][vertex3]);
                        }
                    }
                }
            }
        }

        public Long[][] allPathVertices(long geodeticSetBitset) {
            int vertices = distances.length;
            Long[][] allPathVertices = new Long[vertices][vertices];
            long[][] allPathsDp = new long[vertices][vertices];

            for (int vertexID1 = 0; vertexID1 < distances.length; vertexID1++) {
                for (int vertexID2 = 0; vertexID2 < distances.length; vertexID2++) {
                    if (vertexID2 < vertexID1) {
                        allPathVertices[vertexID1][vertexID2] = allPathVertices[vertexID2][vertexID1];
                    } else {
                        allPathVertices[vertexID1][vertexID2] =
                                verticesInPaths(vertexID1, vertexID2, allPathsDp, geodeticSetBitset);
                    }
                }
            }
            return allPathVertices;
        }

        private long verticesInPaths(int source, int target, long[][] allPathsDp, long geodeticSetBitset) {
            long verticesInPaths = 0L;
            verticesInPaths |= (1L << source);
            verticesInPaths |= getPathVertices(geodeticSetBitset, edgeTo[source][target], allPathsDp, source);

            allPathsDp[source][target] = verticesInPaths;
            return verticesInPaths;
        }

        private long getPathVertices(long geodeticSetBitset, List<DirectedEdge> currentEdges, long[][] allPathsDp,
                                     int source) {
            long verticesInPaths = 0L;

            if (currentEdges == null) {
                return 0;
            }
            for (DirectedEdge edge : currentEdges) {
                verticesInPaths |= (1L << edge.to());

                if (allPathsDp[source][edge.from()] != 0) {
                    verticesInPaths |= allPathsDp[source][edge.from()];
                } else {
                    long intermediateVertices = getPathVertices(geodeticSetBitset, edgeTo[source][edge.from()],
                            allPathsDp, source);
                    allPathsDp[source][edge.from()] = intermediateVertices;
                    verticesInPaths |= intermediateVertices;

                    if (verticesInPaths == geodeticSetBitset) {
                        return geodeticSetBitset;
                    }
                }
            }
            return verticesInPaths;
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
