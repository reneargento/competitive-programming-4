package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/01/23.
 */
@SuppressWarnings("unchecked")
public class IntrepidClimber {

    private static class Edge {
        int otherLandmarkId;
        int cost;

        public Edge(int otherLandmarkId, int cost) {
            this.otherLandmarkId = otherLandmarkId;
            this.cost = cost;
        }
    }

    private static class Costs {
        int totalEnergy;
        int maxReturnEnergy;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            List<Edge>[] adjacencyList = new List[Integer.parseInt(data[0]) + 1];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int friendsToVisit = Integer.parseInt(data[1]);
            for (int i = 0; i < adjacencyList.length - 2; i++) {
                int landmark1 = FastReader.nextInt();
                int landmark2 = FastReader.nextInt();
                int cost = FastReader.nextInt();
                adjacencyList[landmark1].add(new Edge(landmark2, cost));
            }
            boolean[] friendLocations = new boolean[adjacencyList.length];
            for (int i = 0; i < friendsToVisit; i++) {
                int location = FastReader.nextInt();
                friendLocations[location] = true;
            }

            Costs costs = new Costs();
            computeMinimumEnergyRequired(adjacencyList, friendLocations, costs, 1, 0);
            int minimumEnergyRequired = costs.totalEnergy - costs.maxReturnEnergy;
            outputWriter.printLine(minimumEnergyRequired);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean computeMinimumEnergyRequired(List<Edge>[] adjacencyList, boolean[] friendLocations,
                                                        Costs costs, int landmarkId, int currentCost) {
        boolean hasFriend = friendLocations[landmarkId];
        if (hasFriend) {
            costs.maxReturnEnergy = Math.max(costs.maxReturnEnergy, currentCost);
        }

        for (Edge edge : adjacencyList[landmarkId]) {
            boolean hasFriendInSubtree = computeMinimumEnergyRequired(adjacencyList, friendLocations, costs,
                    edge.otherLandmarkId, currentCost + edge.cost);
            if (hasFriendInSubtree) {
                costs.totalEnergy += edge.cost;
            }
            hasFriend = hasFriend || hasFriendInSubtree;
        }
        return hasFriend;
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

        public void flush() {
            writer.flush();
        }
    }
}
