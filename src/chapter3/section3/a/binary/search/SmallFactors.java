package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/03/22.
 */
public class SmallFactors {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();
        List<Long> allSamples = computeAllSamples();

        while (number != 0) {
            long samples = computeSamples(allSamples, number);
            outputWriter.printLine(samples);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Long> computeAllSamples() {
        List<Long> samples = new ArrayList<>();
        long power2 = 1;

        for (int i = 0; i <= 31; i++) {
            long power3 = 1;

            for (int j = 0; j <= 20; j++) {
                long sample = power2 * power3;
                samples.add(sample);
                power3 *= 3;
            }
            power2 *= 2;
        }
        Collections.sort(samples);
        return samples;
    }

    private static long computeSamples(List<Long> samples, int number) {
        int low = 0;
        int high = samples.size() - 1;

        while (low < high) {
            int middle = low + (high - low) / 2;
            if (samples.get(middle) < number) {
                low = middle + 1;
            } else {
                high = middle;
            }
        }
        return samples.get(high);
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
