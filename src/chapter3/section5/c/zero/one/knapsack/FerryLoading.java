package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 22/11/22.
 */
public class FerryLoading {

    private enum Location {
        PORT, STARBOARD, NONE
    }

    private enum Side {
        LEFT, RIGHT
    }

    private static class Result {
        int carsOnFerry;
        List<Location> carLocations;

        public Result(int carsOnFerry, List<Location> carLocations) {
            this.carsOnFerry = carsOnFerry;
            this.carLocations = carLocations;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = Integer.parseInt(FastReader.getLine());

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            int ferryLength = Integer.parseInt(FastReader.getLine()) * 100;
            List<Integer> carLengths = new ArrayList<>();
            int carLength = Integer.parseInt(FastReader.getLine());

            while (carLength != 0) {
                carLengths.add(carLength);
                carLength = Integer.parseInt(FastReader.getLine());
            }

            Result result = computeCarLocations(carLengths, ferryLength);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(result.carsOnFerry);
            for (Location location : result.carLocations) {
                if (location == Location.PORT) {
                    outputWriter.printLine("port");
                } else if (location == Location.STARBOARD) {
                    outputWriter.printLine("starboard");
                } else {
                    break;
                }
            }
        }
        outputWriter.flush();
    }

    private static Result computeCarLocations(List<Integer> carLengths, int ferryLength) {
        int carsOnFerry = 0;
        int maxLengthPossible = 0;

        int[][] dp = new int[carLengths.size() + 1][ferryLength + 1];
        Side[][] currentLocation = new Side[dp.length][dp[0].length];
        int[] prefixSum = computePrefixSum(carLengths);

        dp[0][0] = 1;
        for (int carId = 1; carId < dp.length; carId++) {
            int carLength = carLengths.get(carId - 1);
            for (int length = 1; length < dp[0].length; length++) {
                // Left
                if (length >= carLength && dp[carId - 1][length - carLength] > 0) {
                    dp[carId][length] = 1;
                    currentLocation[carId][length] = Side.LEFT;
                }
                // Right
                if (prefixSum[carId] - length <= ferryLength && dp[carId - 1][length] > 0) {
                    dp[carId][length] = 1;
                    currentLocation[carId][length] = Side.RIGHT;
                }

                if (dp[carId][length] == 1) {
                    carsOnFerry = carId;
                    maxLengthPossible = length;
                }
            }
            if (carsOnFerry != carId) {
                break;
            }
        }

        List<Location> carLocations = new ArrayList<>();
        int currentLength = maxLengthPossible;
        for (int carId = carsOnFerry; carId > 0; carId--) {
            if (currentLocation[carId][currentLength] == Side.LEFT) {
                carLocations.add(Location.PORT);
                currentLength -= carLengths.get(carId - 1);
            } else {
                carLocations.add(Location.STARBOARD);
            }
        }
        Collections.reverse(carLocations);
        return new Result(carsOnFerry, carLocations);
    }

    private static int[] computePrefixSum(List<Integer> carLengths) {
        int[] prefixSum = new int[carLengths.size() + 1];
        for (int i = 1; i <= carLengths.size(); i++) {
            prefixSum[i] = prefixSum[i - 1] + carLengths.get(i - 1);
        }
        return prefixSum;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
