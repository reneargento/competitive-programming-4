package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/01/24.
 */
public class TideGoesInTideGoesOut {

    private static class State implements Comparable<State> {
        int row;
        int column;
        double timePassed;
        boolean tideDropping;

        public State(int row, int column, double timePassed, boolean tideDropping) {
            this.row = row;
            this.column = column;
            this.timePassed = timePassed;
            this.tideDropping = tideDropping;
        }

        @Override
        public int compareTo(State other) {
            return Double.compare(timePassed, other.timePassed);
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
    
        for (int t = 1; t <= tests; t++) {
            int waterLevelHeight = FastReader.nextInt();
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();

            int[][] ceilingHeights = new int[rows][columns];
            int[][] floorHeights = new int[rows][columns];
            readHeights(ceilingHeights);
            readHeights(floorHeights);

            double exitTime = computeExitTime(ceilingHeights, floorHeights, waterLevelHeight);
            outputWriter.printLine(String.format("Case #%d: %.1f", t, exitTime));
        }
        outputWriter.flush();
    }

    private static double computeExitTime(int[][] ceilingHeights, int[][] floorHeights, int waterLevelHeight) {
        double[][] bestTimes = new double[ceilingHeights.length][ceilingHeights[0].length];
        for (double[] values : bestTimes) {
            Arrays.fill(values, Double.POSITIVE_INFINITY);
        }
        bestTimes[0][0] = 0;

        PriorityQueue<State> priorityQueue = new PriorityQueue<>();
        State startState = new State(0, 0, 0, false);
        priorityQueue.offer(startState);

        while (!priorityQueue.isEmpty()) {
            State state = priorityQueue.poll();
            int row = state.row;
            int column = state.column;
            double waterLevel = Math.max(waterLevelHeight - (10 * state.timePassed), 0);

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];
                boolean tideDropping = state.tideDropping;

                if (isValid(ceilingHeights, neighborRow, neighborColumn)) {
                    int adjacentCeilingHeight = ceilingHeights[neighborRow][neighborColumn];
                    double waterCeilingDifference = adjacentCeilingHeight - waterLevel;
                    int currentFloorCeilingDifference = adjacentCeilingHeight - floorHeights[row][column];
                    int adjacentFloorCeilingDifference = adjacentCeilingHeight - floorHeights[neighborRow][neighborColumn];
                    int heightFloorDifference = ceilingHeights[row][column] - floorHeights[neighborRow][neighborColumn];

                    if (currentFloorCeilingDifference >= 50
                            && adjacentFloorCeilingDifference >= 50
                            && heightFloorDifference >= 50) {
                        double nextTime = state.timePassed;
                        double currentWaterLevel = waterLevel;
                        if (waterCeilingDifference < 50) {
                            double waterDropNeeded = 50 - waterCeilingDifference;
                            double timeToWait = waterDropNeeded / 10.0;
                            nextTime += timeToWait;
                            currentWaterLevel -= waterDropNeeded;

                            if (!tideDropping) {
                                tideDropping = true;
                            }
                        }

                        if (tideDropping) {
                            if (currentWaterLevel - floorHeights[row][column] >= 20) {
                                nextTime += 1;
                            } else {
                                nextTime += 10;
                            }
                        }

                        if (nextTime < bestTimes[neighborRow][neighborColumn]) {
                            bestTimes[neighborRow][neighborColumn] = nextTime;
                            State nextState = new State(neighborRow, neighborColumn, nextTime, tideDropping);
                            priorityQueue.offer(nextState);
                        }
                    }
                }
            }
        }
        return bestTimes[bestTimes.length - 1][bestTimes[0].length - 1];
    }

    private static void readHeights(int[][] heights) throws IOException {
        for (int row = 0; row < heights.length; row++) {
            for (int column = 0; column < heights[0].length; column++) {
                heights[row][column] = FastReader.nextInt();
            }
        }
    }

    private static boolean isValid(int[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;
    
        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }
    
        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
