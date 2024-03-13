package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/03/24.
 */
public class ImportSpaghetti {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int files = FastReader.nextInt();
        Map<String, Integer> fileNameToIDMap = new HashMap<>();
        String[] fileIDToNameMap = new String[files];

        for (int f = 0; f < files; f++) {
            String fileName = FastReader.next();
            fileIDToNameMap[f] = fileName;
            getFileID(fileNameToIDMap, fileName);
        }

        int[][] edgeWeightedDigraph = new int[files][files];
        for (int[] values : edgeWeightedDigraph) {
            Arrays.fill(values, INFINITE);
        }

        for (int f = 0; f < files; f++) {
            int fileID = getFileID(fileNameToIDMap, FastReader.next());
            int imports = FastReader.nextInt();
            for (int i = 0; i < imports; i++) {
                String[] data = FastReader.getLine().split(" ");
                for (int d = 1; d < data.length; d++) {
                    String trimmedName = data[d].replaceAll(",", "");
                    int dependencyID = getFileID(fileNameToIDMap, trimmedName);
                    edgeWeightedDigraph[fileID][dependencyID] = 1;
                }
            }
        }

        FloydWarshall floydWarshall = new FloydWarshall(edgeWeightedDigraph);
        List<Integer> filesInShortestCycle = floydWarshall.getShortestCycle();
        if (filesInShortestCycle == null) {
            outputWriter.printLine("SHIP IT");
        } else {
            outputWriter.print(fileIDToNameMap[filesInShortestCycle.get(0)]);
            for (int i = 1; i < filesInShortestCycle.size(); i++) {
                String fileName = fileIDToNameMap[filesInShortestCycle.get(i)];
                outputWriter.print(" " + fileName);
            }
        }
        outputWriter.flush();
    }

    private static int getFileID(Map<String, Integer> fileNameToIDMap, String fileName) {
        if (!fileNameToIDMap.containsKey(fileName)) {
            fileNameToIDMap.put(fileName, fileNameToIDMap.size());
        }
        return fileNameToIDMap.get(fileName);
    }

    private static class DirectedEdge {
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

    private static class FloydWarshall {
        private final int[][] distances;        // length of shortest v->w path
        private final DirectedEdge[][] edgeTo;  // last edge on shortest v->w path

        public FloydWarshall(int[][] edgeWeightedDigraph) {
            int vertices = edgeWeightedDigraph.length;
            distances = new int[vertices][vertices];
            edgeTo = new DirectedEdge[vertices][vertices];

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    int distance = edgeWeightedDigraph[vertex1][vertex2];
                    distances[vertex1][vertex2] = distance;
                    DirectedEdge edge = new DirectedEdge(vertex1, vertex2, distance);
                    edgeTo[vertex1][vertex2] = edge;
                }
            }

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                        if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                            edgeTo[vertex2][vertex3] = edgeTo[vertex1][vertex3];
                        }
                    }
                }
            }
        }

        public int distance(int source, int target) {
            return distances[source][target];
        }

        public List<Integer> path(int source, int target) {
            Deque<DirectedEdge> path = new ArrayDeque<>();
            for (DirectedEdge edge = edgeTo[source][target]; edge.from() != source; edge = edgeTo[source][edge.from()]) {
                path.push(edge);
            }

            List<Integer> pathList = new ArrayList<>();
            while (!path.isEmpty()) {
                DirectedEdge edge = path.pop();
                pathList.add(edge.from());
            }
            pathList.add(target);
            return pathList;
        }

        public List<Integer> getShortestCycle() {
            int shortestLength = INFINITE;
            int startVertexInCycle = -1;

            for (int vertex = 0; vertex < distances.length; vertex++) {
                if (distances[vertex][vertex] < shortestLength) {
                    shortestLength = distances[vertex][vertex];
                    startVertexInCycle = vertex;
                }
            }

            if (shortestLength == INFINITE) {
                return null;
            }
            return path(startVertexInCycle, startVertexInCycle);
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
