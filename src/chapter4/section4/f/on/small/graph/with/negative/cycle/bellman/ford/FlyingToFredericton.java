package chapter4.section4.f.on.small.graph.with.negative.cycle.bellman.ford;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 29/01/24.
 */
@SuppressWarnings("unchecked")
public class FlyingToFredericton {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int cities = FastReader.nextInt();
            int target = cities - 1;
            List<Edge>[] adjacencyList = new List[cities];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            Map<String, Integer> cityNameToID = new HashMap<>();
            for (int i = 0; i < cities; i++) {
                getCityID(cityNameToID, FastReader.next());
            }

            int flights = FastReader.nextInt();
            for (int i = 0; i < flights; i++) {
                int cityID1 = getCityID(cityNameToID, FastReader.next());
                int cityID2 = getCityID(cityNameToID, FastReader.next());
                int cost = FastReader.nextInt();
                adjacencyList[cityID1].add(new Edge(cityID2, cost));
            }

            long[][] costs = computeCosts(adjacencyList);

            if (t > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Scenario #%d", t));

            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int maxStopovers = Math.min(cities, FastReader.nextInt());
                long lowestCost = computeLowestCost(costs, target, maxStopovers);

                if (lowestCost != Long.MAX_VALUE) {
                    outputWriter.printLine(String.format("Total cost of flight(s) is $%d", lowestCost));
                } else {
                    outputWriter.printLine("No satisfactory flights");
                }
            }
        }
        outputWriter.flush();
    }

    private static long[][] computeCosts(List<Edge>[] adjacencyList) {
        // dp[stopovers][cityID]
        long[][] costs = new long[adjacencyList.length + 2][adjacencyList.length];
        for (long[] values : costs) {
            Arrays.fill(values, Long.MAX_VALUE);
        }
        costs[0][0] = 0;

        for (int stopovers = 0; stopovers < adjacencyList.length; stopovers++) {
            for (int cityID = 0; cityID < adjacencyList.length; cityID++) {
                if (costs[stopovers][cityID] != Long.MAX_VALUE) {
                    for (Edge edge : adjacencyList[cityID]) {
                        long candidateCost = costs[stopovers][cityID] + edge.distance;
                        if (candidateCost < costs[stopovers + 1][edge.vertex2]) {
                            costs[stopovers + 1][edge.vertex2] = candidateCost;
                        }
                    }
                }
            }
        }
        return costs;
    }

    private static long computeLowestCost(long[][] costs, int destinationID, int maxStopovers) {
        long lowestCost = Long.MAX_VALUE;
        for (int stopovers = 0; stopovers <= maxStopovers + 1; stopovers++) {
            lowestCost = Math.min(lowestCost, costs[stopovers][destinationID]);
        }
        return lowestCost;
    }

    private static int getCityID(Map<String, Integer> cityNameToID, String cityName) {
        if (!cityNameToID.containsKey(cityName)) {
            cityNameToID.put(cityName, cityNameToID.size());
        }
        return cityNameToID.get(cityName);
    }

    private static class Edge {
        private final int vertex2;
        private final long distance;

        public Edge(int vertex2, long distance) {
            this.vertex2 = vertex2;
            this.distance = distance;
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
