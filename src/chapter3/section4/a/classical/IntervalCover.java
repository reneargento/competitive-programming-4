package chapter3.section4.a.classical;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/05/22.
 */
public class IntervalCover {

    private static class Interval implements Comparable<Interval> {
        int index;
        double start;
        double end;

        public Interval(int index, double start, double end) {
            this.index = index;
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Interval other) {
            if (start != other.start) {
                return Double.compare(start, other.start);
            }
            return Double.compare(other.end, end);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            double start = Double.parseDouble(data[0]);
            double end = Double.parseDouble(data[1]);
            int intervalsNumber = FastReader.nextInt();
            List<Interval> intervals = new ArrayList<>();
            for (int i = 0; i < intervalsNumber; i++) {
                Interval interval = new Interval(i, FastReader.nextDouble(), FastReader.nextDouble());
                if (interval.end >= start) {
                    intervals.add(interval);
                }
            }

            List<Integer> coverIntervals = getCoverIntervals(start, end, intervals);
            if (coverIntervals != null) {
                outputWriter.printLine(coverIntervals.size());
                outputWriter.print(coverIntervals.get(0));
                for (int i = 1; i < coverIntervals.size(); i++) {
                    outputWriter.print(" " + coverIntervals.get(i));
                }
                outputWriter.printLine();
            } else {
                outputWriter.printLine("impossible");
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> getCoverIntervals(double start, double end, List<Interval> intervals) {
        List<Integer> coverIntervals = new ArrayList<>();
        Collections.sort(intervals);
        double currentEnd = start;

        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(i).start > currentEnd) {
                return null;
            }

            double nextEnd = intervals.get(i).end;
            int nextIndex = i;
            for (int j = i + 1; j < intervals.size(); j++) {
                if (intervals.get(j).start > currentEnd) {
                    break;
                }
                if (intervals.get(j).end > nextEnd) {
                    nextIndex = j;
                    nextEnd = intervals.get(j).end;
                }
            }

            i = nextIndex;
            currentEnd = nextEnd;
            coverIntervals.add(intervals.get(nextIndex).index);
            if (intervals.get(i).end >= end) {
                break;
            }
        }

        if (currentEnd < end) {
            return null;
        }
        return coverIntervals;
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
