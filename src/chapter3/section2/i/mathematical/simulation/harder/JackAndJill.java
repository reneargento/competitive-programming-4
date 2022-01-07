package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/01/22.
 */
public class JackAndJill {

    private static final double EPSILON = .000000001;
    private static final double BUCKET_ACCELERATION_INCHES_PER_SECOND = 32.2 * 12;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        double upSeconds = FastReader.nextDouble();
        int scenarioId = 1;

        while (upSeconds > 0) {
            if (scenarioId > 1) {
                outputWriter.printLine();
            }

            double wellDiameter = FastReader.nextDouble();
            double waterLevelBelowTop = FastReader.nextDouble();
            double bucketVolume = FastReader.nextDouble();
            double bucketPullRate = FastReader.nextDouble();
            double downSeconds = FastReader.nextDouble();
            double requiredVolume = FastReader.nextDouble();

            double requiredTime = computeRequiredTime(upSeconds, wellDiameter, waterLevelBelowTop, bucketVolume, bucketPullRate,
                    downSeconds, requiredVolume);
            outputWriter.printLine(String.format("Scenario %d:", scenarioId));
            outputWriter.printLine(String.format("     up hill           %8.2f sec", upSeconds));
            outputWriter.printLine(String.format("     well diameter     %8.2f in", wellDiameter));
            outputWriter.printLine(String.format("     water level       %8.2f in", waterLevelBelowTop));
            outputWriter.printLine(String.format("     bucket volume     %8.2f cu ft", bucketVolume));
            outputWriter.printLine(String.format("     bucket ascent rate%8.2f in/sec", bucketPullRate));
            outputWriter.printLine(String.format("     down hill         %8.2f sec", downSeconds));
            outputWriter.printLine(String.format("     required volume   %8.2f cu ft", requiredVolume));
            outputWriter.printLine(String.format("     TIME REQUIRED     %8.2f sec", requiredTime));

            scenarioId++;
            upSeconds = FastReader.nextDouble();
        }
        outputWriter.flush();
    }

    private static double computeRequiredTime(double upSeconds, double wellDiameter, double waterLevelBelowTop,
                                              double bucketVolume, double bucketPullRate, double downSeconds,
                                              double requiredVolume) {
        double requiredTime = 0;
        double volumeCarried = 0;
        double wellRadius = wellDiameter / 2;
        double bucketVolumeInCubicInches = bucketVolume * 12 * 12 * 12;

        double wellArea = Math.PI * wellRadius * wellRadius;
        // Update well volume
        // v = volume, a = area, h = height
        // v = a * h
        // h = v / a
        double heightIncreasedPerTurn = bucketVolumeInCubicInches / wellArea;

        while (volumeCarried + EPSILON < requiredVolume) {
            // s = distance, u = initial velocity, t = time, a = acceleration
            // s = u*t + 1/2 * a*t^2 // Initial velocity is 0, so u = 0
            // 1/2 * a*t^2 = s
            // a*t^2 = 2 * s
            // t = sqrt((2 * s) / a))
            double secondsToDropBucket = Math.sqrt(2 * waterLevelBelowTop / BUCKET_ACCELERATION_INCHES_PER_SECOND);
            double secondsToLiftTheBucket = waterLevelBelowTop / bucketPullRate;

            waterLevelBelowTop += heightIncreasedPerTurn;

            requiredTime += upSeconds + secondsToDropBucket + secondsToLiftTheBucket + downSeconds;
            volumeCarried += bucketVolume;
        }
        return requiredTime;
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
