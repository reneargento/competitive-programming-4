package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/07/22.
 */
public class MarblesOnATree {

    private static class Vertex {
        int parent;
        int childrenCount;
        int marbles;

        public Vertex() {
            parent = -1;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int verticesNumber = FastReader.nextInt();

        while (verticesNumber != 0) {
            Vertex[] vertices = new Vertex[verticesNumber];
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = new Vertex();
            }

            for (int i = 0; i < verticesNumber; i++) {
                int vertexId = FastReader.nextInt() - 1;
                vertices[vertexId].marbles += FastReader.nextInt();
                int children = FastReader.nextInt();
                vertices[vertexId].childrenCount = children;

                for (int c = 0; c < children; c++) {
                    int childId = FastReader.nextInt() - 1;
                    vertices[childId].parent = vertexId;
                }
            }
            long minimumMoves = computeMinimumMoves(vertices);
            outputWriter.printLine(minimumMoves);
            verticesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeMinimumMoves(Vertex[] vertices) {
        long minimumMoves = 0;
        Queue<Vertex> leaves = new LinkedList<>();
        addLeavesToQueue(vertices, leaves);

        while (!leaves.isEmpty()) {
            Vertex vertex = leaves.poll();

            if (vertex.parent != -1) {
                Vertex parent = vertices[vertex.parent];
                parent.childrenCount--;
                if (parent.childrenCount == 0) {
                    leaves.offer(parent);
                }

                int moves = Math.abs(vertex.marbles - 1);
                minimumMoves += moves;
                parent.marbles += (vertex.marbles - 1);
            }
        }
        return minimumMoves;
    }

    private static void addLeavesToQueue(Vertex[] vertices, Queue<Vertex> leaves) {
        for (Vertex vertex : vertices) {
            if (vertex.childrenCount == 0) {
                leaves.offer(vertex);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
