package chapter4.session6.f.eulerian.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/08/24.
 */
@SuppressWarnings("unchecked")
public class EulerianPath {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int vertices = FastReader.nextInt();
        int edges = FastReader.nextInt();

        while (vertices != 0 || edges != 0) {
            List<Integer>[] adjacent = new List[vertices];
            for (int i = 0; i < adjacent.length; i++) {
                adjacent[i] = new ArrayList<>();
            }

            for (int e = 0; e < edges; e++) {
                int vertexId1 = FastReader.nextInt();
                int vertexId2 = FastReader.nextInt();
                adjacent[vertexId1].add(vertexId2);
            }

            List<Integer> eulerianPath = getEulerianPath(adjacent);
            if (eulerianPath == null) {
                outputWriter.printLine("Impossible");
            } else {
                outputWriter.print(eulerianPath.get(0));
                for (int i = 1; i < eulerianPath.size(); i++) {
                    outputWriter.print(" " + eulerianPath.get(i));
                }
                outputWriter.printLine();
            }

            vertices = FastReader.nextInt();
            edges = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> getEulerianPath(List<Integer>[] adjacent) {
        Deque<Integer> eulerianPathStack = getDirectedEulerianPath(adjacent);
        if (eulerianPathStack == null) {
            return null;
        }

        List<Integer> eulerianPath = new ArrayList<>();
        while (!eulerianPathStack.isEmpty()) {
            eulerianPath.add(eulerianPathStack.pop());
        }
        return eulerianPath;
    }

    private static Deque<Integer> getDirectedEulerianPath(List<Integer>[] adjacent) {
        int edges = 0;
        int[] inDegrees = new int[adjacent.length];
        int[] outDegrees = new int[adjacent.length];

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            edges += adjacent[vertex].size();

            for (int neighbor : adjacent[vertex]) {
                outDegrees[vertex]++;
                inDegrees[neighbor]++;
            }
        }

        // A graph with no edges is considered to have an Eulerian cycle
        if (edges == 0) {
            return new ArrayDeque<>();
        }

        int startVertexCandidates = 0;
        int endVertexCandidates = 0;
        int startVertex = -1;

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            if (inDegrees[vertex] == outDegrees[vertex]) {
                if (inDegrees[vertex] > 0 && startVertex == -1) {
                    startVertex = vertex;
                }
            } else {
                if (inDegrees[vertex] - 1 == outDegrees[vertex]) {
                    endVertexCandidates++;
                } else if (inDegrees[vertex] + 1 == outDegrees[vertex]) {
                    startVertexCandidates++;
                    startVertex = vertex;
                } else {
                    return null;
                }
            }
        }

        boolean hasEulerCycle = startVertexCandidates == 0 && endVertexCandidates == 0;
        boolean hasEulerPath = startVertexCandidates == 1 && endVertexCandidates == 1;
        if (!hasEulerCycle && !hasEulerPath) {
            return null;
        }

        Deque<Integer> dfsStack = new ArrayDeque<>();
        dfsStack.push(startVertex);

        Deque<Integer> eulerPath = new ArrayDeque<>();
        int[] index = new int[adjacent.length];

        while (!dfsStack.isEmpty()) {
            int vertex = dfsStack.peek();

            if (index[vertex] < adjacent[vertex].size()) {
                int indexValue = index[vertex];
                int neighborVertex = adjacent[vertex].get(indexValue);
                dfsStack.push(neighborVertex);
                index[vertex]++;
            } else {
                // Push vertex with no more leaving edges to the Euler path
                eulerPath.push(vertex);
                dfsStack.pop();
            }
        }

        // For each edge visited, we visited a vertex. Add 1 because the first and last vertices are the same.
        if (eulerPath.size() == edges + 1) {
            return eulerPath;
        } else {
            return null;
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
