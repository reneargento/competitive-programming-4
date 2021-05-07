package chapter2.section3.a.priority.queue;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/04/21.
 */
public class Argus {

    private static class Instruction implements Comparable<Instruction> {
        int id;
        int frequency;
        int timeToExecute;

        public Instruction(int id, int frequency, int timeToExecute) {
            this.id = id;
            this.frequency = frequency;
            this.timeToExecute = timeToExecute;
        }

        @Override
        public int compareTo(Instruction other) {
            if (timeToExecute != other.timeToExecute) {
                return Integer.compare(timeToExecute, other.timeToExecute);
            }
            return Integer.compare(id, other.id);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String line = FastReader.getLine();
        PriorityQueue<Instruction> priorityQueue = new PriorityQueue<>();

        while (line.charAt(0) != '#') {
            String[] values = line.split(" ");
            int id = Integer.parseInt(values[1]);
            int frequency = Integer.parseInt(values[2]);
            priorityQueue.offer(new Instruction(id, frequency, frequency));

            line = FastReader.getLine();
        }

        int queries = FastReader.nextInt();
        computeQueries(priorityQueue, queries);
    }

    private static void computeQueries(PriorityQueue<Instruction> priorityQueue, int queries) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        for (int i = 0; i < queries; i++) {
            Instruction instruction = priorityQueue.poll();
            outputWriter.printLine(instruction.id);
            int frequency = instruction.frequency;

            priorityQueue.offer(new Instruction(instruction.id, frequency,
                    instruction.timeToExecute + frequency));
        }
        outputWriter.flush();
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
