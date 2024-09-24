package chapter4.section6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/05/24.
 */
@SuppressWarnings("unchecked")
public class AlwaysOnTheRun {

    private static class Flight {
        int cityID;
        long cost;

        public Flight(int cityID, long cost) {
            this.cityID = cityID;
            this.cost = cost;
        }
    }

    private static final long INFINITE = 100000000000L;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numberOfCities = FastReader.nextInt();
        int numberOfFlights = FastReader.nextInt();
        int scenarioId = 1;

        while (numberOfCities != 0 || numberOfFlights != 0) {
            List<Flight>[][] adjacencyList = new List[numberOfCities][];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new List[31];
                for (int day = 0; day < adjacencyList[i].length; day++) {
                    adjacencyList[i][day] = new ArrayList<>();
                }
            }
            int[][] daysPerCityPair = new int[numberOfCities][numberOfCities];

            for (int cityId1 = 0; cityId1 < adjacencyList.length; cityId1++) {
                for (int cityId2 = 0; cityId2 < adjacencyList.length; cityId2++) {
                    if (cityId1 == cityId2) {
                        continue;
                    }

                    int days = FastReader.nextInt();
                    daysPerCityPair[cityId1][cityId2] = days;
                    for (int day = 0; day < days; day++) {
                        int cost = FastReader.nextInt();
                        adjacencyList[cityId1][day].add(new Flight(cityId2, cost));
                    }
                }
            }

            // dp[cityId][day]
            long[][] dp = new long[numberOfCities][numberOfFlights + 1];
            for (long[] values : dp) {
                Arrays.fill(values, -1);
            }

            long bestFlightsCost = computeBestFlights(adjacencyList, daysPerCityPair, dp, numberOfFlights,0, 0);
            outputWriter.printLine(String.format("Scenario #%d", scenarioId));
            if (bestFlightsCost >= INFINITE) {
                outputWriter.printLine("No flight possible.");
            } else {
                outputWriter.printLine(String.format("The best flight costs %d.", bestFlightsCost));
            }
            outputWriter.printLine();

            scenarioId++;
            numberOfCities = FastReader.nextInt();
            numberOfFlights = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeBestFlights(List<Flight>[][] adjacencyList, int[][] daysPerCityPair, long[][] dp,
                                           int numberOfFlights, int cityId, int flightNumber) {
        if (flightNumber == numberOfFlights) {
            if (cityId == adjacencyList.length - 1) {
                return 0;
            } else {
                return INFINITE;
            }
        }

        if (dp[cityId][flightNumber] != -1) {
            return dp[cityId][flightNumber];
        }

        long bestCost = INFINITE;
        for (int nextCityID = 0; nextCityID < adjacencyList.length; nextCityID++) {
            if (nextCityID == cityId) {
                continue;
            }
            int totalDays = daysPerCityPair[cityId][nextCityID];
            if (totalDays == 0) {
                continue;
            }

            for (Flight flight : adjacencyList[cityId][flightNumber % totalDays]) {
                if (flight.cityID != nextCityID || flight.cost == 0) {
                    continue;
                }
                long cost = flight.cost + computeBestFlights(adjacencyList, daysPerCityPair, dp, numberOfFlights,
                        flight.cityID, flightNumber + 1);
                bestCost = Math.min(bestCost, cost);
            }
        }
        dp[cityId][flightNumber] = bestCost;
        return bestCost;
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
