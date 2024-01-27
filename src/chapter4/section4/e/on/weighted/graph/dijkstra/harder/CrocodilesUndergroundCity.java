package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/01/24.
 */
// Problem located at https://ioinformatics.org/files/ioi2011problem4.pdf
// Submitted at https://dmoj.ca/problem/ioi11p4io
@SuppressWarnings("unchecked")
public class CrocodilesUndergroundCity {

    public static void main(String[] args) throws IOException {
        FastReaderInteger fastReader = new FastReaderInteger();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int chambers = fastReader.nextInt();
        int corridors = fastReader.nextInt();
        int exitChambersNumber = fastReader.nextInt();

        int[][] corridorValues = new int[corridors][2];
        int[] traverseTimes = new int[corridors];
        for (int i = 0; i < corridorValues.length; i++) {
            corridorValues[i][0] = fastReader.nextInt();
            corridorValues[i][1] = fastReader.nextInt();
            traverseTimes[i] = fastReader.nextInt();
        }

        int[] exitChambers = new int[exitChambersNumber];
        for (int i = 0; i < exitChambers.length; i++) {
            exitChambers[i] = fastReader.nextInt();
        }

        long solution = travelPlan(chambers, corridors, corridorValues, traverseTimes, exitChambersNumber, exitChambers);
        outputWriter.printLine(solution);
        outputWriter.flush();
    }

    private static long travelPlan(int chambers, int corridors, int[][] corridorValues, int[] traverseTimes,
                                   int exitChambersNumber, int[] exitChambers) {
        List<Edge>[] adjacencyList = new List[chambers];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < corridors; i++) {
            int chamberID1 = corridorValues[i][0];
            int chamberID2 = corridorValues[i][1];
            int traverseTime = traverseTimes[i];
            adjacencyList[chamberID1].add(new Edge(chamberID2, traverseTime));
            adjacencyList[chamberID2].add(new Edge(chamberID1, traverseTime));
        }

        Dijkstra dijkstra = new Dijkstra(adjacencyList, exitChambers);
        return dijkstra.totalDistance;
    }

    private static class Edge {
        private final int vertex2;
        private final long distance;

        public Edge(int vertex2, long distance) {
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

        private final PriorityQueue<Vertex> priorityQueue;
        private long totalDistance;

        public Dijkstra(List<Edge>[] adjacencyList, int[] sources) {
            priorityQueue = new PriorityQueue<>(adjacencyList.length);
            int[] visits = new int[adjacencyList.length];

            for (int source : sources) {
                priorityQueue.offer(new Vertex(source, 0));
                visits[source] = 1;
            }

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == 0 && visits[vertex.id] == 1) {
                    totalDistance = vertex.distance;
                    break;
                }
                relax(adjacencyList, vertex, visits);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex, int[] visits) {
            if (visits[vertex.id] == 2) {
                return;
            }

            if (visits[vertex.id] == 0) {
                visits[vertex.id] = 1;
                return;
            } else {
                visits[vertex.id]++;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                if (visits[edge.vertex2] == 2) {
                    continue;
                }
                long newDistance = vertex.distance + edge.distance;
                priorityQueue.offer(new Vertex(edge.vertex2, newDistance));
            }
        }
    }

    private static class FastReaderInteger {
        private static final InputStream in = System.in;
        private static final int bufferSize = 30000;
        private static final byte[] buffer = new byte[bufferSize];
        private static int position = 0;
        private static int byteCount = bufferSize;
        private static byte character;

        FastReaderInteger() throws IOException {
            fill();
        }

        private void fill() throws IOException {
            byteCount = in.read(buffer, 0, bufferSize);
        }

        private int nextInt() throws IOException {
            while (character < '-') {
                character = readByte();
            }
            boolean isNegative = (character == '-');
            if (isNegative) {
                character = readByte();
            }
            int value = character - '0';
            while ((character = readByte()) >= '0' && character <= '9') {
                value = value * 10 + character - '0';
            }
            return isNegative ? -value : value;
        }

        private byte readByte() throws IOException {
            if (position == byteCount) {
                fill();
                position = 0;
            }
            return buffer[position++];
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
