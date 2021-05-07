package chapter2.section3.a.priority.queue;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/05/21.
 */
public class ICanGuessTheDataStructure {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String commandsLine = FastReader.getLine();

        while (commandsLine != null) {
            int commands = Integer.parseInt(commandsLine);
            Queue<Integer> queue = new LinkedList<>();
            Deque<Integer> stack = new ArrayDeque<>();
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());

            boolean canBeQueue = true;
            boolean canBeStack = true;
            boolean canBePriorityQueue = true;
            boolean impossible = false;

            for (int i = 0; i < commands; i++) {
                int type = FastReader.nextInt();
                int value = FastReader.nextInt();

                if (impossible) {
                    continue;
                }

                if (type == 1) {
                    queue.offer(value);
                    stack.push(value);
                    priorityQueue.offer(value);
                } else {
                    if (queue.isEmpty()) {
                        impossible = true;
                        continue;
                    }

                    int queueValue = queue.poll();
                    canBeQueue = canBeDataStructure(value, queueValue, canBeQueue);
                    int stackValue = stack.pop();
                    canBeStack = canBeDataStructure(value, stackValue, canBeStack);
                    int priorityQueueValue = priorityQueue.poll();
                    canBePriorityQueue = canBeDataStructure(value, priorityQueueValue, canBePriorityQueue);
                }
            }

            if (impossible
                    || (!canBeQueue && !canBeStack && !canBePriorityQueue)) {
                outputWriter.printLine("impossible");
            } else if (canBeQueue && !canBeStack && !canBePriorityQueue) {
                outputWriter.printLine("queue");
            } else if (canBeStack && !canBeQueue && !canBePriorityQueue) {
                outputWriter.printLine("stack");
            } else if (canBePriorityQueue && !canBeQueue && !canBeStack) {
                outputWriter.printLine("priority queue");
            } else {
                outputWriter.printLine("not sure");
            }
            commandsLine = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean canBeDataStructure(int value, int structureValue, boolean canBeTheDataStructure) {
        if (!canBeTheDataStructure) {
            return false;
        }
        return value == structureValue;
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
