package chapter3.section4.a.classical;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/05/22.
 */
public class FroshWeek {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Integer[] tasks = new Integer[FastReader.nextInt()];
        Integer[] timeIntervals = new Integer[FastReader.nextInt()];

        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = FastReader.nextInt();
        }
        for (int i = 0; i < timeIntervals.length; i++) {
            timeIntervals[i] = FastReader.nextInt();
        }
        int maximumNumberOfTasksCompleted = computeMaximumNumberOfTasksCompleted(tasks, timeIntervals);
        outputWriter.printLine(maximumNumberOfTasksCompleted);
        outputWriter.flush();
    }

    private static int computeMaximumNumberOfTasksCompleted(Integer[] tasks, Integer[] timeIntervals) {
        Arrays.sort(tasks, Collections.reverseOrder());
        Arrays.sort(timeIntervals, Collections.reverseOrder());

        int maximumNumberOfTasksCompleted = 0;
        int tasksIndex = 0;
        for (int i = 0; i < timeIntervals.length && tasksIndex < tasks.length; i++) {
            while (tasksIndex < tasks.length && tasks[tasksIndex] > timeIntervals[i]) {
                tasksIndex++;
            }

            if (tasksIndex < tasks.length && tasks[tasksIndex] <= timeIntervals[i]) {
                maximumNumberOfTasksCompleted++;
                tasksIndex++;
            }
        }
        return maximumNumberOfTasksCompleted;
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
