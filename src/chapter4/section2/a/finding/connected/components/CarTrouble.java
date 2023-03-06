package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/03/23.
 */
@SuppressWarnings("unchecked")
public class CarTrouble {

    private static class Result {
        List<Integer> trapped;
        List<Integer> unreachable;

        public Result(List<Integer> trapped, List<Integer> unreachable) {
            this.trapped = trapped;
            this.unreachable = unreachable;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int streets = FastReader.nextInt();
        List<Integer>[] adjacencyList = new List[1001];
        List<Integer>[] transposedAdjacencyList = new List[1001];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
            transposedAdjacencyList[i] = new ArrayList<>();
        }
        int[] streetInputOrder = new int[streets];

        for (int s = 0; s < streets; s++) {
            int id = FastReader.nextInt();
            streetInputOrder[s] = id;
            int connectedStreets = FastReader.nextInt();

            for (int i = 0; i < connectedStreets; i++) {
                int neighbor = FastReader.nextInt();
                adjacencyList[id].add(neighbor);
                transposedAdjacencyList[neighbor].add(id);
            }
        }

        Result result = analyzeCities(adjacencyList, transposedAdjacencyList, streetInputOrder);
        if (result.trapped.isEmpty() && result.unreachable.isEmpty()) {
            outputWriter.printLine("NO PROBLEMS");
        } else {
            for (int streetID : result.trapped) {
                outputWriter.printLine("TRAPPED " + streetID);
            }
            for (int streetID : result.unreachable) {
                outputWriter.printLine("UNREACHABLE " + streetID);
            }
        }
        outputWriter.flush();
    }

    private static Result analyzeCities(List<Integer>[] adjacencyList, List<Integer>[] transposedAdjacencyList,
                                        int[] streetInputOrder) {
        List<Integer> unreachable = computeUnreachable(adjacencyList, streetInputOrder);
        List<Integer> trapped = computeUnreachable(transposedAdjacencyList, streetInputOrder);
        return new Result(trapped, unreachable);
    }

    private static List<Integer> computeUnreachable(List<Integer>[] adjacencyList, int[] streetInputOrder) {
        List<Integer> unreachable = new ArrayList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        depthFirstSearch(adjacencyList, visited, 0);

        for (int streetID : streetInputOrder) {
            if (!visited[streetID]) {
                unreachable.add(streetID);
            }
        }
        return unreachable;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int vertex) {
        visited[vertex] = true;
        for (int neighbor : adjacencyList[vertex]) {
            if (!visited[neighbor]) {
                depthFirstSearch(adjacencyList, visited, neighbor);
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