package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/04/23.
 */
@SuppressWarnings("unchecked")
public class Molekule {

    private static class Edge {
        int moleculeID1;
        int moleculeID2;

        public Edge(int moleculeID1, int moleculeID2) {
            this.moleculeID1 = moleculeID1;
            this.moleculeID2 = moleculeID2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int molecules = FastReader.nextInt();
        List<Integer>[] adjacencyList = new List[molecules];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < molecules - 1; i++) {
            int moleculeID1 = FastReader.nextInt() - 1;
            int moleculeID2 = FastReader.nextInt() - 1;
            adjacencyList[moleculeID1].add(moleculeID2);
            adjacencyList[moleculeID2].add(moleculeID1);
            edges.add(new Edge(moleculeID1, moleculeID2));
        }
        setDirections(adjacencyList, edges);
    }

    private static void setDirections(List<Integer>[] adjacencyList, List<Edge> edges) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        Boolean[] colors = new Boolean[adjacencyList.length];
        setColors(adjacencyList, colors, 0, true);

        for (Edge edge : edges) {
            outputWriter.printLine(colors[edge.moleculeID1] ? "1" : "0");
        }
        outputWriter.flush();
    }

    private static void setColors(List<Integer>[] adjacencyList, Boolean[] colors, int moleculeID, boolean color) {
        colors[moleculeID] = color;

        for (int neighborID : adjacencyList[moleculeID]) {
            if (colors[neighborID] == null) {
                setColors(adjacencyList, colors, neighborID, !color);
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
