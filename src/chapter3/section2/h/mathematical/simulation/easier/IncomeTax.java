package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/12/21.
 */
public class IncomeTax {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int startValue = FastReader.nextInt();
        int taxPercent = FastReader.nextInt();

        while (startValue != 0 || taxPercent != 0) {

            long highestHazardIncome = computeHighestHazardIncome(startValue, taxPercent);

            if (highestHazardIncome == -1) {
                outputWriter.printLine("Not found");
            } else {
                outputWriter.printLine(highestHazardIncome);
            }
            startValue = FastReader.nextInt();
            taxPercent = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeHighestHazardIncome(long startValue, int taxPercent) {
        if (taxPercent == 0 || taxPercent == 100 || startValue == 1) {
            return -1;
        }

        long highestHazardIncome = 100 * (startValue - 1) / (100 - taxPercent);
        long modResult = 100 * (startValue - 1) % (100 - taxPercent);

        if (highestHazardIncome < startValue ||
                (highestHazardIncome == startValue && modResult == 0)) {
            return -1;
        }

        if (modResult == 0) {
            return highestHazardIncome - 1;
        } else {
            return (long) Math.floor(highestHazardIncome);
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
