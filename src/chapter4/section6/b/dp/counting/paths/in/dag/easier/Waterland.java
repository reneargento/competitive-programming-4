package chapter4.section6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/05/24.
 */
@SuppressWarnings("unchecked")
public class Waterland {

    private static class Result {
        int teamsRequired;
        int teamsReachedMeetingPlace;

        public Result(int teamsRequired, int teamsReachedMeetingPlace) {
            this.teamsRequired = teamsRequired;
            this.teamsReachedMeetingPlace = teamsReachedMeetingPlace;
        }
    }

    private static final int MOD = 100000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            int tunnels = FastReader.nextInt();
            for (int i = 0; i < tunnels; i++) {
                int islandID1 = FastReader.nextInt() - 1;
                int islandID2 = FastReader.nextInt() - 1;
                adjacencyList[islandID1].add(islandID2);
            }

            Result result = countTeams(adjacencyList);
            outputWriter.printLine(String.format("Case %d: %d %d", t, result.teamsRequired,
                    result.teamsReachedMeetingPlace));
        }
        outputWriter.flush();
    }

    private static Result countTeams(List<Integer>[] adjacencyList) {
        int[] numberOfIslandsInPath = new int[adjacencyList.length];
        int[] numberOfPaths = NumberOfPaths.countNumberOfPaths(adjacencyList, numberOfIslandsInPath);
        int yablonoiIslandID = adjacencyList.length - 1;
        return new Result(numberOfIslandsInPath[yablonoiIslandID], numberOfPaths[yablonoiIslandID]);
    }

    private static class NumberOfPaths {

        public static int[] countNumberOfPaths(List<Integer>[] adjacencyList, int[] numberOfIslands) {
            int[] numberOfPaths = new int[adjacencyList.length];
            numberOfPaths[0] = 1;

            int[] inDegrees = computeInDegrees(adjacencyList);
            int[] topologicalOrder = khanTopologicalSort(adjacencyList, inDegrees);

            for (int vertexID : topologicalOrder) {
                for (int neighbor : adjacencyList[vertexID]) {
                    numberOfPaths[neighbor] += numberOfPaths[vertexID];
                    numberOfPaths[neighbor] %= MOD;
                    numberOfIslands[neighbor] += numberOfIslands[vertexID] + numberOfPaths[vertexID];
                    numberOfIslands[neighbor] %= MOD;
                }
            }
            return numberOfPaths;
        }

        private static int[] computeInDegrees(List<Integer>[] adjacencyList) {
            int[] inDegrees = new int[adjacencyList.length];

            for (int i = 0; i < adjacencyList.length; i++) {
                for (int neighbor : adjacencyList[i]) {
                    inDegrees[neighbor]++;
                }
            }
            return inDegrees;
        }

        private static int[] khanTopologicalSort(List<Integer>[] adjacencyList, int[] inDegrees) {
            int[] inDegreesCopy = new int[inDegrees.length];
            System.arraycopy(inDegrees, 0, inDegreesCopy, 0, inDegrees.length);

            Queue<Integer> queue = new LinkedList<>();
            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                if (inDegreesCopy[vertexID] == 0) {
                    queue.add(vertexID);
                }
            }


            int[] topologicalOrder = new int[adjacencyList.length];
            int topologicalOrderIndex = 0;

            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();
                topologicalOrder[topologicalOrderIndex++] = currentVertex;

                for (int neighbor : adjacencyList[currentVertex]) {
                    inDegreesCopy[neighbor]--;

                    if (inDegreesCopy[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
            return topologicalOrder;
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
