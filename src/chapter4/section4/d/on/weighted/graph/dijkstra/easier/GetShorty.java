package chapter4.section4.d.on.weighted.graph.dijkstra.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/01/24.
 */
@SuppressWarnings("unchecked")
public class GetShorty {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int intersections = FastReader.nextInt();
        int corridors = FastReader.nextInt();

        while (intersections != 0 || corridors != 0) {
            List<Edge>[] adjacencyList = new List[intersections];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int m = 0; m < corridors; m++) {
                int intersectionID1 = FastReader.nextInt();
                int intersectionID2 = FastReader.nextInt();
                double factor = FastReader.nextDouble();
                adjacencyList[intersectionID1].add(new Edge(intersectionID2, factor));
                adjacencyList[intersectionID2].add(new Edge(intersectionID1, factor));
            }

            Dijkstra dijkstra = new Dijkstra(adjacencyList, 0);
            double finalFraction = dijkstra.distTo[adjacencyList.length - 1];
            outputWriter.printLine(String.format("%.4f", finalFraction));

            intersections = FastReader.nextInt();
            corridors = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static class Edge {
        private final int vertex2;
        private final double factor;

        public Edge(int vertex2, double factor) {
            this.vertex2 = vertex2;
            this.factor = factor;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            double fraction;

            public Vertex(int id, double fraction) {
                this.id = id;
                this.fraction = fraction;
            }

            @Override
            public int compareTo(Vertex other) {
                return Double.compare(other.fraction, fraction);
            }
        }

        private final double[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;

        public Dijkstra(List<Edge>[] adjacencyList, int source) {
            distTo = new double[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, 0);
            distTo[source] = 1;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                relax(adjacencyList, priorityQueue.poll());
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] < distTo[vertex.id] * edge.factor) {
                    distTo[neighbor] = distTo[vertex.id] * edge.factor;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                }
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
