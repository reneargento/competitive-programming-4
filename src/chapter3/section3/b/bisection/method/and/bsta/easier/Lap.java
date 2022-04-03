package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;

/**
 * Created by Rene Argento on 01/04/22.
 */
public class Lap {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int timeFast = Integer.parseInt(data[0]);
            int timeSlow = Integer.parseInt(data[1]);

            int stragglerLap = computeStragglerLap(timeFast, timeSlow);
            outputWriter.printLine(stragglerLap);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeStragglerLap(int timeFast, int timeSlow) {
        int stragglerLap = Integer.MAX_VALUE;
        int low = 0;
        int high = 10000;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (overtakes(timeFast, timeSlow, middle)) {
                stragglerLap = middle;
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return stragglerLap;
    }

    private static boolean overtakes(int timeFast, int timeSlow, int laps) {
        double delta = timeSlow - timeFast;
        double lapsToOvertake = timeSlow / delta;
        return lapsToOvertake <= laps;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
