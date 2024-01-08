package chapter4.section4.d.on.weighted.graph.dijkstra.easier;

import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by Rene Argento on 06/01/24.
 */
public class NumberMaze {

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReaderInteger fastReader = new FastReaderInteger();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = fastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[][] maze = new int[fastReader.nextInt()][fastReader.nextInt()];
            for (int row = 0; row < maze.length; row++) {
                for (int column = 0; column < maze[0].length; column++) {
                    maze[row][column] = fastReader.nextInt();
                }
            }

            long minimumCost = computeMinimumCost(maze);
            outputWriter.printLine(minimumCost);
        }
        outputWriter.flush();
    }

    private static long computeMinimumCost(int[][] maze) {
        Dijkstra dijkstra = new Dijkstra(maze);
        int exitID = getNodeID(maze, maze.length - 1, maze[0].length - 1);
        return dijkstra.distTo[exitID];
    }

    private static int getNodeID(int[][] maze, int row, int column) {
        return maze[0].length * row + column;
    }

    private static boolean isValid(int[][] maze, int row, int column) {
        return row >= 0 && row < maze.length && column >= 0 && column < maze[0].length;
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

        public Dijkstra(int[][] maze) {
            int nodes = maze.length * maze[0].length;
            int source = 0;
            distTo = new long[nodes];
            priorityQueue = new PriorityQueue<>(nodes);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = maze[0][0];
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                relax(maze, priorityQueue.poll());
            }
        }

        private void relax(int[][] maze, Vertex vertex) {
            int row = vertex.id / maze[0].length;
            int column = vertex.id % maze[0].length;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(maze, neighborRow, neighborColumn)) {
                    int cost = maze[neighborRow][neighborColumn];
                    int neighborID = getNodeID(maze, neighborRow, neighborColumn);

                    if (distTo[neighborID] > distTo[vertex.id] + cost) {
                        distTo[neighborID] = distTo[vertex.id] + cost;
                        priorityQueue.offer(new Vertex(neighborID, distTo[neighborID]));
                    }
                }
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
