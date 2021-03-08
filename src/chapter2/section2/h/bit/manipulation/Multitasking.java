package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/03/21.
 */
public class Multitasking {

    private static class Task implements Comparable<Task> {
        int startTime;
        int endTime;
        int repeatTime;

        public Task(int startTime, int endTime, int repeatTime) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.repeatTime = repeatTime;
        }

        @Override
        public int compareTo(Task other) {
            return Integer.compare(startTime, other.startTime);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int oneTimeTasks = FastReader.nextInt();
        int repeatingTasks = FastReader.nextInt();

        while (oneTimeTasks != 0 || repeatingTasks != 0) {
            PriorityQueue<Task> priorityQueue = new PriorityQueue<>();

            for (int i = 0; i < oneTimeTasks; i++) {
                int startTime = FastReader.nextInt();
                int endTime = FastReader.nextInt();
                priorityQueue.offer(new Task(startTime, endTime, 0));
            }

            for (int i = 0; i < repeatingTasks; i++) {
                int startTime = FastReader.nextInt();
                int endTime = FastReader.nextInt();
                int repeatTime = FastReader.nextInt();
                priorityQueue.offer(new Task(startTime, endTime, repeatTime));
            }

            boolean doesConflict = checkConflict(priorityQueue);
            System.out.println(doesConflict ? "CONFLICT" : "NO CONFLICT");

            oneTimeTasks = FastReader.nextInt();
            repeatingTasks = FastReader.nextInt();
        }
    }

    private static boolean checkConflict(PriorityQueue<Task> priorityQueue) {
        int nextFreeTime = 0;

        while (!priorityQueue.isEmpty()) {
            Task nextTask = priorityQueue.poll();

            if (nextTask.startTime > 1000000) {
                break;
            }
            if (nextTask.startTime < nextFreeTime) {
                return true;
            }
            nextFreeTime = nextTask.endTime;

            if (nextTask.repeatTime > 0) {
                int repeatTime = nextTask.repeatTime;
                Task repeatTask = new Task(nextTask.startTime + repeatTime, nextTask.endTime + repeatTime,
                        repeatTime);
                priorityQueue.offer(repeatTask);
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
}
