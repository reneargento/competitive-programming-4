package chapter3.section5.e.traveling.salesman.problem;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/12/22.
 */
public class BusTour {

    private static final int INFINITE = 100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseId = 1;
        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int locations = Integer.parseInt(data[0]);
            int edgesNumber = Integer.parseInt(data[1]);
            int[][] edges = new int[locations][locations];
            for (int[] values : edges) {
                Arrays.fill(values, INFINITE);
            }
            for (int i = 0; i < edgesNumber; i++) {
                int locationId1 = FastReader.nextInt();
                int locationId2 = FastReader.nextInt();
                int time = FastReader.nextInt();
                edges[locationId1][locationId2] = time;
                edges[locationId2][locationId1] = time;
            }

            int shortestTourTime;
            if (locations == 3) {
                shortestTourTime = (edges[0][1] + edges[1][2]) * 2;
            } else {
                floydWarshall(edges);
                shortestTourTime = computeShortestTourTime(edges);
            }
            outputWriter.printLine(String.format("Case %d: %d", caseId, shortestTourTime));
            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void floydWarshall(int[][] edges) {
        for (int location1 = 0; location1 < edges.length; location1++) {
            for (int location2 = 0; location2 < edges.length; location2++) {
                if (location1 == location2) {
                    continue;
                }
                for (int location3 = 0; location3 < edges.length; location3++) {
                    if (edges[location2][location1] + edges[location1][location3] < edges[location2][location3]) {
                        edges[location2][location3] = edges[location2][location1] + edges[location1][location3];
                    }
                }
            }
        }
    }

    private static int computeShortestTourTime(int[][] edges) {
        int shortestTourTime = Integer.MAX_VALUE;
        int locations = edges.length;
        int hotels = edges.length - 2;
        int hotelsBitmaskSize = 1 << hotels;
        int bitmaskSize = 1 << locations;
        int halfHotels = hotels / 2;

        int[][] dpHeadquarters = initDp(locations, bitmaskSize);
        int[][] dpAttraction = initDp(locations, bitmaskSize);

        for (int bitmask = 0; bitmask < hotelsBitmaskSize; bitmask++) {
            if (countSetBits(bitmask) != halfHotels) {
                continue;
            }
            int complementBitmask = bitmask ^ (hotelsBitmaskSize - 1);
            for (int lastHotelFirstHalf = 1; lastHotelFirstHalf < edges.length - 1; lastHotelFirstHalf++) {
                for (int lastHalfHotelWayBack = 1; lastHalfHotelWayBack < edges.length - 1; lastHalfHotelWayBack++) {
                    if ((bitmask & (1 << (lastHotelFirstHalf - 1))) == 0
                            || (bitmask & (1 << (lastHalfHotelWayBack - 1))) == 0) {
                        continue;
                    }

                    int bitmaskWithoutHotel = bitmask ^ (1 << (lastHotelFirstHalf - 1));
                    int bitmaskWithoutReturnHotel = bitmask ^ (1 << (lastHalfHotelWayBack - 1));

                    int timeToMiddlePoint = computeShortestTimeToTarget(edges, dpHeadquarters, 0,
                            lastHotelFirstHalf, bitmaskWithoutHotel);
                    int timeToAttraction = computeShortestTimeToTarget(edges, dpAttraction,
                            edges.length - 1, lastHotelFirstHalf, complementBitmask);
                    int timeBackFromAttraction = computeShortestTimeToTarget(edges, dpAttraction,
                            edges.length - 1, lastHalfHotelWayBack, bitmaskWithoutReturnHotel);
                    int timeBackToHeadquarters = computeShortestTimeToTarget(edges, dpHeadquarters, 0,
                            lastHalfHotelWayBack, complementBitmask);

                    int totalTime = timeToMiddlePoint + timeToAttraction + timeBackFromAttraction + timeBackToHeadquarters;
                    shortestTourTime = Math.min(shortestTourTime, totalTime);
                }
            }
        }
        return shortestTourTime;
    }

    private static int computeShortestTimeToTarget(int[][] edges, int[][] dp, int targetLocation, int currentLocationId,
                                                   int endStateBitmask) {
        if (endStateBitmask == 0) {
            dp[currentLocationId][endStateBitmask] = edges[currentLocationId][targetLocation];
            return dp[currentLocationId][endStateBitmask];
        }
        if (dp[currentLocationId][endStateBitmask] != -1) {
            return dp[currentLocationId][endStateBitmask];
        }

        int shortestTime = INFINITE;
        for (int locationId = 0; locationId < edges.length; locationId++) {
            int offsetLocationId = locationId + 1;
            if ((endStateBitmask & (1 << locationId)) > 0 && edges[currentLocationId][offsetLocationId] > 0) {
                int nextEndStateBitmask = endStateBitmask ^ (1 << locationId);
                int time = computeShortestTimeToTarget(edges, dp, targetLocation, offsetLocationId, nextEndStateBitmask);
                if (time != INFINITE) {
                    time += edges[currentLocationId][offsetLocationId];
                }
                shortestTime = Math.min(shortestTime, time);
            }
        }
        dp[currentLocationId][endStateBitmask] = shortestTime;
        return dp[currentLocationId][endStateBitmask];
    }

    private static int countSetBits(int bitmask) {
        int bitsSet = 0;
        while (bitmask > 0) {
            bitmask &= (bitmask - 1);
            bitsSet++;
        }
        return bitsSet;
    }

    private static int[][] initDp(int locations, int bitmaskSize) {
        int[][] dp = new int[locations][bitmaskSize];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return dp;
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
