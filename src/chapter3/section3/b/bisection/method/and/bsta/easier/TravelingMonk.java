package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/03/22.
 */
public class TravelingMonk {

    private static final double EPSILON = .0000001;

    private static class Segment {
        int elevationChange;
        int time;

        public Segment(int elevationChange, int time) {
            this.elevationChange = elevationChange;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Segment[] ascentSegments = new Segment[FastReader.nextInt()];
        Segment[] descentSegments = new Segment[FastReader.nextInt()];
        readSegments(ascentSegments);
        readSegments(descentSegments);

        double timeOnSameSpot = findTimeOnSameSpot(ascentSegments, descentSegments);
        outputWriter.printLine(timeOnSameSpot);
        outputWriter.flush();
    }

    private static void readSegments(Segment[] segments) throws IOException {
        for (int i = 0; i < segments.length; i++) {
            segments[i] = new Segment(FastReader.nextInt(), FastReader.nextInt());
        }
    }

    private static double findTimeOnSameSpot(Segment[] ascentSegments, Segment[] descentSegments) {
        int totalHeight = getTotalHeight(ascentSegments);
        double lowTime = 1;
        double highTime = 500000;
        double increment = 0.00000001;
        double earliestTimeOnSameSpot = Double.POSITIVE_INFINITY;

        while (lowTime <= highTime) {
            double time = lowTime + (highTime - lowTime) / 2;
            double heightOnAscent = getHeightOnTime(ascentSegments, time, 0, true);
            double heightOnDescent = getHeightOnTime(descentSegments, time, totalHeight, false);

            boolean isInTheSameSpot = Math.abs(heightOnAscent - heightOnDescent) < EPSILON;
            boolean crossed = heightOnAscent > heightOnDescent;

            if (isInTheSameSpot) {
                earliestTimeOnSameSpot = time;
                highTime = time - increment;
            } else if (crossed) {
                highTime = time - increment;
            } else {
                lowTime = time + increment;
            }
        }
        return earliestTimeOnSameSpot;
    }

    private static int getTotalHeight(Segment[] segments) {
        int height = 0;
        for (Segment segment : segments) {
            height += segment.elevationChange;
        }
        return height;
    }

    private static double getHeightOnTime(Segment[] segments, double time, int startHeight, boolean isAscent) {
        double currentTime = 0;
        double height = startHeight;

        for (Segment segment : segments) {
            double deltaElevation;

            if (currentTime + segment.time <= time) {
                deltaElevation = segment.elevationChange;
            } else {
                double deltaTime = time - currentTime;
                deltaElevation = (segment.elevationChange * deltaTime) / segment.time;
            }

            if (isAscent) {
                height += deltaElevation;
            } else {
                height -= deltaElevation;
            }
            currentTime += segment.time;
            if (currentTime >= time) {
                break;
            }
        }
        return height;
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
