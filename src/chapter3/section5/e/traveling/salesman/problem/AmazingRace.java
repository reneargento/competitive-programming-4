package chapter3.section5.e.traveling.salesman.problem;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/12/22.
 */
public class AmazingRace {

    private static class Location {
        int points;
        int timeToComplete;
        int deadline;

        public Location(int points, int timeToComplete, int deadline) {
            this.points = points;
            this.timeToComplete = timeToComplete;
            this.deadline = deadline;
        }
    }

    private static class Solution {
        int maximumPoints;
        List<Integer> locationIndices;

        public Solution(int maximumPoints, List<Integer> locationIndices) {
            this.maximumPoints = maximumPoints;
            this.locationIndices = locationIndices;
        }
    }

    private static final int INFINITE = 1000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Location[] locations = new Location[FastReader.nextInt() + 2];
        int totalTime = FastReader.nextInt();

        for (int i = 0; i < locations.length - 2; i++) {
            locations[i] = new Location(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
        }
        locations[locations.length - 2] = new Location(0, 0, -1);
        locations[locations.length - 1] = new Location(0, 0, -1);

        int[][] timeToTravel = new int[locations.length][locations.length];
        for (int row = 0; row < timeToTravel.length; row++) {
            for (int column = 0; column < timeToTravel[0].length; column++) {
                timeToTravel[row][column] = FastReader.nextInt();
            }
        }
        Solution solution = computeStrategy(locations, totalTime, timeToTravel);
        if (solution.maximumPoints == INFINITE) {
            outputWriter.printLine("0");
        } else {
            outputWriter.printLine(solution.maximumPoints);
            if (solution.maximumPoints > 0) {
                List<Integer> indices = solution.locationIndices;
                outputWriter.print(indices.get(0));
                for (int i = 1; i < indices.size(); i++) {
                    outputWriter.print(" " + indices.get(i));
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static Solution computeStrategy(Location[] locations, int totalTime, int[][] timeToTravel) {
        int bitmaskSize = 1 << locations.length;
        int[][] dp = new int[locations.length][bitmaskSize];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return getSolution(locations, timeToTravel, dp, totalTime);
    }

    private static Solution getSolution(Location[] locations, int[][] timeToTravel, int[][] dp, int totalTime) {
        List<Integer> bestSolution = null;
        int maxPoints = 0;
        int endLocationIndex = locations.length - 1;
        int endBitmask = (1 << (locations.length - 2)) - 1;

        for (int bitmask = 0; bitmask <= endBitmask; bitmask++) {
            if (dp[endLocationIndex][bitmask] == -1) {
                computeMinimumTimes(locations, timeToTravel, dp, endLocationIndex, bitmask);
            }

            if (dp[endLocationIndex][bitmask] <= totalTime) {
                int points = 0;
                List<Integer> indices = new ArrayList<>();
                for (int locationId = 0; locationId < locations.length - 2; locationId++) {
                    if ((bitmask & (1 << locationId)) > 0) {
                        points += locations[locationId].points;
                        indices.add(locationId + 1);
                    }
                }

                if (points > maxPoints) {
                    maxPoints = points;
                    bestSolution = indices;
                }
            }
        }
        return new Solution(maxPoints, bestSolution);
    }

    private static int computeMinimumTimes(Location[] locations, int[][] timeToTravel, int[][] dp,
                                           int currentLocationId, int bitmask) {
        if (dp[currentLocationId][bitmask] != -1) {
            return dp[currentLocationId][bitmask];
        }
        if (currentLocationId == locations.length - 1 && bitmask == 0) {
            dp[currentLocationId][bitmask] = timeToTravel[locations.length - 2][currentLocationId];
            return dp[currentLocationId][bitmask];
        }

        // Is at the end
        if (currentLocationId == locations.length - 1) {
            int minimumTime = INFINITE;
            for (int locationId = 0; locationId < locations.length; locationId++) {
                if ((bitmask & (1 << locationId)) > 0) {
                    int time = computeMinimumTimes(locations, timeToTravel, dp, locationId, bitmask)
                            + timeToTravel[locationId][currentLocationId];
                    minimumTime = Math.min(minimumTime, time);
                }
            }
            dp[currentLocationId][bitmask] = minimumTime;
            return dp[currentLocationId][bitmask];
        }

        int deadline = locations[currentLocationId].deadline;
        // Is at the start
        if (hasOnlyOneBitSet(bitmask)) {
            int time = timeToTravel[locations.length - 2][currentLocationId]
                    + locations[currentLocationId].timeToComplete;
            if (deadline == -1 || time <= deadline) {
                dp[currentLocationId][bitmask] = time;
            } else {
                dp[currentLocationId][bitmask] = INFINITE;
            }
            return dp[currentLocationId][bitmask];
        }

        // Is at the middle
        int minimumTime = INFINITE;
        int nextBitmask = bitmask ^ (1 << currentLocationId);
        for (int locationId = 0; locationId < locations.length; locationId++) {
            if (locationId == currentLocationId) {
                continue;
            }
            if ((bitmask & (1 << locationId)) > 0) {
                int time = computeMinimumTimes(locations, timeToTravel, dp, locationId, nextBitmask)
                        + locations[currentLocationId].timeToComplete
                        + timeToTravel[locationId][currentLocationId];
                if (deadline == -1 || time <= deadline) {
                    minimumTime = Math.min(minimumTime, time);
                }
            }
        }
        dp[currentLocationId][bitmask] = minimumTime;
        return dp[currentLocationId][bitmask];
    }

    private static boolean hasOnlyOneBitSet(int bitmask) {
        return (bitmask & (bitmask - 1)) == 0;
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
