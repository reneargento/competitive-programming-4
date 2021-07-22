package chapter2.section4.a.graph.data.structures.problems;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/07/21.
 */
@SuppressWarnings("unchecked")
public class WeakVertices {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int vertices = FastReader.nextInt();

        while (vertices != -1) {
            Set<Integer>[] adjacencySet = new Set[vertices];
            for (int i = 0; i < vertices; i++) {
                adjacencySet[i] = new HashSet<>();
            }

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    int edge = FastReader.nextInt();
                    if (edge == 1) {
                        adjacencySet[vertex1].add(vertex2);
                    }
                }
            }
            List<Integer> weakVertices = computeWeakVertices(adjacencySet);
            for (int i = 0; i < weakVertices.size(); i++) {
                outputWriter.print(weakVertices.get(i));

                if (i != weakVertices.size() - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
            vertices = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeWeakVertices(Set<Integer>[] adjacencySet) {
        Set<Integer> triangles = new HashSet<>();

        for (int vertex = 0; vertex < adjacencySet.length; vertex++) {
            if (triangles.contains(vertex)) {
                continue;
            }

            for (int vertex2 : adjacencySet[vertex]) {
                for (int vertex3 : adjacencySet[vertex2]) {
                    if (vertex3 == vertex) {
                        continue;
                    }
                    if (adjacencySet[vertex].contains(vertex3)) {
                        triangles.add(vertex);
                        triangles.add(vertex2);
                        triangles.add(vertex3);
                        break;
                    }
                }

                if (triangles.contains(vertex)) {
                    break;
                }
            }
        }

        List<Integer> weakVertices = new ArrayList<>();
        for (int vertex = 0; vertex < adjacencySet.length; vertex++) {
            if (!triangles.contains(vertex)) {
                weakVertices.add(vertex);
            }
        }
        return weakVertices;
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
