package chapter4.section2.f.finding.articulation.points.bridges;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/04/23.
 */
@SuppressWarnings("unchecked")
public class KingpinEscape {

    private static class Route {
        int locationA;
        int locationB;

        public Route(int locationA, int locationB) {
            this.locationA = locationA;
            this.locationB = locationB;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int hideouts = FastReader.nextInt();
        int headquartersLocation = FastReader.nextInt();
        List<Integer>[] adjacencyList = new List[hideouts];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < hideouts - 1; i++) {
            int locationA = FastReader.nextInt();
            int locationB = FastReader.nextInt();
            adjacencyList[locationA].add(locationB);
            adjacencyList[locationB].add(locationA);
        }

        List<Route> escapeRoutesToAdd = computeEscapeRoutesToAdd(adjacencyList);
        outputWriter.printLine(escapeRoutesToAdd.size());
        for (Route route : escapeRoutesToAdd) {
            outputWriter.printLine(route.locationA + " " + route.locationB);
        }
        outputWriter.flush();
    }

    private static List<Route> computeEscapeRoutesToAdd(List<Integer>[] adjacencyList) {
        List<Route> escapeRoutesToAdd = new ArrayList<>();
        List<Integer> leaves = new ArrayList<>();
        boolean[] visited = new boolean[adjacencyList.length];

        depthFirstSearch(adjacencyList, visited, leaves, 0);
        int upperBound = leaves.size() / 2;

        for (int i = 0; i < upperBound; i++) {
            int leafToConnect = leaves.size() / 2 + i;
            escapeRoutesToAdd.add(new Route(leaves.get(i), leaves.get(leafToConnect)));
        }
        if (leaves.size() % 2 == 1) {
            escapeRoutesToAdd.add(new Route(leaves.get(leaves.size() - 1), leaves.get(0)));
        }
        return escapeRoutesToAdd;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, List<Integer> leaves,
                                         int locationID) {
        visited[locationID] = true;
        if (adjacencyList[locationID].size() == 1) {
            leaves.add(locationID);
        }

        for (int neighborID : adjacencyList[locationID]) {
            if (!visited[neighborID]) {
                depthFirstSearch(adjacencyList, visited, leaves, neighborID);
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
