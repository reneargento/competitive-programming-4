package chapter4.section6.e.bipartite.graph;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by Rene Argento on 02/08/24.
 */
@SuppressWarnings("unchecked")
public class SortingSlides {

    private static class Coordinate {
        int[] xCoordinates;
        int[] yCoordinates;

        public Coordinate(int[] xCoordinates, int[] yCoordinates) {
            this.xCoordinates = xCoordinates;
            this.yCoordinates = yCoordinates;
        }
    }

    private static class Match implements Comparable<Match> {
        char letter;
        int numberId;

        public Match(char letter, int numberId) {
            this.letter = letter;
            this.numberId = numberId;
        }

        @Override
        public int compareTo(Match other) {
            return Character.compare(letter, other.letter);
        }
    }

    private static class Edge {
        int slideId;
        int numberVertexId;

        public Edge(int slideId, int numberVertexId) {
            this.slideId = slideId;
            this.numberVertexId = numberVertexId;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int slides = FastReader.nextInt();
        int heapId = 1;

        while (slides != 0) {
            Coordinate[] slideCoordinates = readSlideCoordinates(slides);
            java.awt.Point[] numberCoordinates = readNumberCoordinates(slides);

            List<Match> matches = checkMatches(slideCoordinates, numberCoordinates);
            outputWriter.printLine(String.format("Heap %d", heapId));
            if (matches.isEmpty()) {
                outputWriter.printLine("none\n");
            } else {
                outputWriter.print(String.format("(%c,%d)", matches.get(0).letter, matches.get(0).numberId));
                for (int i = 1; i < matches.size(); i++) {
                    outputWriter.print(String.format(" (%c,%d)", matches.get(i).letter, matches.get(i).numberId));
                }
                outputWriter.printLine("\n");
            }
            heapId++;
            slides = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Coordinate[] readSlideCoordinates(int slides) throws IOException {
        Coordinate[] coordinates = new Coordinate[slides];

        for (int i = 0; i < coordinates.length; i++) {
            int[] xCoordinates = new int[4];
            int[] yCoordinates = new int[4];

            // Read coordinates in clockwise order
            xCoordinates[0] = FastReader.nextInt();
            xCoordinates[1] = xCoordinates[0];
            xCoordinates[2] = FastReader.nextInt();
            xCoordinates[3] = xCoordinates[2];

            yCoordinates[0] = FastReader.nextInt();
            yCoordinates[1] = FastReader.nextInt();
            yCoordinates[2] = yCoordinates[1];
            yCoordinates[3] = yCoordinates[0];

            coordinates[i] = new Coordinate(xCoordinates, yCoordinates);
        }
        return coordinates;
    }

    private static java.awt.Point[] readNumberCoordinates(int slides) throws IOException {
        java.awt.Point[] coordinates = new java.awt.Point[slides];

        for (int i = 0; i < coordinates.length; i++) {
            int xCoordinate = FastReader.nextInt();
            int yCoordinate = FastReader.nextInt();
            coordinates[i] = new java.awt.Point(xCoordinate, yCoordinate);
        }
        return coordinates;
    }

    private static List<Match> checkMatches(Coordinate[] slideCoordinates, java.awt.Point[] numberCoordinates) {
        List<Integer>[] adjacencyList = new List[slideCoordinates.length + numberCoordinates.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int slideId = 0; slideId < slideCoordinates.length; slideId++) {
            for (int numberId = 0; numberId < numberCoordinates.length; numberId++) {
                if (isPointInPolygon(slideCoordinates[slideId], numberCoordinates[numberId])) {
                    int numberVertexId = slideCoordinates.length + numberId;
                    adjacencyList[slideId].add(numberVertexId);
                }
            }
        }

        int matches = computeMaximumCardinality(adjacencyList, slideCoordinates.length, null);
        List<Match> matchList = new ArrayList<>();

        for (int slideId = 0; slideId < slideCoordinates.length; slideId++) {
            for (int numberVertexId : adjacencyList[slideId]) {
                Edge edgeToSkip = new Edge(slideId, numberVertexId);
                int matchesWithoutEdge = computeMaximumCardinality(adjacencyList, slideCoordinates.length, edgeToSkip);

                if (matches != matchesWithoutEdge) {
                    char slideLabel = (char) ('A' + slideId);
                    int numberId = numberVertexId - slideCoordinates.length + 1;
                    matchList.add(new Match(slideLabel, numberId));
                }
            }
        }
        Collections.sort(matchList);
        return matchList;
    }

    private static int computeMaximumCardinality(List<Integer>[] adjacencyList, int leftPartitionVertexIDs,
                                                 Edge edgeToSkip) {
        int maximumMatches = 0;
        int[] match = new int[adjacencyList.length];
        Arrays.fill(match, -1);

        for (int vertexID = 0; vertexID < leftPartitionVertexIDs; vertexID++) {
            boolean[] visited = new boolean[adjacencyList.length];
            maximumMatches += tryToMatch(adjacencyList, match, visited, edgeToSkip, vertexID);
        }
        return maximumMatches;
    }

    private static int tryToMatch(List<Integer>[] adjacencyList, int[] match, boolean[] visited, Edge edgeToSkip,
                                  int vertexID) {
        if (visited[vertexID]) {
            return 0;
        }
        visited[vertexID] = true;

        for (int neighbor : adjacencyList[vertexID]) {
            if (edgeToSkip != null && edgeToSkip.slideId == vertexID && edgeToSkip.numberVertexId == neighbor) {
                continue;
            }
            if (match[neighbor] == -1 || tryToMatch(adjacencyList, match, visited, edgeToSkip, match[neighbor]) == 1) {
                match[neighbor] = vertexID;
                return 1;
            }
        }
        return 0;
    }

    private static boolean isPointInPolygon(Coordinate polygonCoordinates, java.awt.Point point) {
        Polygon polygon = new Polygon(polygonCoordinates.xCoordinates, polygonCoordinates.yCoordinates,
                polygonCoordinates.xCoordinates.length);
        return polygon.contains(point);
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
