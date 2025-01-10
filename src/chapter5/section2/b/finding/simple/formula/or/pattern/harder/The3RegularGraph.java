package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/12/24.
 */
public class The3RegularGraph {

    private static class Edge {
        int vertexId1;
        int vertexId2;

        public Edge(int vertexId1, int vertexId2) {
            this.vertexId1 = vertexId1;
            this.vertexId2 = vertexId2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int verticesNumber = FastReader.nextInt();

        while (verticesNumber != 0) {
            List<Edge> graph = build3RegularGraph(verticesNumber, 1);
            if (graph == null) {
                outputWriter.printLine("Impossible");
            } else {
                outputWriter.printLine(graph.size());
                for (Edge edge : graph) {
                    outputWriter.printLine(edge.vertexId1 + " " + edge.vertexId2);
                }
            }
            verticesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Edge> build3RegularGraph(int verticesNumber, int startVertex) {
        if (verticesNumber == 2 || verticesNumber % 2 == 1) {
            return null;
        }

        if (verticesNumber % 4 == 0) {
            return buildMultiple4Graph(verticesNumber, startVertex);
        } else if (verticesNumber % 6 == 0) {
            return buildMultiple6Graph(verticesNumber, startVertex);
        } else {
            List<Edge> graph = buildMultiple4Graph(4, startVertex);
            List<Edge> graphRemaining = build3RegularGraph(verticesNumber - 4, startVertex + 4);
            graph.addAll(graphRemaining);
            return graph;
        }
    }

    private static List<Edge> buildMultiple4Graph(int verticesNumber, int startVertexId) {
        List<Edge> graph = new ArrayList<>();
        for (int vertexId = startVertexId; vertexId < startVertexId + verticesNumber; vertexId += 4) {
            int nextVertexId = vertexId + 1;
            int nextNextVertexId = vertexId + 2;
            int nextNextNextVertexId = vertexId + 3;

            graph.add(new Edge(vertexId, nextVertexId));
            graph.add(new Edge(vertexId, nextNextVertexId));
            graph.add(new Edge(vertexId, nextNextNextVertexId));
            graph.add(new Edge(nextVertexId, nextNextVertexId));
            graph.add(new Edge(nextVertexId, nextNextNextVertexId));
            graph.add(new Edge(nextNextVertexId, nextNextNextVertexId));
        }
        return graph;
    }

    private static List<Edge> buildMultiple6Graph(int verticesNumber, int startVertexId) {
        List<Edge> graph = new ArrayList<>();
        for (int vertexId = startVertexId; vertexId < startVertexId + verticesNumber; vertexId += 6) {
            int nextVertexId = vertexId + 3;
            int nextNextVertexId = vertexId + 4;
            int nextNextNextVertexId = vertexId + 5;

            graph.add(new Edge(vertexId, nextVertexId));
            graph.add(new Edge(vertexId, nextNextVertexId));
            graph.add(new Edge(vertexId, nextNextNextVertexId));
            graph.add(new Edge(vertexId + 1, nextVertexId));
            graph.add(new Edge(vertexId + 1, nextNextVertexId));
            graph.add(new Edge(vertexId + 1, nextNextNextVertexId));
            graph.add(new Edge(vertexId + 2, nextVertexId));
            graph.add(new Edge(vertexId + 2, nextNextVertexId));
            graph.add(new Edge(vertexId + 2, nextNextNextVertexId));
        }
        return graph;
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
