package chapter3.section4.a.classical;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/05/22.
 */
public class MinimalCoverage {

    private static class Segment implements Comparable<Segment> {
        int start;
        int end;

        public Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Segment other) {
            if (start != other.start) {
                return Integer.compare(start, other.start);
            }
            return Integer.compare(other.end, end);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            int maxCoordinate = FastReader.nextInt();
            List<Segment> segmentList = new ArrayList<>();

            int start = FastReader.nextInt();
            int end = FastReader.nextInt();
            while (start != 0 || end != 0) {
                if (end >= 0) {
                    segmentList.add(new Segment(start, end));
                }
                start = FastReader.nextInt();
                end = FastReader.nextInt();
            }

            Collections.sort(segmentList);
            List<Segment> segmentsToCover = filterSegments(segmentList, maxCoordinate);
            if (segmentsToCover == null) {
                outputWriter.printLine("0");
            } else {
                outputWriter.printLine(segmentsToCover.size());
                for (Segment segment : segmentsToCover) {
                    outputWriter.printLine(String.format("%d %d", segment.start, segment.end));
                }
            }
        }
        outputWriter.flush();
    }

    private static List<Segment> filterSegments(List<Segment> segmentList, int maxCoordinate) {
        if (segmentList.isEmpty()) {
            return null;
        }

        List<Segment> filteredSegments = new ArrayList<>();
        int high = 0;

        for (int i = 0; i < segmentList.size(); i++) {
            if (segmentList.get(i).start > high) {
                return null;
            }

            int nextSegment = i;
            int highestEnd = Integer.MIN_VALUE;

            for (int j = i; j < segmentList.size(); j++) {
                Segment segment = segmentList.get(j);
                if (segment.start <= high) {
                    if (segment.end > highestEnd) {
                        highestEnd = segment.end;
                        nextSegment = j;
                    }
                } else {
                    break;
                }
            }
            i = nextSegment;

            Segment segment = segmentList.get(i);
            filteredSegments.add(segment);

            high = Math.max(high, segment.end);

            if (high >= maxCoordinate) {
                break;
            }
        }

        if (high < maxCoordinate) {
            return null;
        }
        return filteredSegments;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
