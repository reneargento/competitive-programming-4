package chapter4.section5.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 29/03/24.
 */
public class AsterixAndObelix {

    private static final int INFINITE = 1000000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();
        int roads = FastReader.nextInt();
        int queries = FastReader.nextInt();
        int caseID = 1;

        while (cities != 0 || roads != 0 || queries != 0) {
            long[][] costs = new long[cities][cities];
            for (int cityID = 0; cityID < costs.length; cityID++) {
                Arrays.fill(costs[cityID], INFINITE);
                costs[cityID][cityID] = 0;
            }
            int[] feastCosts = new int[cities];
            for (int i = 0; i < feastCosts.length; i++) {
                feastCosts[i] = FastReader.nextInt();
            }
            int[][] highestFeastCost = new int[cities][cities];

            for (int r = 0; r < roads; r++) {
                int cityID1 = FastReader.nextInt() - 1;
                int cityID2 = FastReader.nextInt() - 1;
                int cost = FastReader.nextInt();
                costs[cityID1][cityID2] = cost;
                costs[cityID2][cityID1] = cost;

                int maxFeastCost = Math.max(feastCosts[cityID1], feastCosts[cityID2]);
                highestFeastCost[cityID1][cityID2] = maxFeastCost;
                highestFeastCost[cityID2][cityID1] = maxFeastCost;
            }
            // Need to run Floyd-Warshall twice due to highestFeastCost values
            floydWarshall(costs, highestFeastCost);
            floydWarshall(costs, highestFeastCost);

            if (caseID > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Case #%d", caseID));
            for (int q = 0; q < queries; q++) {
                int cityID1 = FastReader.nextInt() - 1;
                int cityID2 = FastReader.nextInt() - 1;
                long totalCost;
                if (costs[cityID1][cityID2] == INFINITE) {
                    totalCost = -1;
                } else if (cityID1 == cityID2) {
                    totalCost = feastCosts[cityID1];
                } else {
                    totalCost = costs[cityID1][cityID2] + highestFeastCost[cityID1][cityID2];
                }
                outputWriter.printLine(totalCost);
            }

            caseID++;
            cities = FastReader.nextInt();
            roads = FastReader.nextInt();
            queries = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void floydWarshall(long[][] costs, int[][] highestFeastCost) {
        for (int vertex1 = 0; vertex1 < costs.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < costs.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < costs.length; vertex3++) {
                    int candidateMaxFeastCost = Math.max(highestFeastCost[vertex2][vertex1],
                            highestFeastCost[vertex1][vertex3]);
                    long candidateTotalCost = costs[vertex2][vertex1] + costs[vertex1][vertex3] + candidateMaxFeastCost;
                    long currentTotalCost = costs[vertex2][vertex3] + highestFeastCost[vertex2][vertex3];

                    if (currentTotalCost > candidateTotalCost) {
                        costs[vertex2][vertex3] = costs[vertex2][vertex1] + costs[vertex1][vertex3];
                        highestFeastCost[vertex2][vertex3] = candidateMaxFeastCost;
                    }
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

        public void flush() {
            writer.flush();
        }
    }
}
