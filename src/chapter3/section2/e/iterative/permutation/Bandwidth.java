package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 29/11/21.
 */
public class Bandwidth {

    private static class Result {
        String ordering;
        int bandwith;

        public Result(String ordering, int bandwith) {
            this.ordering = ordering;
            this.bandwith = bandwith;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("#")) {
            String[] edges = line.split(";");
            Map<Character, String> adjacencyList = new HashMap<>();

            Set<Character> vertices = new HashSet<>();
            for (String edge : edges) {
                String[] vertexAndNeighbors = edge.split(":");
                char vertex = vertexAndNeighbors[0].charAt(0);

                adjacencyList.put(vertex, vertexAndNeighbors[1]);

                vertices.add(vertex);
                for (char neighbor : vertexAndNeighbors[1].toCharArray()) {
                    vertices.add(neighbor);
                }
            }
            char[] verticesArray = new char[vertices.size()];
            int verticesArrayIndex = 0;
            for (Character vertex : vertices) {
                verticesArray[verticesArrayIndex] = vertex;
                verticesArrayIndex++;
            }

            Result minimumBandwith = getMinimumBandwith(verticesArray, adjacencyList);
            for (char vertex : minimumBandwith.ordering.toCharArray()) {
                outputWriter.print(vertex + " ");
            }
            outputWriter.printLine(String.format("-> %d", minimumBandwith.bandwith));

            line = FastReader.getLine();
        }

        outputWriter.flush();
    }

    private static Result getMinimumBandwith(char[] vertices, Map<Character, String> adjacencyList) {
        char[] order = new char[vertices.length];
        return getMinimumBandwith(vertices, adjacencyList, order, 0, 0);
    }

    private static Result getMinimumBandwith(char[] vertices, Map<Character, String> adjacencyList,
                                             char[] order, int index, int mask) {
        if (mask == (1 << vertices.length) - 1) {
            int bandwith = getOrderingBandwith(adjacencyList, order);
            return new Result(new String(order), bandwith);
        }

        Result bestResult = null;

        for (int i = 0; i < vertices.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                order[index] = vertices[i];
                Result result = getMinimumBandwith(vertices, adjacencyList, order, index + 1, newMask);
                if (bestResult == null
                        || bestResult.bandwith > result.bandwith
                        || (bestResult.bandwith == result.bandwith
                             && bestResult.ordering.compareTo(result.ordering) > 0)) {
                    bestResult = result;
                }
            }
        }
        return bestResult;
    }

    private static int getOrderingBandwith(Map<Character, String> adjacencyList, char[] order) {
        int bandwith = 0;

        for (int i = 0; i < order.length; i++) {
            char vertex = order[i];
            String edges = adjacencyList.get(vertex);
            if (edges == null) {
                continue;
            }

            for (int j = 0; j < order.length; j++) {
                if (edges.contains(order[j] + "")) {
                    int currentBandwith = Math.abs(i - j);
                    bandwith = Math.max(bandwith, currentBandwith);
                }
            }
        }
        return bandwith;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
