package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/03/22.
 */
public class NeedForSpeedUVa {

    private static final double EPSILON = 0.00000000001;

    private static class Segment {
        int distance;
        int speedometerReading;

        public Segment(int distance, int speedometerReading) {
            this.distance = distance;
            this.speedometerReading = speedometerReading;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int segmentsNumber = Integer.parseInt(data[0]);
            Segment[] segments = new Segment[segmentsNumber];
            int totalTime = Integer.parseInt(data[1]);

            for (int i = 0; i < segments.length; i++) {
                segments[i] = new Segment(FastReader.nextInt(), FastReader.nextInt());
            }
            double constant = computeConstant(segments, totalTime);
            outputWriter.printLine(constant);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double computeConstant(Segment[] segments, int totalTime) {
        double low = -1000;
        double high = 10000000;

        while (high - low > EPSILON) {
            double middle = low + (high - low) / 2;
            double timeWithConstant = computeTimeWithConstant(segments, middle);
            double timeDifference = totalTime - timeWithConstant;

            if (Math.abs(timeDifference) <= EPSILON) {
                return middle;
            } else if (timeWithConstant > totalTime) {
                low = middle;
            } else {
                high = middle;
            }
        }
        return low;
    }

    private static double computeTimeWithConstant(Segment[] segments, double constant) {
        double time = 0;
        for (Segment segment : segments) {
            double speed = (segment.speedometerReading + constant);
            if (speed < EPSILON) {
                return Double.POSITIVE_INFINITY;
            }
            time += segment.distance / speed;
        }
        return time;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
