package chapter3.section4.d.involving.priority.queue;

import java.io.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/07/22.
 */
public class AssigningWorkstations {

    private static class Event implements Comparable<Event> {
        int time;
        boolean isArrival;

        public Event(int time, boolean isArrival) {
            this.time = time;
            this.isArrival = isArrival;
        }

        @Override
        public int compareTo(Event other) {
            if (time != other.time) {
                return Integer.compare(time, other.time);
            }
            if (other.isArrival && !isArrival) {
                return -1;
            } else if (isArrival && !other.isArrival) {
                return 1;
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int researchers = FastReader.nextInt();
        int inactivityTime = FastReader.nextInt();
        PriorityQueue<Event> events = new PriorityQueue<>();

        for (int i = 0; i < researchers; i++) {
            int arrivalTime = FastReader.nextInt();
            int duration = FastReader.nextInt();
            events.offer(new Event(arrivalTime, true));
            events.offer(new Event(arrivalTime + duration, false));
        }
        int unlocksSaved = computeUnlocksSaved(events, inactivityTime);
        outputWriter.printLine(unlocksSaved);
        outputWriter.flush();
    }

    private static int computeUnlocksSaved(PriorityQueue<Event> events, int inactivityTime) {
        int unlocksSaved = 0;
        Queue<Integer> leaveTimes = new LinkedList<>();

        while (!events.isEmpty()) {
            Event event = events.poll();

            if (event.isArrival) {
                while (!leaveTimes.isEmpty()) {
                    int lockTime = leaveTimes.poll() + inactivityTime;
                    if (lockTime >= event.time) {
                        unlocksSaved++;
                        break;
                    }
                }
            } else {
                leaveTimes.offer(event.time);
            }
        }
        return unlocksSaved;
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
