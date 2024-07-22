package chapter4.session6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 02/07/24.
 */
@SuppressWarnings("unchecked")
public class CentralPostOffice {

    private static class VertexData {
        int vertexID;
        int distance;

        public VertexData(int vertexID, int distance) {
            this.vertexID = vertexID;
            this.distance = distance;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int cityId = 0; cityId < adjacencyList.length; cityId++) {
                int neighbors = FastReader.nextInt();
                for (int n = 0; n < neighbors; n++) {
                    int neighborId = FastReader.nextInt() - 1;
                    adjacencyList[cityId].add(neighborId);
                }
            }

            int minimumDispatchTime = computeMinimumDispatchTime(adjacencyList);
            outputWriter.printLine(minimumDispatchTime);
        }
        outputWriter.flush();
    }

    private static int computeMinimumDispatchTime(List<Integer>[] adjacent) {
        int numberOfEdges = adjacent.length - 1;
        return (2 * numberOfEdges) - computeTreeDiameter(adjacent);
    }

    public static int computeTreeDiameter(List<Integer>[] adjacencyList) {
        VertexData furthestVertex = getFurthestVertex(adjacencyList, 0);
        VertexData furthestVertexFromFurthest = getFurthestVertex(adjacencyList, furthestVertex.vertexID);
        return furthestVertexFromFurthest.distance;
    }

    private static VertexData getFurthestVertex(List<Integer>[] adjacencyList, int sourceVertexId) {
        Queue<VertexData> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        VertexData source = new VertexData(sourceVertexId, 0);
        queue.offer(source);
        visited[sourceVertexId] = true;

        VertexData furthestVertex = source;

        while (!queue.isEmpty()) {
            VertexData vertexData = queue.poll();

            for (int neighbor : adjacencyList[vertexData.vertexID]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    int newDistance = vertexData.distance + 1;
                    queue.offer(new VertexData(neighbor, newDistance));

                    if (newDistance > furthestVertex.distance) {
                        furthestVertex = new VertexData(neighbor, newDistance);
                    }
                }
            }
        }
        return furthestVertex;
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
