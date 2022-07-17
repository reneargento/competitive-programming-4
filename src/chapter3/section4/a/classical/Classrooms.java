package chapter3.section4.a.classical;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/05/22.
 */
public class Classrooms {

    private static class Activity implements Comparable<Activity> {
        int start;
        int end;

        public Activity(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Activity other) {
            if (end != other.end) {
                return Integer.compare(end, other.end);
            }
            return Integer.compare(start, other.start);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Activity[] activities = new Activity[FastReader.nextInt()];
        int classrooms = FastReader.nextInt();

        for (int i = 0; i < activities.length; i++) {
            activities[i] = new Activity(FastReader.nextInt(), FastReader.nextInt());
        }

        int maximumActivitiesScheduled = computeMaximumActivitiesScheduled(activities, classrooms);
        outputWriter.printLine(maximumActivitiesScheduled);
        outputWriter.flush();
    }

    private static int computeMaximumActivitiesScheduled(Activity[] activities, int classrooms) {
        Arrays.sort(activities);
        Multiset<Integer> endingTimes = new Multiset<>();

        for (int i = 0; i < classrooms; i++) {
            endingTimes.add(0);
        }

        int maximumActivitiesScheduled = 0;
        for (Activity activity : activities) {
            Integer closestFinishTime = endingTimes.floorKey(activity.start);
            if (closestFinishTime == null) {
                continue;
            }

            // Add 1 so that floorKey() will not get intersecting activities
            endingTimes.add(activity.end + 1);
            endingTimes.delete(closestFinishTime);
            maximumActivitiesScheduled++;
        }
        return maximumActivitiesScheduled;
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
