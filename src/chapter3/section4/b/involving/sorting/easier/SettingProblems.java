package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/06/22.
 */
public class SettingProblems {

    private static class TaskTime implements Comparable<TaskTime> {
        int sultanTime;
        int golapibabaTime;

        public TaskTime(int sultanTime, int golapibabaTime) {
            this.sultanTime = sultanTime;
            this.golapibabaTime = golapibabaTime;
        }

        @Override
        public int compareTo(TaskTime other) {
            int time1 = sultanTime + Math.max(golapibabaTime, other.sultanTime) + other.golapibabaTime;
            int time2 = other.sultanTime + Math.max(other.golapibabaTime, sultanTime) + golapibabaTime;
            return Integer.compare(time1, time2);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int problems = Integer.parseInt(line);
            int[] sultanTimes = readTimes(problems);
            int[] golapibabaTimes = readTimes(problems);

            int minimumTime = computeMinimumTime(sultanTimes, golapibabaTimes);
            outputWriter.printLine(minimumTime);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[] readTimes(int length) throws IOException {
        int[] times = new int[length];

        for (int i = 0; i < times.length; i++) {
            times[i] = FastReader.nextInt();
        }
        return times;
    }

    private static int computeMinimumTime(int[] sultanTimes, int[] golapibabaTimes) {
        TaskTime[] taskTimes = new TaskTime[sultanTimes.length];
        for (int i = 0; i < taskTimes.length; i++) {
            taskTimes[i] = new TaskTime(sultanTimes[i], golapibabaTimes[i]);
        }
        Arrays.sort(taskTimes);

        int minimumTime = taskTimes[0].sultanTime;

        for (int i = 1; i < taskTimes.length; i++) {
            if (taskTimes[i - 1].golapibabaTime > taskTimes[i].sultanTime) {
                int difference = taskTimes[i - 1].golapibabaTime - taskTimes[i].sultanTime;

                for (int j = i + 1; j < taskTimes.length; j++) {
                    if (difference <= taskTimes[j].sultanTime) {
                        taskTimes[j].sultanTime -= difference;
                        break;
                    } else {
                        difference -= taskTimes[j].sultanTime;
                        taskTimes[j].sultanTime = 0;
                    }
                }
            }

            int maxCurrentTime = Math.max(taskTimes[i - 1].golapibabaTime, taskTimes[i].sultanTime);
            minimumTime += maxCurrentTime;
        }
        minimumTime += taskTimes[taskTimes.length - 1].golapibabaTime;
        return minimumTime;
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
