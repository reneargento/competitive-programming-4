package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/02/22.
 */
@SuppressWarnings("unchecked")
public class AllWalksOfLengthNFromTheFirstNode {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int nodes = Integer.parseInt(data[0]);
            int walksLength = Integer.parseInt(data[1]);

            List<Integer>[] adjacencyList = new ArrayList[nodes + 1];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int vertex1 = 1; vertex1 < adjacencyList.length; vertex1++) {
                for (int vertex2 = 1; vertex2 < adjacencyList.length; vertex2++) {
                    int edge = FastReader.nextInt();
                    if (edge == 1 && vertex1 <= vertex2) {
                        adjacencyList[vertex1].add(vertex2);
                        adjacencyList[vertex2].add(vertex1);
                    }
                }
            }

            List<Integer[]> walks = computeWalks(walksLength, adjacencyList);
            if (walks.isEmpty()) {
                outputWriter.printLine(String.format("no walk of length %d", walksLength));
            } else {
                for (Integer[] walk : walks) {
                    outputWriter.print("(");
                    for (int i = 0; i < walk.length; i++) {
                        outputWriter.print(walk[i]);
                        if (i != walk.length - 1) {
                            outputWriter.print(",");
                        }
                    }
                    outputWriter.printLine(")");
                }
            }
            line = FastReader.getLine();
            if (line != null) {
                outputWriter.printLine();
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static List<Integer[]> computeWalks(int walksLength, List<Integer>[] adjacencyList) {
        List<Integer[]> walks = new ArrayList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        Integer[] currentVertices = new Integer[walksLength + 1];

        currentVertices[0] = 1;
        visited[1] = true;
        computeWalks(walksLength, adjacencyList, walks, 1, visited, currentVertices, 1);
        return walks;
    }

    private static void computeWalks(int walksLength, List<Integer>[] adjacencyList,
                                     List<Integer[]> walks, int vertex, boolean[] visited,
                                     Integer[] currentVertices, int currentWalkLength) {
        if (currentWalkLength == walksLength + 1) {
            Integer[] walk = new Integer[currentVertices.length];
            System.arraycopy(currentVertices, 0, walk, 0, currentVertices.length);
            walks.add(walk);
            return;
        }

        for (int neighbor : adjacencyList[vertex]) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                currentVertices[currentWalkLength] = neighbor;
                computeWalks(walksLength, adjacencyList, walks, neighbor, visited, currentVertices, currentWalkLength + 1);
                visited[neighbor] = false;
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
            while (!tokenizer.hasMoreTokens() ) {
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
