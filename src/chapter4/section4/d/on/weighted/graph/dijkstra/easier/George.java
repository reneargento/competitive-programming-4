package chapter4.section4.d.on.weighted.graph.dijkstra.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/01/24.
 */
@SuppressWarnings("unchecked")
public class George {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int intersections = FastReader.nextInt();
        List<Edge>[] adjacencyList = new List[intersections];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        int streets = FastReader.nextInt();
        int lukaStart = FastReader.nextInt() - 1;
        int lukaTarget = FastReader.nextInt() - 1;
        int lukaMinutesStart = FastReader.nextInt();
        int[] georgesIntersections = new int[FastReader.nextInt()];
        int[][] streetsTime = new int[intersections][intersections];

        for (int i = 0; i < georgesIntersections.length; i++) {
            georgesIntersections[i] = FastReader.nextInt() - 1;
        }

        for (int m = 0; m < streets; m++) {
            int intersectionID1 = FastReader.nextInt() - 1;
            int intersectionID2 = FastReader.nextInt() - 1;
            int minutes = FastReader.nextInt();
            adjacencyList[intersectionID1].add(new Edge(intersectionID1, intersectionID2, minutes));
            adjacencyList[intersectionID2].add(new Edge(intersectionID2, intersectionID1, minutes));
            streetsTime[intersectionID1][intersectionID2] = minutes;
            streetsTime[intersectionID2][intersectionID1] = minutes;
        }

        long minimumDeliveryTime = computeMinimumDeliveryTime(adjacencyList, lukaStart, lukaTarget,
                lukaMinutesStart, georgesIntersections, streetsTime) - lukaMinutesStart;
        outputWriter.printLine(minimumDeliveryTime);
        outputWriter.flush();
    }

    private static long computeMinimumDeliveryTime(List<Edge>[] adjacencyList, int lukaStart, int lukaTarget,
                                                   int lukaMinutesStart, int[] georgesIntersections,
                                                   int[][] streetsTime) {
        Block[][] blocks = computeStreetBlocks(georgesIntersections, streetsTime);
        Dijkstra dijkstra = new Dijkstra(adjacencyList, lukaStart, lukaTarget, lukaMinutesStart, blocks);
        return dijkstra.distTo[lukaTarget];
    }

    private static Block[][] computeStreetBlocks(int[] georgesIntersections, int[][] streetsTime) {
        int intersections = streetsTime.length;
        Block[][] blocks = new Block[intersections][intersections];
        int time = 0;

        if (georgesIntersections.length > 0) {
            int previousIntersection = georgesIntersections[0];

            for (int i = 1; i < georgesIntersections.length; i++) {
                int intersection = georgesIntersections[i];
                int nextTime = time + streetsTime[previousIntersection][intersection];
                Block block = new Block(time, nextTime - 1);
                blocks[previousIntersection][intersection] = block;
                blocks[intersection][previousIntersection] = block;

                time = nextTime;
                previousIntersection = intersection;
            }
        }
        return blocks;
    }

    private static class Block {
        int startTime;
        int endTime;

        public Block(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    private static class Edge {
        private final int vertex1;
        private final int vertex2;
        private final long distance;

        public Edge(int vertex1, int vertex2, long distance) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.distance = distance;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            long distance;

            public Vertex(int id, long distance) {
                this.id = id;
                this.distance = distance;
            }

            @Override
            public int compareTo(Vertex other) {
                return Long.compare(distance, other.distance);
            }
        }

        private final long[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final long MAX_VALUE = 10000000000000000L;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int target, int startTime, Block[][] blocks) {
            distTo = new long[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = startTime;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == target) {
                    break;
                }
                relax(adjacencyList, vertex, blocks);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex, Block[][] blocks) {
            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;
                long startDistance = distTo[vertex.id];

                if (blocks[edge.vertex1][edge.vertex2] != null) {
                    Block block = blocks[edge.vertex1][edge.vertex2];
                    if (block.startTime <= startDistance && startDistance <= block.endTime) {
                        startDistance = block.endTime + 1;
                    }
                }

                if (distTo[neighbor] > startDistance + edge.distance) {
                    distTo[neighbor] = startDistance + edge.distance;
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
