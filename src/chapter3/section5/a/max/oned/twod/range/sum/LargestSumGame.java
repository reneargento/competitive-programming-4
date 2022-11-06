package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;

/**
 * Created by Rene Argento on 05/11/22.
 */
public class LargestSumGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] values = line.split(" ");
            int maxSum = kadane(values);
            outputWriter.printLine(maxSum);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int kadane(String[] values) {
        int maxSum = 0;
        int currentSum = 0;

        for (String valueString : values) {
            currentSum += Integer.parseInt(valueString);
            maxSum = Math.max(maxSum, currentSum);
            currentSum = Math.max(currentSum, 0);
        }
        return maxSum;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
