package chapter3.section4.d.involving.priority.queue;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 17/07/22.
 */
public class EntertainmentBox {

    private static class Show implements Comparable<Show> {
        int start;
        int end;

        public Show(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Show other) {
            if (end != other.end) {
                return Integer.compare(end, other.end);
            }
            return Integer.compare(start, other.start);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Show[] shows = new Show[FastReader.nextInt()];
        int slots = FastReader.nextInt();

        for (int i = 0; i < shows.length; i++) {
            shows[i] = new Show(FastReader.nextInt(), FastReader.nextInt());
        }
        int maxShowsRecorded = computeMaxShowsRecorded(shows, slots);
        outputWriter.printLine(maxShowsRecorded);
        outputWriter.flush();
    }

    private static int computeMaxShowsRecorded(Show[] shows, int slots) {
        Arrays.sort(shows);
        Multiset<Integer> endingTimes = new Multiset<>();

        for (int i = 0; i < slots; i++) {
            endingTimes.add(0);
        }

        int maxShowsRecorded = 0;
        for (Show show : shows) {
            Integer closestFinishTime = endingTimes.floorKey(show.start);
            if (closestFinishTime == null) {
                continue;
            }

            endingTimes.add(show.end);
            endingTimes.delete(closestFinishTime);
            maxShowsRecorded++;
        }
        return maxShowsRecorded;
    }

    private static class Multiset<Key extends Comparable<Key>> {
        private final TreeMap<Key, Integer> multiset;

        Multiset() {
            multiset = new TreeMap<>();
        }

        public void add(Key key) {
            int keyFrequency = multiset.getOrDefault(key, 0);
            multiset.put(key, keyFrequency + 1);
        }

        public void delete(Key key) {
            int keyFrequency = multiset.get(key);
            if (keyFrequency == 1) {
                multiset.remove(key);
            } else {
                multiset.put(key, keyFrequency - 1);
            }
        }

        public int size() {
            return multiset.size();
        }

        public Key floorKey(Key key) {
            return multiset.floorKey(key);
        }
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
