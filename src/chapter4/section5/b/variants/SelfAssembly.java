package chapter4.section5.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/04/24.
 */
public class SelfAssembly {

    private static final int MAX_SIZE = 52;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int moleculeTypes = FastReader.nextInt();
        boolean[][] connected = new boolean[MAX_SIZE][MAX_SIZE];

        for (int i = 0; i < moleculeTypes; i++) {
            String molecule = FastReader.next();
            List<Integer> vertices = getVertices(molecule);
            addConnections(vertices, connected);
        }

        transitiveClosure(connected);
        outputWriter.printLine(isUnbounded(connected) ? "unbounded" : "bounded");
        outputWriter.flush();
    }

    private static List<Integer> getVertices(String molecule) {
        List<Integer> vertices = new ArrayList<>();

        for (int i = 0; i < molecule.length(); i += 2) {
            char firstSymbol = molecule.charAt(i);
            if (firstSymbol == '0') {
                continue;
            }
            int vertexID = firstSymbol - 'A';
            if (molecule.charAt(i + 1) == '+') {
                vertexID += MAX_SIZE / 2;
            }
            vertices.add(vertexID);
        }
        return vertices;
    }

    private static void addConnections(List<Integer> vertices, boolean[][] connected) {
        for (int vertexID1 = 0; vertexID1 < vertices.size(); vertexID1++) {
            int vertexID = vertices.get(vertexID1);
            for (int vertexID2 = 0; vertexID2 < vertices.size(); vertexID2++) {
                if (vertexID1 == vertexID2) {
                    continue;
                }
                int oppositeVertex = (vertices.get(vertexID2) + MAX_SIZE / 2) % MAX_SIZE;
                connected[vertexID][oppositeVertex] = true;
            }
        }
    }

    private static boolean isUnbounded(boolean[][] connected) {
        for (int vertexID = 0; vertexID < connected.length; vertexID++) {
            if (connected[vertexID][vertexID]) {
                return true;
            }
        }
        return false;
    }

    private static void transitiveClosure(boolean[][] distances) {
        for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < distances.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < distances.length; vertex3++) {
                    distances[vertex2][vertex3] |= distances[vertex2][vertex1] && distances[vertex1][vertex3];
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
