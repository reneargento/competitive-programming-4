package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/01/24.
 */
public class BlockCrusher {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int height = FastReader.nextInt();
        int width = FastReader.nextInt();

        while (height != 0 || width != 0) {
            int[][] block = new int[height][width];
            for (int row = 0; row < block.length; row++) {
                String columns = FastReader.getLine();
                for (int column = 0; column < columns.length(); column++) {
                    block[row][column] = Character.getNumericValue(columns.charAt(column));
                }
            }

            new Dijkstra(block);

            for (int row = 0; row < block.length; row++) {
                for (int column = 0; column < block[0].length; column++) {
                    if (block[row][column] != 0) {
                        outputWriter.print(block[row][column]);
                    } else {
                        outputWriter.print(" ");
                    }
                }
                outputWriter.printLine();
            }
            outputWriter.printLine();

            height = FastReader.nextInt();
            width = FastReader.nextInt();
        }
        outputWriter.flush();
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

        private final int[] vertexTo;
        private final long[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final long MAX_VALUE = 10000000000000000L;
        private final int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
        private final int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

        public Dijkstra(int[][] block) {
            int vertices = block.length * block[0].length;
            vertexTo = new int[vertices];
            distTo = new long[vertices];
            priorityQueue = new PriorityQueue<>(vertices);
            int endVertexID = -1;

            Arrays.fill(vertexTo, -1);
            Arrays.fill(distTo, MAX_VALUE);
            for (int column = 0; column < block[0].length; column++) {
                int vertexID = getVertexID(block, 0, column);
                distTo[vertexID] = block[0][column];
                priorityQueue.offer(new Vertex(vertexID, distTo[vertexID]));
            }

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                int row = vertex.id / block[0].length;
                if (row == block.length - 1) {
                    endVertexID = vertex.id;
                    break;
                }
                relax(block, vertex);
            }
            markFractureLine(block, endVertexID);
        }

        private void relax(int[][] block, Vertex vertex) {
            int row = vertex.id / block[0].length;
            int column = vertex.id % block[0].length;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(block, neighborRow, neighborColumn)) {
                    int neighborID = getVertexID(block, neighborRow, neighborColumn);
                    int distance = block[neighborRow][neighborColumn];

                    if (distTo[neighborID] > distTo[vertex.id] + distance) {
                        distTo[neighborID] = distTo[vertex.id] + distance;
                        vertexTo[neighborID] = vertex.id;
                        priorityQueue.offer(new Vertex(neighborID, distTo[neighborID]));
                    }
                }
            }
        }

        public void markFractureLine(int[][] block, int endVertex) {
            int endRow = endVertex / block[0].length;
            int endColumn = endVertex % block[0].length;
            block[endRow][endColumn] = 0;

            for (int previousVertexID = vertexTo[endVertex]; previousVertexID != -1; previousVertexID = vertexTo[previousVertexID]) {
                int row = previousVertexID / block[0].length;
                int column = previousVertexID % block[0].length;
                block[row][column] = 0;
            }
        }

        private int getVertexID(int[][] block, int row, int column) {
            return row * block[0].length + column;
        }

        private boolean isValid(int[][] block, int row, int column) {
            return row >= 0 && row < block.length && column >= 0 && column < block[0].length;
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
