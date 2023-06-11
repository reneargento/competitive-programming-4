package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 02/06/23.
 */
// Submitted at https://dmoj.ca/problem/ioi11p1io
@SuppressWarnings("unchecked")
public class TropicalGarden {

    private static OutputWriter outputWriter;

    public static void main(String[] args) throws IOException {
        FastReader fastReader = new FastReader();
        outputWriter = new OutputWriter(System.out);

        int numberOfFountains = fastReader.nextInt();
        int numberOfTrails = fastReader.nextInt();
        int restaurantFountainID = fastReader.nextInt();

        int[][] trails = new int[numberOfTrails][2];
        for (int i = 0; i < trails.length; i++) {
            trails[i][0] = fastReader.nextInt();
            trails[i][1] = fastReader.nextInt();
        }
        int groups = fastReader.nextInt();
        int[] trailsNeeded = new int[groups];
        for (int i = 0; i < trailsNeeded.length; i++) {
            trailsNeeded[i] = fastReader.nextInt();
        }

        countRoutes(numberOfFountains, numberOfTrails, restaurantFountainID, trails, groups, trailsNeeded);
        outputWriter.flush();
    }

    public static void countRoutes(int numberOfFountains, int numberOfTrails, int restaurantFountainID, int[][] trails,
                                   int groups, int[] trailsNeeded) {
        int[][][] distancesToRestaurant = new int[numberOfFountains][2][2];
        List<Integer>[] graph = new List[numberOfFountains];
        List<Integer>[] filteredGraph = new List[numberOfFountains];
        createGraphs(graph, filteredGraph, trails);

        int cycleLengthRouteType0 = computeCycleLength(filteredGraph, numberOfFountains, restaurantFountainID, 0);
        int cycleLengthRouteType1 = computeCycleLength(filteredGraph, numberOfFountains, restaurantFountainID, 1);

        breadthFirstSearch(graph, filteredGraph, distancesToRestaurant, restaurantFountainID, 0);
        breadthFirstSearch(graph, filteredGraph, distancesToRestaurant, restaurantFountainID, 1);

        int[] trailsPerDistanceRoute0 = new int[numberOfFountains * 2];
        int[] trailsPerDistanceRoute1 = new int[numberOfFountains * 2];

        for (int fountainID = 0; fountainID < numberOfFountains; fountainID++) {
            int distanceRoute0 = distancesToRestaurant[fountainID][0][0];
            trailsPerDistanceRoute0[distanceRoute0]++;

            int distanceRoute1 = distancesToRestaurant[fountainID][1][0];
            trailsPerDistanceRoute1[distanceRoute1]++;
        }

        int totalFountains = numberOfFountains * 2;
        if (cycleLengthRouteType0 != -1) {
            for (int distance = cycleLengthRouteType0 + 1; distance < totalFountains; distance++) {
                int distanceMinusCycle = distance - cycleLengthRouteType0;
                trailsPerDistanceRoute0[distance] += trailsPerDistanceRoute0[distanceMinusCycle];
            }
        }
        if (cycleLengthRouteType1 != -1) {
            for (int distance = cycleLengthRouteType1 + 1; distance < totalFountains; distance++) {
                int distanceMinusCycle = distance - cycleLengthRouteType1;
                trailsPerDistanceRoute1[distance] += trailsPerDistanceRoute1[distanceMinusCycle];
            }
        }

        for (int groupID = 0; groupID < trailsNeeded.length; groupID++) {
            if (groupID > 0) {
                outputWriter.print(" ");
            }

            int trailsRequired = trailsNeeded[groupID];
            int numberOfPossibleTrails;
            if (trailsRequired < totalFountains) {
                numberOfPossibleTrails = trailsPerDistanceRoute0[trailsRequired]
                        + trailsPerDistanceRoute1[trailsRequired];
            } else {
                numberOfPossibleTrails = computeTrails(trailsRequired, trailsPerDistanceRoute0, cycleLengthRouteType0,
                        totalFountains);
                numberOfPossibleTrails += computeTrails(trailsRequired, trailsPerDistanceRoute1, cycleLengthRouteType1,
                        totalFountains);
            }
            answer(numberOfPossibleTrails);
        }
    }

    private static int computeTrails(int trailsRequired, int[] trailsPerDistance, int cycleLength, int totalFountains) {
        int numberOfPossibleTrails = 0;
        if (cycleLength != -1) {
            int distance = totalFountains / cycleLength * cycleLength + (trailsRequired % cycleLength);
            if (distance >= totalFountains) {
                distance -= cycleLength;
            }
            numberOfPossibleTrails += trailsPerDistance[distance];
        }
        return numberOfPossibleTrails;
    }

    private static void createGraphs(List<Integer>[] graph, List<Integer>[] filteredGraph, int[][] trails) {
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
            filteredGraph[i] = new ArrayList<>();
        }

        for (int trailID = 0; trailID < trails.length; trailID++) {
            int fountainID1 = trails[trailID][0];
            int fountainID2 = trails[trailID][1];

            graph[fountainID1].add(fountainID2);
            graph[fountainID2].add(fountainID1);

            if (filteredGraph[fountainID1].size() < 2) {
                filteredGraph[fountainID1].add(fountainID2);
            }
            if (filteredGraph[fountainID2].size() < 2) {
                filteredGraph[fountainID2].add(fountainID1);
            }
        }
    }

    private static int computeCycleLength(List<Integer>[] filteredGraph, int numberOfFountains, int sourceFountainID,
                                          int sourceRouteType) {
        boolean[][] visited = new boolean[numberOfFountains][2];
        int cycleLength = 0;
        int currentFountainID = sourceFountainID;
        int currentRouteType = sourceRouteType;

        do {
            cycleLength++;
            int routeTypeValue = currentRouteType;
            visited[currentFountainID][routeTypeValue] = true;

            int nextRouteType;
            if (filteredGraph[currentFountainID].size() == 1 || currentRouteType == 0) {
                nextRouteType = 0;
            } else {
                nextRouteType = 1;
            }
            int nextFountainID = filteredGraph[currentFountainID].get(nextRouteType);
            currentRouteType = (currentFountainID == filteredGraph[nextFountainID].get(0)) ? 1 : 0;
            currentFountainID = nextFountainID;
        } while (!visited[currentFountainID][currentRouteType]);

        if (currentFountainID == sourceFountainID && currentRouteType == sourceRouteType) {
            return cycleLength;
        }
        return -1;
    }

    private static void breadthFirstSearch(List<Integer>[] graph, List<Integer>[] filteredGraph,
                                           int[][][] distancesToRestaurant, int sourceFountainID, int sourceRouteType) {
        boolean[][] visited = new boolean[graph.length][2];
        Deque<Integer> stack = new ArrayDeque<>();
        int fountainIDAndRouteType = (sourceFountainID << 1) | sourceRouteType;
        stack.add(fountainIDAndRouteType);

        while (!stack.isEmpty()) {
            int state = stack.pop();
            int currentFountainID = state >> 1;
            int currentRouteType = state & 1;

            for (int nextFountainID : graph[currentFountainID]) {
                if (currentRouteType == 0 && filteredGraph[currentFountainID].get(0) == nextFountainID) {
                    continue;
                }
                if (currentRouteType == 1 && filteredGraph[currentFountainID].get(0) != nextFountainID) {
                    continue;
                }

                if (graph[nextFountainID].size() == 1) {
                    updateDistance(distancesToRestaurant, visited, stack, sourceRouteType, nextFountainID,
                            currentFountainID, currentRouteType, 0);
                    updateDistance(distancesToRestaurant, visited, stack, sourceRouteType, nextFountainID,
                            currentFountainID, currentRouteType, 1);
                } else {
                    if (filteredGraph[nextFountainID].get(0) == currentFountainID) {
                        updateDistance(distancesToRestaurant, visited, stack, sourceRouteType, nextFountainID,
                                currentFountainID, currentRouteType, 0);
                    } else if (filteredGraph[nextFountainID].size() > 1
                            && filteredGraph[nextFountainID].get(1) == currentFountainID) {
                        updateDistance(distancesToRestaurant, visited, stack, sourceRouteType, nextFountainID,
                                currentFountainID, currentRouteType, 1);
                    }
                }
            }
        }
    }

    private static void updateDistance(int[][][] distancesToRestaurant, boolean[][] visited, Deque<Integer> stack,
                                       int sourceRouteType, int nextFountainID, int currentFountainID,
                                       int currentRouteType, int visitedRouteType) {
        if (!visited[nextFountainID][visitedRouteType]) {
            visited[nextFountainID][visitedRouteType] = true;
            distancesToRestaurant[nextFountainID][sourceRouteType][visitedRouteType] =
                    distancesToRestaurant[currentFountainID][sourceRouteType][currentRouteType] + 1;
            int nextState = nextFountainID << 1;
            if (visitedRouteType == 1) {
                nextState |= 1;
            }
            stack.push(nextState);
        }
    }

    public static void answer(int numberOfPossibleTrails) {
        outputWriter.print(numberOfPossibleTrails);
    }

    private static class FastReader {
        final InputStream in = System.in;
        final int bufferSize = 30000;
        byte[] buffer = new byte[bufferSize];
        int position = 0;
        int byteCount = bufferSize;
        byte character;

        FastReader() throws IOException {
            fill();
        }

        private void fill() throws IOException {
            byteCount = in.read(buffer, 0, bufferSize);
        }

        private int nextInt() throws IOException {
            while (character < '-') {
                character = readByte();
            }
            boolean isNegative = (character == '-');
            if (isNegative) {
                character = readByte();
            }
            int value = character - '0';
            while ((character = readByte()) >= '0' && character <= '9') {
                value = value * 10 + character - '0';
            }
            return isNegative ? -value : value;
        }

        private byte readByte() throws IOException {
            if (position == byteCount) {
                fill();
                position = 0;
            }
            return buffer[position++];
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
