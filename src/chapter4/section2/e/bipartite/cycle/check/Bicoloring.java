package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/03/23.
 */
@SuppressWarnings("unchecked")
public class Bicoloring {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodes = FastReader.nextInt();

        while (nodes != 0) {
            int edges = FastReader.nextInt();
            List<Integer>[] adjacencyList = new ArrayList[nodes];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < edges; i++) {
                int nodeID1 = FastReader.nextInt();
                int nodeID2 = FastReader.nextInt();
                adjacencyList[nodeID1].add(nodeID2);
                adjacencyList[nodeID2].add(nodeID1);
            }
            boolean isBiColorable = isBiColorable(adjacencyList);
            outputWriter.printLine(isBiColorable ? "BICOLORABLE." : "NOT BICOLORABLE.");
            nodes = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean isBiColorable(List<Integer>[] adjacencyList) {
        boolean[] visited = new boolean[adjacencyList.length];
        boolean[] colors = new boolean[adjacencyList.length];

        colors[0] = true;
        return isBiColorable(adjacencyList, visited, colors, 0);
    }

    private static boolean isBiColorable(List<Integer>[] adjacencyList, boolean[] visited, boolean[] colors,
                                         int nodeID) {
        visited[nodeID] = true;
        boolean isBiColorable = true;

        for (int neighborID : adjacencyList[nodeID]) {
            if (visited[neighborID] && colors[neighborID] == colors[nodeID]) {
                return false;
            }
            if (!visited[neighborID]) {
                colors[neighborID] = !colors[nodeID];
                isBiColorable &= isBiColorable(adjacencyList, visited, colors, neighborID);
            }
        }
        return isBiColorable;
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
