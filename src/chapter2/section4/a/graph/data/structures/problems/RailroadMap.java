package chapter2.section4.a.graph.data.structures.problems;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/07/21.
 */
@SuppressWarnings("unchecked")
public class RailroadMap {

    private static class MapResult {
        List<Edge>[] simplifiedMap;
        int railroads;

        public MapResult(List<Edge>[] simplifiedMap, int railroads) {
            this.simplifiedMap = simplifiedMap;
            this.railroads = railroads;
        }
    }

    private static class CompressionResult {
        int newSourceVertex;
        long extraLength;

        public CompressionResult(int newSourceVertex, long extraLength) {
            this.newSourceVertex = newSourceVertex;
            this.extraLength = extraLength;
        }
    }

    private static class Edge {
        int neighbor;
        long length;

        public Edge(int neighbor, long length) {
            this.neighbor = neighbor;
            this.length = length;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            int stations = FastReader.nextInt();
            int segments = FastReader.nextInt();

            List<Edge>[] adjacencyList = new List[stations + 1];
            for (int s = 0; s < segments; s++) {
                int station1 = FastReader.nextInt();
                int station2 = FastReader.nextInt();
                int length = FastReader.nextInt();

                if (adjacencyList[station1] == null) {
                    adjacencyList[station1] = new ArrayList<>();
                }
                if (adjacencyList[station2] == null) {
                    adjacencyList[station2] = new ArrayList<>();
                }

                adjacencyList[station1].add(new Edge(station2, length));
                adjacencyList[station2].add(new Edge(station1, length));
            }

            MapResult mapResult = simplifyMap(adjacencyList);
            outputWriter.printLine(mapResult.railroads);
            printMap(mapResult.simplifiedMap, outputWriter);
        }
        outputWriter.flush();
    }

    private static MapResult simplifyMap(List<Edge>[] adjacencyList) {
        List<Edge>[] simplifiedMap = new List[adjacencyList.length];
        for (int station = 1; station < simplifiedMap.length; station++) {
            simplifiedMap[station] = new ArrayList<>();
        }
        int railroads = 0;
        Set<Integer> verticesToSkip = new HashSet<>();

        for (int station = 1; station < adjacencyList.length; station++) {
            if (adjacencyList[station] == null || verticesToSkip.contains(station)) {
                continue;
            }
            if (adjacencyList[station].size() != 2) {
                for (Edge edge : adjacencyList[station]) {
                    if (adjacencyList[edge.neighbor] != null
                            && adjacencyList[edge.neighbor].size() != 2) {
                        simplifiedMap[station].add(edge);
                        railroads++;
                    }
                }
            } else {
                List<Edge> list = adjacencyList[station];
                int station1 = list.get(0).neighbor;
                int station2 = list.get(1).neighbor;
                long length = list.get(0).length + list.get(1).length;

                CompressionResult compressionResultRight = compress(adjacencyList, station2, station, verticesToSkip);
                CompressionResult compressionResultLeft = compress(adjacencyList, station1, station, verticesToSkip);

                int sourceVertex = compressionResultLeft.newSourceVertex;
                int targetVertex = compressionResultRight.newSourceVertex;
                length += compressionResultLeft.extraLength + compressionResultRight.extraLength;

                simplifiedMap[sourceVertex].add(new Edge(targetVertex, length));
                if (sourceVertex != targetVertex) {
                    simplifiedMap[targetVertex].add(new Edge(sourceVertex, length));
                }
                railroads += 2;
            }
        }
        return new MapResult(simplifiedMap, railroads / 2);
    }

    private static CompressionResult compress(List<Edge>[] adjacencyList, int station, int previousStation,
                                              Set<Integer> verticesToSkip) {
        long length = 0;

        while (adjacencyList[station] != null
                && adjacencyList[station].size() == 2
                && !verticesToSkip.contains(station)) {
            int stationNeighbor1 = adjacencyList[station].get(0).neighbor;
            int stationNeighbor2 = adjacencyList[station].get(1).neighbor;

            verticesToSkip.add(station);

            if (stationNeighbor1 != previousStation) {
                previousStation = station;
                length += adjacencyList[station].get(0).length;
                station = stationNeighbor1;
            } else {
                previousStation = station;
                length += adjacencyList[station].get(1).length;
                station = stationNeighbor2;
            }
        }
        return new CompressionResult(station, length);
    }

    private static void printMap(List<Edge>[] simplifiedMap, OutputWriter outputWriter) {
        for (int station = 1; station < simplifiedMap.length; station++) {
            for (Edge railroad : simplifiedMap[station]) {
                if (railroad.neighbor >= station) {
                    outputWriter.printLine(String.format("%d %d %d", station, railroad.neighbor, railroad.length));
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
