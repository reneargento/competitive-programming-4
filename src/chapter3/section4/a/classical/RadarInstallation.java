package chapter3.section4.a.classical;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/05/22.
 */
public class RadarInstallation {

    private static class Interval implements Comparable<Interval> {
        double start;
        double end;

        public Interval(double start, double end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Interval other) {
            if (end != other.end) {
                return Double.compare(end, other.end);
            }
            return Double.compare(start, other.start);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int testCase = 1;

        while (!line.equals("0 0")) {
            String[] data = line.split(" ");
            int islands = Integer.parseInt(data[0]);
            int distance = Integer.parseInt(data[1]);
            Interval[] intervals = new Interval[islands];
            boolean possible = true;

            for (int i = 0; i < islands; i++) {
                int xCoordinate = FastReader.nextInt();
                int yCoordinate = FastReader.nextInt();
                double xDistanceFromCenter = Math.sqrt(Math.pow(distance, 2) - Math.pow(yCoordinate, 2));
                intervals[i] = new Interval(xCoordinate - xDistanceFromCenter, xCoordinate + xDistanceFromCenter);

                if (yCoordinate > distance) {
                    possible = false;
                }
            }

            int minimalRadarsNeeded;
            if (possible) {
                minimalRadarsNeeded = computeMinimalRadarsNeeded(intervals);
            } else {
                minimalRadarsNeeded = -1;
            }
            outputWriter.printLine(String.format("Case %d: %d", testCase, minimalRadarsNeeded));
            FastReader.getLine();
            line = FastReader.getLine();
            testCase++;
        }
        outputWriter.flush();
    }

    private static int computeMinimalRadarsNeeded(Interval[] intervals) {
        Arrays.sort(intervals);
        int minimalRadarsNeeded = 0;

        for (int i = 0; i < intervals.length;) {
            int j;
            for (j = 0; j < intervals.length; j++) {
                if (intervals[j].start > intervals[i].end) {
                    break;
                }
            }
            i = j;
            minimalRadarsNeeded++;
        }
        return minimalRadarsNeeded;
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
