package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/04/23.
 */
@SuppressWarnings("unchecked")
public class Hoppers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        int links = FastReader.nextInt();
        for (int i = 0; i < links; i++) {
            int hostID1 = FastReader.nextInt() - 1;
            int hostID2 = FastReader.nextInt() - 1;
            adjacencyList[hostID1].add(hostID2);
            adjacencyList[hostID2].add(hostID1);
        }

        int minimumITRequests = computeMinimumITRequests(adjacencyList);
        outputWriter.printLine(minimumITRequests);
        outputWriter.flush();
    }

    private static int computeMinimumITRequests(List<Integer>[] adjacencyList) {
        int minimumITRequests = 0;
        boolean hasNonBipartiteGraph = false;
        Boolean[] color = new Boolean[adjacencyList.length];

        for (int hostID = 0; hostID < adjacencyList.length; hostID++) {
            if (color[hostID] == null) {
                minimumITRequests++;
                boolean isBipartite = isBipartite(adjacencyList, color, hostID, true);
                if (!isBipartite) {
                    hasNonBipartiteGraph = true;
                }
            }
        }

        if (hasNonBipartiteGraph) {
            minimumITRequests--;
        }
        return minimumITRequests;
    }

    private static boolean isBipartite(List<Integer>[] adjacencyList, Boolean[] color, int hostID, boolean colorValue) {
        boolean isBipartite = true;
        color[hostID] = colorValue;

        for (int neighborID : adjacencyList[hostID]) {
            if (color[neighborID] == null) {
                isBipartite &= isBipartite(adjacencyList, color, neighborID, !colorValue);
            } else if (color[neighborID] == colorValue) {
                isBipartite = false;
            }
        }
        return isBipartite;
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
