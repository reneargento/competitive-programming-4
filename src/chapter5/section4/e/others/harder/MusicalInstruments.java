package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/01/26.
 */
public class MusicalInstruments {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] highestPriorityFrequency = new int[FastReader.nextInt()];
            int students = FastReader.nextInt();

            for (int s = 0; s < students; s++) {
                for (int i = 0; i < highestPriorityFrequency.length; i++) {
                    int priority = FastReader.nextInt();
                    if (priority == 1) {
                        highestPriorityFrequency[i]++;
                    }
                }
            }

            long possibleDistributions = computePossibleDistributions(highestPriorityFrequency);
            outputWriter.printLine(possibleDistributions);
        }
        outputWriter.flush();
    }

    private static long computePossibleDistributions(int[] highestPriorityFrequency) {
        long possibleDistributions = 1;
        for (int frequency : highestPriorityFrequency) {
            if (frequency != 0) {
                possibleDistributions *= frequency;
            }
        }
        return possibleDistributions;
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

        public void flush() {
            writer.flush();
        }
    }
}
