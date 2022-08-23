package chapter3.section4.f.non.classical.harder;

import java.io.*;

/**
 * Created by Rene Argento on 10/08/22.
 */
public class MoliuNumberGenerator {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            long target = Long.parseLong(line);
            int minimumOperations = computeMinimumOperations(target);
            outputWriter.printLine(minimumOperations);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumOperations(long target) {
        int minimumOperations = 0;

        while (target > 0) {
            if (target % 2 == 1) {
                minimumOperations++;

                if (target == 1) {
                    break;
                }

                long nextTarget = (target - 1) / 2;
                if (nextTarget % 2 == 0 || nextTarget == 1) {
                    target--;
                } else {
                    target++;
                }
            }
            target /= 2;
            minimumOperations++;
        }
        return minimumOperations;
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
