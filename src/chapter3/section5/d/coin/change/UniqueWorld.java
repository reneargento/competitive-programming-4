package chapter3.section5.d.coin.change;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/11/22.
 */
@SuppressWarnings("unchecked")
public class UniqueWorld {

    private static class Road {
        int destination;
        int cost;

        public Road(int destination, int cost) {
            this.destination = destination;
            this.cost = cost;
        }
    }

    private static final int INFINITE = 1000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int unika = FastReader.nextInt();
            List<Road>[] roads = new List[unika + 1];
            for (int i = 0; i < roads.length; i++) {
                roads[i] = new ArrayList<>();
            }

            int roadsNumber = FastReader.nextInt();
            for (int i = 0; i < roadsNumber; i++) {
                int source = FastReader.nextInt();
                int destination = FastReader.nextInt();
                int cost = FastReader.nextInt();

                roads[source].add(new Road(destination, cost));
                roads[destination].add(new Road(source, cost));
            }

            if (t > 0) {
                outputWriter.printLine();
            }

            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int source = FastReader.nextInt();
                int destination = FastReader.nextInt();
                int targetCost = FastReader.nextInt();

                int roadsInPath = countRoadsInPath(unika, roads, source, destination, targetCost);
                if (roadsInPath != -1) {
                    outputWriter.printLine(String.format("Yes %d", roadsInPath));
                } else {
                    outputWriter.printLine("No");
                }
            }
        }
        outputWriter.flush();
    }

    private static int countRoadsInPath(int unika, List<Road>[] roads, int source, int destination, int targetCost) {
        List<Integer> costsInPath = new ArrayList<>();
        boolean[] visited = new boolean[unika + 1];
        boolean hasPath = computePath(destination, roads, visited, costsInPath, source);

        if (!hasPath) {
            return -1;
        }
        int pathCost = computePathCost(costsInPath);
        int remainingCost = targetCost - pathCost;

        if (remainingCost < 0 || remainingCost % 2 != 0) {
            return -1;
        }
        if (remainingCost == 0) {
            return costsInPath.size();
        }
        if (costsInPath.size() == 1) {
            return -1;
        }

        int roadsUsed = costsInPath.size();
        // Remove last road
        costsInPath.remove(0);
        // Divide by 2 to be able to add an even number of roads
        remainingCost /= 2;

        int[] dp = new int[remainingCost + 1];
        Arrays.fill(dp, INFINITE);
        dp[0] = 0;

        for (int road : costsInPath) {
            for (int cost = road; cost < dp.length; cost++) {
                int roadsUsedCurrently = dp[cost];
                int roadsUsedAlternate = dp[cost - road] + 1;
                dp[cost] = Math.min(roadsUsedCurrently, roadsUsedAlternate);
            }
        }

        if (dp[remainingCost] == INFINITE) {
            return -1;
        }
        return dp[remainingCost] * 2 + roadsUsed;
    }

    private static boolean computePath(int target, List<Road>[] roads, boolean[] visited, List<Integer> costsInPath,
                                       int vertex) {
        if (target == vertex) {
            return true;
        }
        visited[vertex] = true;

        for (Road road : roads[vertex]) {
            if (!visited[road.destination]
                    && computePath(target, roads, visited, costsInPath, road.destination)) {
                costsInPath.add(road.cost);
                return true;
            }
        }
        return false;
    }

    private static int computePathCost(List<Integer> costsInPath) {
        int pathCost = 0;
        for (int cost : costsInPath) {
            pathCost += cost;
        }
        return pathCost;
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
