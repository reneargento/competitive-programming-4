package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/03/22.
 */
// Based on https://nordic.icpc.io/ncpc2009/ncpc2009slides.pdf
public class RainFallKattis {

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

        double leakMmLocation = FastReader.nextDouble();
        double waterLeakRate = FastReader.nextDouble();
        double rainfallDuration = FastReader.nextDouble();
        double timeBetweenRainEndAndObservation = FastReader.nextDouble();
        double waterMmLevelObserved = FastReader.nextDouble();

        Result rainfall = computeRainfall(leakMmLocation, waterLeakRate, rainfallDuration,
                timeBetweenRainEndAndObservation, waterMmLevelObserved);
        outputWriter.printLine(String.format("%.9f %.9f", rainfall.smallestRainfall, rainfall.largestRainfall));
        outputWriter.flush();
    }

    private static Result computeRainfall(double leakMmLocation, double waterLeakRate, double rainfallDuration,
                                          double timeBetweenRainEndAndObservation, double waterMmLevelObserved) {
        double smallestRainfall;
        double largestRainfall;

        if (waterLeakRate == 0) {
            smallestRainfall = waterMmLevelObserved;
            largestRainfall = waterMmLevelObserved;
        } else {
            double a = waterLeakRate;
            double b = -waterLeakRate * (rainfallDuration + timeBetweenRainEndAndObservation) - waterMmLevelObserved;
            double c = leakMmLocation * rainfallDuration;
            double timeUntilReachingLeak = (-b - Math.sqrt(b * b - 4 * a * c)) / (2 * a);

            double waterMmLeaked = waterLeakRate * (rainfallDuration + timeBetweenRainEndAndObservation - timeUntilReachingLeak);
            smallestRainfall = waterMmLevelObserved + (waterMmLevelObserved <= leakMmLocation ? 0 : waterMmLeaked);
            largestRainfall = waterMmLevelObserved + (waterMmLevelObserved < leakMmLocation ? 0 : waterMmLeaked);
        }
        return new Result(smallestRainfall, largestRainfall);
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
