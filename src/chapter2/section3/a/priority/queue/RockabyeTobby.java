package chapter2.section3.a.priority.queue;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/05/21.
 */
public class RockabyeTobby {

    private static class Medication implements Comparable<Medication> {
        int priority;
        String name;
        int frequency;
        int nextTime;

        public Medication(int priority, String name, int frequency) {
            this.priority = priority;
            this.name = name;
            this.frequency = frequency;
            nextTime = frequency;
        }

        @Override
        public int compareTo(Medication other) {
            if (nextTime != other.nextTime) {
                return Integer.compare(nextTime, other.nextTime);
            }
            return Integer.compare(priority, other.priority);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int medicationsNumber = FastReader.nextInt();
            int minimumRequired = FastReader.nextInt();

            PriorityQueue<Medication> medications = new PriorityQueue<>();
            for (int i = 0; i < medicationsNumber; i++) {
                String name = FastReader.next();
                int frequency = FastReader.nextInt();
                Medication medication = new Medication(i, name, frequency);
                medications.offer(medication);
            }
            printMedications(medications, minimumRequired, outputWriter);
        }
        outputWriter.flush();
    }

    private static void printMedications(PriorityQueue<Medication> medications, int minimumRequired,
                                         OutputWriter outputWriter) {
        for (int i = 0; i < minimumRequired; i++) {
            Medication nextMedication = medications.poll();
            outputWriter.printLine(nextMedication.nextTime + " " + nextMedication.name);
            nextMedication.nextTime += nextMedication.frequency;
            medications.offer(nextMedication);
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
