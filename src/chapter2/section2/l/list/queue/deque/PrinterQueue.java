package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/04/21.
 */
public class PrinterQueue {

    private static class Job {
        int index;
        int priority;

        public Job(int index, int priority) {
            this.index = index;
            this.priority = priority;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int jobs = FastReader.nextInt();
            int jobIndex = FastReader.nextInt();

            int[] priorityFrequency = new int[10];
            Queue<Job> printQueue = new LinkedList<>();

            for (int i = 0; i < jobs; i++) {
                int priority = FastReader.nextInt();
                priorityFrequency[priority]++;
                printQueue.offer(new Job(i, priority));
            }

            int minutes = countMinutesUntilPrinting(printQueue, priorityFrequency, jobIndex);
            outputWriter.printLine(minutes);
        }
        outputWriter.flush();
    }

    private static int countMinutesUntilPrinting(Queue<Job> printQueue, int[] priorityFrequency, int jobIndex) {
        int minutes = 0;

        while (true) {
            Job job = printQueue.poll();
            if (jobWithHigherPriorityExists(job.priority, priorityFrequency)) {
                printQueue.offer(job);
            } else {
                priorityFrequency[job.priority]--;
                minutes++;

                if (job.index == jobIndex) {
                    return minutes;
                }
            }
        }
    }

    private static boolean jobWithHigherPriorityExists(int priority, int[] priorityFrequency) {
        for (int p = priority + 1; p < priorityFrequency.length; p++) {
            if (priorityFrequency[p] > 0) {
                return true;
            }
        }
        return false;
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
