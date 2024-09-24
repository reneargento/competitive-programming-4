package chapter4.section6.e.bipartite.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/08/24.
 */
@SuppressWarnings("unchecked")
public class Paintball {

    private static class Result {
        int maxCardinality;
        int[] matches;

        public Result(int maxCardinality, int[] matches) {
            this.maxCardinality = maxCardinality;
            this.matches = matches;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int players = FastReader.nextInt();
        int connections = FastReader.nextInt();

        List<Integer>[] adjacencyList = new List[players * 2];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < connections; i++) {
            int playerId1 = FastReader.nextInt() - 1;
            int playerId2 = FastReader.nextInt() - 1;
            int player1VertexId = players + playerId1;
            int player2VertexId = players + playerId2;

            adjacencyList[playerId1].add(player2VertexId);
            adjacencyList[playerId2].add(player1VertexId);
        }

        Result matchesResult = computeMaximumCardinality(adjacencyList, players);
        if (matchesResult.maxCardinality != players) {
            outputWriter.printLine("Impossible");
        } else {
            for (int playerId = 0; playerId < players; playerId++) {
                int match = matchesResult.matches[playerId] - players + 1;
                outputWriter.printLine(match);
            }
        }
        outputWriter.flush();
    }

    private static Result computeMaximumCardinality(List<Integer>[] adjacencyList, int leftPartitionMaxVertexID) {
        int maximumMatches = 0;
        int[] match = new int[adjacencyList.length];
        int[] reverseMatch = new int[adjacencyList.length];
        Arrays.fill(match, -1);

        for (int vertexID = 0; vertexID < leftPartitionMaxVertexID; vertexID++) {
            boolean[] visited = new boolean[adjacencyList.length];
            maximumMatches += tryToMatch(adjacencyList, match, reverseMatch, visited, vertexID);
        }
        return new Result(maximumMatches, reverseMatch);
    }

    private static int tryToMatch(List<Integer>[] adjacencyList, int[] match, int[] reverseMatch, boolean[] visited,
                                  int vertexID) {
        if (visited[vertexID]) {
            return 0;
        }
        visited[vertexID] = true;

        for (int neighbor : adjacencyList[vertexID]) {
            if (match[neighbor] == -1
                    || tryToMatch(adjacencyList, match, reverseMatch, visited, match[neighbor]) == 1) {
                match[neighbor] = vertexID; // flip status
                reverseMatch[vertexID] = neighbor;
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
