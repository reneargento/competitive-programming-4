package chapter2.section3.a.priority.queue;

import java.io.*;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/05/21.
 */
public class Clinic {

    private static class Patient implements Comparable<Patient> {
        String name;
        int time;
        long priority;

        public Patient(int time, String name, long severity, long clinicConstant) {
            this.name = name;
            this.time = time;
            priority = severity + clinicConstant * (MAX_WAIT - time);
        }

        @Override
        public int compareTo(Patient other) {
            if (priority != other.priority) {
                return Long.compare(other.priority, priority);
            }
            return name.compareTo(other.name);
        }
    }

    private static final int MAX_WAIT = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int queries = FastReader.nextInt();
        int clinicConstant = FastReader.nextInt();

        PriorityQueue<Patient> patients = new PriorityQueue<>();
        Set<String> gonePatients = new HashSet<>();

        for (int q = 0; q < queries; q++) {
            int type = FastReader.nextInt();
            int time = FastReader.nextInt();

            if (type == 2) {
                while (!patients.isEmpty() && gonePatients.contains(patients.peek().name)) {
                    gonePatients.remove(patients.peek().name);
                    patients.poll();
                }
                if (patients.isEmpty() || patients.peek().time > time) {
                    outputWriter.printLine("doctor takes a break");
                } else {
                    outputWriter.printLine(patients.peek().name);
                    patients.poll();
                }
            } else {
                String name = FastReader.next();
                if (type == 1) {
                    int severity = FastReader.nextInt();
                    Patient patient = new Patient(time, name, severity, clinicConstant);
                    patients.offer(patient);
                } else {
                    gonePatients.add(name);
                }
            }
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
