package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/07/22.
 */
public class ShoemakersProblem {

    private static class Job implements Comparable<Job> {
        int id;
        int time;
        double fine;

        public Job(int id, int time, double fine) {
            this.id = id;
            this.time = time;
            this.fine = fine;
        }

        @Override
        public int compareTo(Job other) {
            double ratio1 = fine / time;
            double ratio2 = other.fine / other.time;

            if (ratio1 != ratio2) {
                return Double.compare(ratio2, ratio1);
            }
            return Integer.compare(id, other.id);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            Job[] jobs = new Job[FastReader.nextInt()];
            for (int i = 0; i < jobs.length; i++) {
                jobs[i] = new Job(i + 1, FastReader.nextInt(), FastReader.nextInt());
            }
            Arrays.sort(jobs);

            outputWriter.print(jobs[0].id);
            for (int i = 1; i < jobs.length; i++) {
                outputWriter.print(" " + jobs[i].id);
            }
            outputWriter.printLine();
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
