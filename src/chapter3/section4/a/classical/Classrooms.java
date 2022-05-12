package chapter3.section4.a.classical;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/05/22.
 */
// Based on https://github.com/mpfeifer1/Kattis/blob/master/classrooms.cpp
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

    private static class Time implements Comparable<Time> {
        int value;
        int index;

        public Time(int value, int index) {
            this.value = value;
            this.index = index; // Needed to have more than one node with the same time in the tree
        }

        @Override
        public int compareTo(Time other) {
            if (value != other.value) {
                return Integer.compare(value, other.value);
            }
            return Integer.compare(index, other.index);
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
        TreeSet<Time> scheduledActivities = new TreeSet<>();

        int maximumActivitiesScheduled = 0;
        for (int i = 0; i < activities.length; i++) {
            Time startTime = new Time(-activities[i].start, 0);
            Time earliestEndTime = scheduledActivities.ceiling(startTime);

            // If it starts before all end times
            if (earliestEndTime == null) {
                if (scheduledActivities.size() < classrooms) {
                    scheduledActivities.add(new Time(-activities[i].end - 1, i));
                    maximumActivitiesScheduled++;
                }
                continue;
            }

            // Remove the activity that ends before the current activity and add it
            scheduledActivities.remove(earliestEndTime);
            scheduledActivities.add(new Time(-activities[i].end - 1, i));
            maximumActivitiesScheduled++;
        }
        return maximumActivitiesScheduled;
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
