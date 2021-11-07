package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 06/11/21.
 */
public class SummerTrip {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String events = FastReader.getLine();
        long goodItineraries = 0;

        for (int i = 0; i < events.length(); i++) {
            char startEvent = events.charAt(i);

            if (i < events.length() - 1
                    && startEvent == events.charAt(i + 1)) {
                continue;
            }
            Set<Character> computedEvents = new HashSet<>();

            for (int j = i + 1; j < events.length(); j++) {
                char currentEvent = events.charAt(j);
                if (currentEvent == startEvent) {
                    break;
                }

                if (computedEvents.contains(currentEvent)) {
                    continue;
                }

                computedEvents.add(currentEvent);
                goodItineraries++;
            }
        }

        outputWriter.printLine(goodItineraries);
        outputWriter.flush();
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
