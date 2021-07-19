package chapter2.section4.a.graph.data.structures.problems;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/07/21.
 */
public class TheForrestForTheTrees {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            BitSet bitSet = new BitSet(26);
            int edges = 0;
            String edge = FastReader.getLine();

            while (edge.charAt(0) != '*') {
                int vertex1 = edge.charAt(1) - 'A';
                int vertex2 = edge.charAt(3) - 'A';
                bitSet.set(vertex1);
                bitSet.set(vertex2);
                edges++;

                edge = FastReader.getLine();
            }

            String[] vertices = FastReader.getLine().split(",");
            int numberOfVertices = vertices.length;
            int connectedComponents = numberOfVertices - edges;

            int acorns = numberOfVertices - bitSet.cardinality();
            int trees = connectedComponents - acorns;
            outputWriter.printLine(String.format("There are %d tree(s) and %d acorn(s).", trees, acorns));
        }
        outputWriter.flush();
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