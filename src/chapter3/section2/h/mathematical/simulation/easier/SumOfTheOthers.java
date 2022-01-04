package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class SumOfTheOthers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int[] numbers = new int[data.length];

            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Integer.parseInt(data[i]);
            }
            int sum = getSum(numbers);
            outputWriter.printLine(sum);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int getSum(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            int sum = 0;

            for (int j = 0; j < numbers.length; j++) {
                if (i == j) {
                    continue;
                }
                sum += numbers[j];
            }

            if (sum == numbers[i]) {
                return numbers[i];
            }
        }
        return -1;
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
