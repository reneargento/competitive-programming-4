package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/07/22.
 */
public class AirConditionedMinions {

    private static class TemperaturePreference implements Comparable<TemperaturePreference> {
        int start;
        int end;

        public TemperaturePreference(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(TemperaturePreference other) {
            if (start != other.start) {
                return Integer.compare(start, other.start);
            }
            return Integer.compare(end, other.end);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        TemperaturePreference[] preferences = new TemperaturePreference[FastReader.nextInt()];
        for (int i = 0; i < preferences.length; i++) {
            preferences[i] = new TemperaturePreference(FastReader.nextInt(), FastReader.nextInt());
        }
        int minimumRooms = computeMinimumRooms(preferences);
        outputWriter.printLine(minimumRooms);
        outputWriter.flush();
    }

    private static int computeMinimumRooms(TemperaturePreference[] preferences) {
        Arrays.sort(preferences);
        int minimumRooms = 0;

        for (int i = 0; i < preferences.length; i++) {
            minimumRooms++;
            int end = preferences[i].end;

            for (int j = i + 1; j < preferences.length; j++) {
                if (preferences[j].start <= end) {
                    i++;
                    end = Math.min(end, preferences[j].end);
                } else {
                    break;
                }
            }
        }
        return minimumRooms;
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
