package chapter4.section6.e.bipartite.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/07/24.
 */
public class EscapePlan {

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
        int robotsNumber = FastReader.nextInt();
        int scenarioId = 1;

        while (robotsNumber != 0) {
            Coordinate[] robots = new Coordinate[robotsNumber];
            for (int i = 0; i < robots.length; i++) {
                robots[i] = new Coordinate(FastReader.nextDouble(), FastReader.nextDouble());
            }

            Coordinate[] holes = new Coordinate[FastReader.nextInt()];
            for (int i = 0; i < holes.length; i++) {
                holes[i] = new Coordinate(FastReader.nextDouble(), FastReader.nextDouble());
            }

            List<Integer> robotsEscaped = countRobotsEscape(robots, holes);
            outputWriter.printLine(String.format("Scenario %d", scenarioId));
            outputWriter.printLine(String.format("In 5 seconds %d robot(s) can escape", robotsEscaped.get(0)));
            outputWriter.printLine(String.format("In 10 seconds %d robot(s) can escape", robotsEscaped.get(1)));
            outputWriter.printLine(String.format("In 20 seconds %d robot(s) can escape\n", robotsEscaped.get(2)));

            scenarioId++;
            robotsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> countRobotsEscape(Coordinate[] robots, Coordinate[] holes) {
        List<Integer> robotsEscapedList = new ArrayList<>();
        int[] seconds = { 5, 10, 20 };
        for (int secondsValue : seconds) {
            List<Integer>[] adjacencyList = buildGraph(robots, holes, secondsValue);
            int robotsEscaped = computeMaximumCardinality(adjacencyList, robots.length);
            robotsEscapedList.add(robotsEscaped);
        }
        return robotsEscapedList;
    }

    @SuppressWarnings("unchecked")
    private static List<Integer>[] buildGraph(Coordinate[] robots, Coordinate[] holes, int seconds) {
        List<Integer>[] adjacencyList = new List[robots.length + holes.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        double maxDistance = 10 * seconds;

        for (int robotId  = 0; robotId < robots.length; robotId++) {
            for (int holeId = 0; holeId < holes.length; holeId++) {
                double distance = distanceBetweenCoordinates(robots[robotId], holes[holeId]);
                if (distance <= maxDistance) {
                    int holeVertexId = holeId + robots.length;
                    adjacencyList[robotId].add(holeVertexId);
                }
            }
        }
        return adjacencyList;
    }

    private static double distanceBetweenCoordinates(Coordinate coordinate1, Coordinate coordinate2) {
        return Math.sqrt(Math.pow(coordinate1.x - coordinate2.x, 2) + Math.pow(coordinate1.y - coordinate2.y, 2));
    }

    private static int computeMaximumCardinality(List<Integer>[] adjacencyList, int leftPartitionMaxVertexID) {
        int maximumMatches = 0;
        int[] match = new int[adjacencyList.length];
        Arrays.fill(match, -1);

        for (int vertexID = 0; vertexID <= leftPartitionMaxVertexID; vertexID++) {
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
