package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 17/03/24.
 */
// Based on the official solution in https://2015.bapc.eu/
public class Hotels {

    private static class Result implements Comparable<Result> {
        int numberOfStairs;
        int worstPossibleFloor;

        public Result(int numberOfStairs, int worstPossibleFloor) {
            this.numberOfStairs = numberOfStairs;
            this.worstPossibleFloor = worstPossibleFloor;
        }

        @Override
        public int compareTo(Result other) {
            if (numberOfStairs != other.numberOfStairs) {
                return Integer.compare(other.numberOfStairs, numberOfStairs);
            }
            return Integer.compare(worstPossibleFloor, other.worstPossibleFloor);
        }
    }

    private static class Vertex implements Comparable<Vertex> {
        int elevatorIndex;
        int floorNumber;

        public Vertex(int elevatorIndex, int floorNumber) {
            this.elevatorIndex = elevatorIndex;
            this.floorNumber = floorNumber;
        }

        @Override
        public int compareTo(Vertex other) {
            if (floorNumber != other.floorNumber) {
                return Integer.compare(floorNumber, other.floorNumber);
            }
            return Integer.compare(elevatorIndex, other.elevatorIndex);
        }
    }

    private static final int INFINITE = 1000000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int floors = FastReader.nextInt();
            int elevators = FastReader.nextInt();

            int[] remainder = new int[elevators + 1];
            int[] modulus = new int[elevators + 1];
            modulus[0] = floors;

            for (int i = 1; i <= elevators; i++) {
                remainder[i] = FastReader.nextInt();
                modulus[i] = FastReader.nextInt();
            }

            Result result = computeWorstFloor(elevators, floors, remainder, modulus);
            outputWriter.printLine(String.format("%d %d", result.numberOfStairs, result.worstPossibleFloor));
        }
        outputWriter.flush();
    }

    private static Result computeWorstFloor(int elevators, int floors, int[] remainder, int[] modulus) {
        int[][] distances = buildGraph(elevators, floors, remainder, modulus);
        floydWarshall(distances);

        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        for (int elevatorID = 0; elevatorID < remainder.length; elevatorID++) {
            Vertex vertex = new Vertex(elevatorID, remainder[elevatorID]);
            priorityQueue.offer(vertex);
        }

        Vertex previousElevator = new Vertex(-1, -1);
        Result result = new Result(0, 0);

        while (!priorityQueue.isEmpty()) {
            Vertex currentElevator = priorityQueue.poll();
            if (previousElevator.elevatorIndex != -1) {
                Result worstIntermediateFloor = findWorstIntermediateFloor(previousElevator.floorNumber,
                        currentElevator.floorNumber, distances[0][previousElevator.elevatorIndex],
                        distances[0][currentElevator.elevatorIndex]);
                if (result.compareTo(worstIntermediateFloor) > 0) {
                    result = worstIntermediateFloor;
                }
            }

            int elevatorNextStop = currentElevator.floorNumber + modulus[currentElevator.elevatorIndex];
            if (elevatorNextStop < floors) {
                priorityQueue.offer(new Vertex(currentElevator.elevatorIndex, elevatorNextStop));
            }
            previousElevator = currentElevator;
        }

        Result topFloorResult;
        if (previousElevator.elevatorIndex != -1) {
            int stairsDistanceFromTopFloor = stairsDistance(floors - 1, previousElevator.floorNumber,
                    distances[0][previousElevator.elevatorIndex]);
            topFloorResult = new Result(stairsDistanceFromTopFloor, floors - 1);
        } else {
            // There are no elevators
            topFloorResult = new Result(floors - 1, floors - 1);
        }
        if (result.compareTo(topFloorResult) > 0) {
            result = topFloorResult;
        }
        return result;
    }

    private static int[][] buildGraph(int elevators, int floors, int[] remainder, int[] modulus) {
        int[][] distances = new int[elevators + 1][elevators + 1];
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();

        for (int elevatorIndex = 0; elevatorIndex <= elevators; elevatorIndex++) {
            Arrays.fill(distances[elevatorIndex], INFINITE);
            distances[elevatorIndex][elevatorIndex] = 0;
            priorityQueue.offer(new Vertex(elevatorIndex, remainder[elevatorIndex]));
        }

        Vertex previousElevator = new Vertex(-1, -1);
        while (!priorityQueue.isEmpty()) {
            Vertex currentElevator = priorityQueue.poll();
            if (previousElevator.elevatorIndex != -1) {
                int elevatorID1 = previousElevator.elevatorIndex;
                int elevatorID2 = currentElevator.elevatorIndex;
                int distance = currentElevator.floorNumber - previousElevator.floorNumber;
                if (distance < distances[elevatorID1][elevatorID2]) {
                    distances[elevatorID1][elevatorID2] = distance;
                    distances[elevatorID2][elevatorID1] = distance;
                }
            }

            int elevatorNextStop = currentElevator.floorNumber + modulus[currentElevator.elevatorIndex];
            if (elevatorNextStop < floors) {
                priorityQueue.offer(new Vertex(currentElevator.elevatorIndex, elevatorNextStop));
            }
            previousElevator = currentElevator;
        }
        return distances;
    }

    private static void floydWarshall(int[][] distances) {
        int vertices = distances.length;

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                    }
                }
            }
        }
    }

    private static Result findWorstIntermediateFloor(int floorNumber1, int floorNumber2, int stairsRequiredFloor1,
                                                     int stairsRequiredFloor2) {
        // Edge cases
        int floorsDifference = floorNumber2 - floorNumber1;
        if (stairsRequiredFloor1 + floorsDifference <= stairsRequiredFloor2) {
            return new Result(stairsRequiredFloor2, floorNumber2);
        }
        if (stairsRequiredFloor2 + floorsDifference <= stairsRequiredFloor1) {
            return new Result(stairsRequiredFloor1, floorNumber1);
        }

        // Binary search
        int lowFloor = floorNumber1;
        int highFloor = floorNumber2;
        while (highFloor > lowFloor + 1) {
            int middleFloor = lowFloor + (highFloor - lowFloor) / 2;
            int stairsDistanceFromFloor1 = stairsDistance(middleFloor, floorNumber1, stairsRequiredFloor1);
            int stairsDistanceFromFloor2 = stairsDistance(middleFloor, floorNumber2, stairsRequiredFloor2);
            if (stairsDistanceFromFloor1 <= stairsDistanceFromFloor2) {
                lowFloor = middleFloor;
            } else {
                highFloor = middleFloor;
            }
        }

        int stairsDistanceFromWorstFloor = stairsDistance(lowFloor, floorNumber1, stairsRequiredFloor1);
        return new Result(stairsDistanceFromWorstFloor, lowFloor);
    }

    private static int stairsDistance(int floorNumber1, int floorNumber2, int stairsRequiredFloor2) {
        return stairsRequiredFloor2 + Math.abs(floorNumber2 - floorNumber1);
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
