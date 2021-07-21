package chapter2.section4.a.graph.data.structures.problems;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/07/21.
 */
public class DemandingDilemma {

    private static class Edge {
        int vertex1;
        int vertex2;

        public Edge(int vertex1, int vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Edge edge = (Edge) o;
            return vertex1 == edge.vertex1 && vertex2 == edge.vertex2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertex1, vertex2);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();

            int[][] matrix = new int[rows][columns];
            for (int row = 0; row < matrix.length; row++) {
                for (int column = 0; column < matrix[0].length; column++) {
                    matrix[row][column] = FastReader.nextInt();
                }
            }

            boolean canBeIncidenceMatrix = canBeIncidenceMatrix(matrix);
            outputWriter.printLine(canBeIncidenceMatrix ? "Yes" : "No");
        }
        outputWriter.flush();
    }

    private static boolean canBeIncidenceMatrix(int[][] matrix) {
        Set<Edge> edges = new HashSet<>();

        int[] incidenceEdgesInColumn = new int[matrix[0].length];
        for (int column = 0; column < matrix[0].length; column++) {
            int vertex1 = -1;

            for (int row = 0; row < matrix.length; row++) {
                if (matrix[row][column] == 1) {
                    if (vertex1 == -1) {
                        vertex1 = row;
                    } else {
                        Edge edge = new Edge(vertex1, row);
                        if (edges.contains(edge)) {
                            return false;
                        }
                        edges.add(edge);
                    }
                    incidenceEdgesInColumn[column]++;
                }
            }
        }

        for (int edgesInColumn : incidenceEdgesInColumn) {
            if (edgesInColumn != 2) {
                return false;
            }
        }
        return true;
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
