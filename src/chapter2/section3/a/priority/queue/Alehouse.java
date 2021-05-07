package chapter2.section3.a.priority.queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/05/21.
 */
public class Alehouse {

    private static class Event implements Comparable<Event> {
        int time;
        boolean isEntrance;

        public Event(int time, boolean isEntrance) {
            this.time = time;
            this.isEntrance = isEntrance;
        }

        @Override
        public int compareTo(Event other) {
            if (time != other.time) {
                return Integer.compare(time, other.time);
            }
            if (isEntrance && !other.isEntrance) {
                return -1;
            } else if (!isEntrance && other.isEntrance) {
                return 1;
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int residents = FastReader.nextInt();
        int milliseconds = FastReader.nextInt();
        PriorityQueue<Event> events = new PriorityQueue<>();

        for (int i = 0; i < residents; i++) {
            int entrance = FastReader.nextInt();
            int exit = FastReader.nextInt() + milliseconds;
            events.offer(new Event(entrance, true));
            events.offer(new Event(exit, false));
        }

        int maxFriends = computeMaxFriends(events);
        System.out.println(maxFriends);
    }

    private static int computeMaxFriends(PriorityQueue<Event> events) {
        int peopleInAlehouse = 0;
        int maxFriends = 0;

        while (!events.isEmpty()) {
            Event event = events.poll();
            if (event.isEntrance) {
                peopleInAlehouse++;
            } else {
                peopleInAlehouse--;
            }
            maxFriends = Math.max(maxFriends, peopleInAlehouse);
        }
        return maxFriends;
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
}
