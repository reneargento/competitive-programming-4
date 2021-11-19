package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/11/21.
 */
@SuppressWarnings("unchecked")
public class TriangleCounting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int vertices = FastReader.nextInt();
            int edges = FastReader.nextInt();
            List<Integer>[] adjacencyList = new List[vertices + 1];

            for (int i = 0; i < edges; i++) {
                int vertex1 = FastReader.nextInt();
                int vertex2 = FastReader.nextInt();
                if (vertex1 < vertex2) {
                    if (adjacencyList[vertex1] == null) {
                        adjacencyList[vertex1] = new ArrayList<>();
                    }
                    adjacencyList[vertex1].add(vertex2);
                } else {
                    if (adjacencyList[vertex2] == null) {
                        adjacencyList[vertex2] = new ArrayList<>();
                    }
                    adjacencyList[vertex2].add(vertex1);
                }
            }
            long triangles = countTriangles(adjacencyList);
            outputWriter.printLine(triangles);
        }
        outputWriter.flush();
    }

    private static long countTriangles(List<Integer>[] adjacencyList) {
        long triangles = 0;
        int[] edgesMarked = new int[adjacencyList.length];

        for (int vertex1 = 1; vertex1 < adjacencyList.length; vertex1++) {
            if (adjacencyList[vertex1] != null) {
                for (int vertex2 : adjacencyList[vertex1]) {
                    edgesMarked[vertex2] = 1;
                }

                for (int vertex2 : adjacencyList[vertex1]) {
                    if (adjacencyList[vertex2] != null) {
                        for (int vertex3 : adjacencyList[vertex2]) {
                            triangles += edgesMarked[vertex3];
                        }
                    }
                }

                for (int vertex2 : adjacencyList[vertex1]) {
                    edgesMarked[vertex2] = 0;
                }
            }
        }
        return triangles;
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
