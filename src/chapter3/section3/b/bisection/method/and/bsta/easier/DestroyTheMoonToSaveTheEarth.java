package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/03/22.
 */
public class DestroyTheMoonToSaveTheEarth {

    private static final double MILLIMETERS_TO_KILOMETER = 1000000.0;
    private static final long SECONDS_TO_DAY = 60 * 60 * 24;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int days = FastReader.nextInt();
            int mmPerSecond = FastReader.nextInt();
            int distance = FastReader.nextInt();

            int tonsOfMass = computeTonsOfMass(days, mmPerSecond, distance);
            if (tonsOfMass >= 0) {
                outputWriter.printLine(String.format("Add %d tons", tonsOfMass));
            } else {
                outputWriter.printLine(String.format("Remove %d tons", -tonsOfMass));
            }
        }
        outputWriter.flush();
    }

    private static int computeTonsOfMass(int days, int mmPerSecond, int distance) {
        double kmPerSecond = mmPerSecond / MILLIMETERS_TO_KILOMETER;
        double originalDistance = kmPerSecond * SECONDS_TO_DAY * days;
        double targetDistance = originalDistance + distance;

        double extraMassLow = -100000;
        double extraMassHigh = 100000;

        while (extraMassLow <= extraMassHigh) {
            double extraMass = extraMassLow + (extraMassHigh - extraMassLow) / 2;
            double distanceTravelled = computeDistanceTravelled(days, kmPerSecond, extraMass);

            if (distanceTravelled == targetDistance) {
                return (int) extraMass;
            } else if (distanceTravelled < targetDistance) {
                extraMassHigh = extraMass - 0.0001;
            } else {
                extraMassLow = extraMass + 0.0001;
            }
        }
        return (int) extraMassHigh;
    }

    private static double computeDistanceTravelled(int days, double kmPerSecond, double extraMass) {
        double massImpact = extraMass / MILLIMETERS_TO_KILOMETER;
        double distanceInADay = (kmPerSecond - massImpact) * SECONDS_TO_DAY;
        return distanceInADay * days;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
