package chapter3.section4.a.classical;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/05/22.
 */
public class GasStations {

    private static class Interval implements Comparable<Interval> {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Interval other) {
            if (start != other.start) {
                return Integer.compare(start, other.start);
            }
            return Integer.compare(end, other.end);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int roadLength = FastReader.nextInt();
        int gasStations = FastReader.nextInt();

        while (roadLength != 0 || gasStations != 0) {
            Interval[] intervals = new Interval[gasStations];
            for (int i = 0; i < intervals.length; i++) {
                int location = FastReader.nextInt();
                int radius = FastReader.nextInt();
                intervals[i] = new Interval(location - radius, location + radius);
            }
            int eliminatedGasStations = computeEliminatedGasStations(roadLength, intervals);
            outputWriter.printLine(eliminatedGasStations);

            roadLength = FastReader.nextInt();
            gasStations = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeEliminatedGasStations(int roadLength, Interval[] intervals) {
        Arrays.sort(intervals);

        int eliminatedGasStations = intervals.length;
        int position = 0;
        int intervalIndex = 0;
        int highestEnd;

        while (position < roadLength) {
            highestEnd = position;

            while (intervalIndex < intervals.length && intervals[intervalIndex].start <= position) {
                highestEnd = Math.max(highestEnd, intervals[intervalIndex].end);
                intervalIndex++;
            }

            if (highestEnd == position) {
                break;
            }
            position = highestEnd;
            eliminatedGasStations--;
        }

        if (position < roadLength) {
            return -1;
        }
        return eliminatedGasStations;
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
