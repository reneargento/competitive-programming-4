package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/03/22.
 */
public class RainFall {

    private static final double EPSILON = .000000001;

    private static class Result {
        double smallestRainfall;
        double largestRainfall;

        public Result(double smallestRainfall, double largestRainfall) {
            this.smallestRainfall = smallestRainfall;
            this.largestRainfall = largestRainfall;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            double leakMmLocation = FastReader.nextDouble();
            double waterLeakRate = FastReader.nextDouble();
            double rainfallDuration = FastReader.nextDouble();
            double timeBetweenRainEndAndObservation = FastReader.nextDouble();
            double waterMmLevelObserved = FastReader.nextDouble();

            Result rainfall = computeRainfall(leakMmLocation, waterLeakRate, rainfallDuration,
                    timeBetweenRainEndAndObservation, waterMmLevelObserved);
            outputWriter.printLine(String.format("%f %f", rainfall.smallestRainfall, rainfall.largestRainfall));
        }
        outputWriter.flush();
    }

    private static Result computeRainfall(double leakMmLocation, double waterLeakRate, double rainfallDuration,
                                          double timeBetweenRainEndAndObservation, double waterMmLevelObserved) {
        double low = 0;
        double high = waterLeakRate * (rainfallDuration + timeBetweenRainEndAndObservation) + waterMmLevelObserved;
        double smallestRainfall = binarySearch(waterMmLevelObserved, true, leakMmLocation, waterLeakRate,
                rainfallDuration, timeBetweenRainEndAndObservation, low, high);
        double largestRainfall = binarySearch(waterMmLevelObserved, false, leakMmLocation, waterLeakRate,
                rainfallDuration, timeBetweenRainEndAndObservation, low, high);
        return new Result(smallestRainfall, largestRainfall);
    }

    private static double binarySearch(double target, boolean isLowerBound, double leakMmLocation, double waterLeakRate,
                                       double rainfallDuration, double timeBetweenRainEndAndObservation,
                                       double low, double high) {
        double result = -1;

        while (low + EPSILON < high) {
            double middle = low + (high - low) / 2;
            double levelReached = mmLevelReachedWithRainfall(leakMmLocation, waterLeakRate, rainfallDuration,
                    timeBetweenRainEndAndObservation, middle);

            if (Math.abs(levelReached - target) < EPSILON) {
                result = middle;
                double candidate;

                if (isLowerBound) {
                    candidate = binarySearch(target, true, leakMmLocation, waterLeakRate,
                            rainfallDuration, timeBetweenRainEndAndObservation, low, middle);
                } else {
                    candidate = binarySearch(target, false, leakMmLocation, waterLeakRate,
                            rainfallDuration, timeBetweenRainEndAndObservation, middle, high);
                }
                if (candidate != -1) {
                    result = candidate;
                }
                break;
            } else if (levelReached < target) {
                low = middle;
            } else {
                high = middle;
            }
        }
        return result;
    }

    private static double mmLevelReachedWithRainfall(double leakMmLocation, double waterLeakRate, double rainfallDuration,
                                                     double timeBetweenRainEndAndObservation, double rainfallMm) {
        double mmPerHour = rainfallMm / rainfallDuration;
        double timeUntilReachingLeak = leakMmLocation / mmPerHour;
        if (timeUntilReachingLeak > rainfallDuration) {
            return rainfallMm;
        }

        double timeAfterReachingLeak = rainfallDuration - timeUntilReachingLeak;

        double mmUntilReachingLeak = timeUntilReachingLeak * mmPerHour;
        double mmAfterReachingLeak = timeAfterReachingLeak * (mmPerHour - waterLeakRate);

        double totalMmDuringRain = mmUntilReachingLeak + mmAfterReachingLeak;
        if (totalMmDuringRain <= leakMmLocation) {
            return totalMmDuringRain;
        }

        double mmLossAfterRainEnded = timeBetweenRainEndAndObservation * waterLeakRate;
        double maxPossibleMmLeakAfterRain = totalMmDuringRain - leakMmLocation;
        double mmLoss = Math.min(mmLossAfterRainEnded, maxPossibleMmLeakAfterRain);
        return totalMmDuringRain - mmLoss;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
