package chapter4.section6.e.bipartite.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/08/24.
 */
@SuppressWarnings("unchecked")
public class GopherII {

    private static class Coordinate {
        double x;
        double y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int gophersNumber = Integer.parseInt(data[0]);
            int gopherHolesNumber = Integer.parseInt(data[1]);
            int timeToReachHole = Integer.parseInt(data[2]);
            int velocity = Integer.parseInt(data[3]);

            Coordinate[] gophers = new Coordinate[gophersNumber];
            Coordinate[] gopherHoles = new Coordinate[gopherHolesNumber];
            for (int i = 0; i < gophers.length; i++) {
                gophers[i] = new Coordinate(FastReader.nextDouble(), FastReader.nextDouble());
            }
            for (int i = 0; i < gopherHoles.length; i++) {
                gopherHoles[i] = new Coordinate(FastReader.nextDouble(), FastReader.nextDouble());
            }

            int vulnerableGophers = computeVulnerableGophersNumber(gophers, gopherHoles, timeToReachHole, velocity);
            outputWriter.printLine(vulnerableGophers);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeVulnerableGophersNumber(Coordinate[] gophers, Coordinate[] gopherHoles,
                                                      int timeToReachHole, int velocity) {
        List<Integer>[] adjacencyList = new List[gophers.length + gopherHoles.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int gopherId = 0; gopherId < gophers.length; gopherId++) {
            for (int gopherHoleId = 0; gopherHoleId < gopherHoles.length; gopherHoleId++) {
                int gopherHoleVertexId = gophers.length + gopherHoleId;

                double distance = distanceBetweenPoints(gophers[gopherId], gopherHoles[gopherHoleId]);
                double timeToReach = distance / velocity;
                if (timeToReach <= timeToReachHole) {
                    adjacencyList[gopherId].add(gopherHoleVertexId);
                }
            }
        }

        int maximumCardinality = computeMaximumCardinality(adjacencyList, gophers.length);
        return gophers.length - maximumCardinality;
    }

    private static int computeMaximumCardinality(List<Integer>[] adjacencyList, int leftPartitionMaxVertexID) {
        int maximumMatches = 0;
        int[] match = new int[adjacencyList.length];
        Arrays.fill(match, -1);

        for (int vertexID = 0; vertexID < leftPartitionMaxVertexID; vertexID++) {
            boolean[] visited = new boolean[adjacencyList.length];
            maximumMatches += tryToMatch(adjacencyList, match, visited, vertexID);
        }
        return maximumMatches;
    }

    private static int tryToMatch(List<Integer>[] adjacencyList, int[] match, boolean[] visited, int vertexID) {
        if (visited[vertexID]) {
            return 0;
        }
        visited[vertexID] = true;

        for (int neighbor : adjacencyList[vertexID]) {
            if (match[neighbor] == -1 || tryToMatch(adjacencyList, match, visited, match[neighbor]) == 1) {
                match[neighbor] = vertexID; // flip status
                return 1;
            }
        }
        return 0;
    }

    private static double distanceBetweenPoints(Coordinate coordinate1, Coordinate coordinate2) {
        return Math.sqrt(Math.pow(coordinate1.x - coordinate2.x, 2) + Math.pow(coordinate1.y - coordinate2.y, 2));
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
