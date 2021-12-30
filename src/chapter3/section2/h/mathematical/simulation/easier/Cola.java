package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;

/**
 * Created by Rene Argento on 29/12/21.
 */
public class Cola {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int bottles = Integer.parseInt(line);
            int maximumBottles = computeMaximumBottles(bottles);
            outputWriter.printLine(maximumBottles);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMaximumBottles(int bottles) {
        int totalBottlesWithoutBorrowing = computeBottles(bottles);
        int totalBottlesBorrowing1 = computeBottles(bottles + 1) - 1;
        return Math.max(totalBottlesWithoutBorrowing, totalBottlesBorrowing1);
    }

    private static int computeBottles(int bottles) {
        int totalBottles = bottles;

        while (bottles / 3 >= 1) {
            totalBottles += (bottles / 3);
            bottles = (bottles / 3) + (bottles % 3);
        }
        return totalBottles;
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
