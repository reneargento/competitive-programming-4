package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/01/23.
 */
@SuppressWarnings("unchecked")
public class Railroads {

    private static class Stop {
        int time;
        int cityID;

        public Stop(int time, int cityID) {
            this.time = time;
            this.cityID = cityID;
        }
    }

    private static class Connection {
        int departureTime;
        int arrivalTime;
        int cityID;

        public Connection(int departureTime, int arrivalTime, int cityID) {
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
            this.cityID = cityID;
        }
    }

    private static class Result {
        int departureTimestamp;
        int arrivalTimestamp;

        public Result(int departureTimestamp, int arrivalTimestamp) {
            this.departureTimestamp = departureTimestamp;
            this.arrivalTimestamp = arrivalTimestamp;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int scenarios = FastReader.nextInt();

        for (int scenarioID = 1; scenarioID <= scenarios; scenarioID++) {
            int cities = FastReader.nextInt();
            Map<String, Integer> cityNameToIdMap = new HashMap<>();
            for (int i = 0; i < cities; i++) {
                String cityName = FastReader.next();
                cityNameToIdMap.put(cityName, i);
            }

            List<Connection>[] connections = new List[cities];
            for (int i = 0; i < connections.length; i++) {
                connections[i] = new ArrayList<>();
            }

            int trains = FastReader.nextInt();
            for (int t = 0; t < trains; t++) {
                int stations = FastReader.nextInt();
                Stop previousStop = null;

                for (int i = 0; i < stations; i++) {
                    int time = FastReader.nextInt();
                    String cityName = FastReader.next();
                    int cityID = cityNameToIdMap.get(cityName);
                    Stop stop = new Stop(time, cityID);
                    if (previousStop != null) {
                        connections[previousStop.cityID].add(new Connection(previousStop.time, time, cityID));
                    }
                    previousStop = stop;
                }
            }

            int earliestTime = FastReader.nextInt();
            String startCityName = FastReader.next();
            String endCityName = FastReader.next();
            int startCityID = cityNameToIdMap.getOrDefault(startCityName, -1);
            int endCityID = cityNameToIdMap.getOrDefault(endCityName, -1);

            Result result = null;
            if (startCityID != -1 && endCityID != -1) {
                result = computeTrip(connections, earliestTime, startCityID, endCityID);
            }

            outputWriter.printLine(String.format("Scenario %d", scenarioID));
            if (result != null) {
                outputWriter.printLine(String.format("%-9s %04d %s", "Departure", result.departureTimestamp, startCityName));
                outputWriter.printLine(String.format("%-9s %04d %s", "Arrival", result.arrivalTimestamp, endCityName));
            } else {
                outputWriter.printLine("No connection");
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static Result computeTrip(List<Connection>[] connections, int earliestTime, int startCityID, int endCityID) {
        // dp[time][cityID] = earliest arrival time
        int[][] dp = new int[2400][connections.length];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        int bestDeparture = -1;
        int bestArrival = Integer.MAX_VALUE;
        for (Connection connection : connections[startCityID]) {
            if (connection.departureTime >= earliestTime) {
                int arrival = computeTrip(connections, dp, endCityID, connection.cityID, connection.arrivalTime);
                if (arrival != Integer.MAX_VALUE) {
                    if (arrival < bestArrival
                            || (arrival == bestArrival && connection.departureTime > bestDeparture)) {
                        bestDeparture = connection.departureTime;
                        bestArrival = arrival;
                    }
                }
            }
        }
        if (bestDeparture != -1) {
            return new Result(bestDeparture, bestArrival);
        }
        return null;
    }

    private static int computeTrip(List<Connection>[] connections, int[][] dp, int endCityID, int cityID, int time) {
        if (cityID == endCityID) {
            return time;
        }
        if (dp[time][cityID] != -1) {
            return dp[time][cityID];
        }

        int bestArrival = Integer.MAX_VALUE;
        for (Connection connection : connections[cityID]) {
            if (connection.departureTime >= time) {
                int arrival = computeTrip(connections, dp, endCityID, connection.cityID, connection.arrivalTime);
                bestArrival = Math.min(bestArrival, arrival);
            }
        }
        dp[time][cityID] = bestArrival;
        return dp[time][cityID];
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
