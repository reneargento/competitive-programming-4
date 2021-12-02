package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/12/21.
 */
public class MeetingRoomArrangement {

    private static class Event implements Comparable<Event> {
        int startTime;
        int endTime;

        public Event(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public int compareTo(Event other) {
            if (startTime != other.startTime) {
                return Integer.compare(startTime, other.startTime);
            }
            return Integer.compare(endTime, other.endTime);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int days = FastReader.nextInt();

        for (int d = 0; d < days; d++) {
            List<Event> events = new ArrayList<>();

            int startTime = FastReader.nextInt();
            int endTime = FastReader.nextInt();

            while (startTime != 0 || endTime != 0) {
                events.add(new Event(startTime, endTime));
                startTime = FastReader.nextInt();
                endTime = FastReader.nextInt();
            }

            int maxEvents = computeMaxEvents(events);
            outputWriter.printLine(maxEvents);
        }
        outputWriter.flush();
    }

    private static int computeMaxEvents(List<Event> events) {
        Collections.sort(events);
        return computeMaxEvents(events, 0, 0);
    }

    private static int computeMaxEvents(List<Event> events, int mask, int index) {
        if (index == events.size()) {
            List<Event> selectedEvents = selectEvents(events, mask);
            return countEventsOnDay(selectedEvents);
        }

        int maskWithEventSelected = mask | (1 << index);
        int eventsCountWithEvent = computeMaxEvents(events, maskWithEventSelected, index + 1);
        int eventsCountWithoutEvent = computeMaxEvents(events, mask, index + 1);
        return Math.max(eventsCountWithEvent, eventsCountWithoutEvent);
    }

    private static List<Event> selectEvents(List<Event> events, int mask) {
        List<Event> selectedEvents = new ArrayList<>();

        for (int i = 0; i < events.size(); i++) {
            if ((mask & (1 << i)) > 0) {
                selectedEvents.add(events.get(i));
            }
        }
        return selectedEvents;
    }

    private static int countEventsOnDay(List<Event> events) {
        int nextAvailableHour = 0;

        for (Event event : events) {
            if (event.startTime < nextAvailableHour) {
                return 0;
            }
            nextAvailableHour = event.endTime;
        }
        return events.size();
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
