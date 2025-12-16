package chapter5.section4.c.catalan.numbers;

import java.io.*;

/**
 * Created by Rene Argento on 11/12/25.
 */
public class HowManyNodes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long[] catalanNumbers = catalanNumbers();
        String line = FastReader.getLine();

        while (line != null) {
            long number = Long.parseLong(line);
            int index = computeIndex(catalanNumbers, number);
            outputWriter.printLine(index);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeIndex(long[] catalanNumbers, long number) {
        if (number == 0) {
            return 0;
        }

        for (int i = 1; i < catalanNumbers.length; i++) {
            if (catalanNumbers[i] >= number) {
                return i;
            }
        }
        return 0;
    }

    private static long[] catalanNumbers() {
        long[] catalanNumbers = new long[20];
        catalanNumbers[0] = 1;

        for (int i = 0; i < catalanNumbers.length - 1; i++) {
            catalanNumbers[i + 1] = catalanNumbers[i] * (4 * i + 2) / (i + 2);
        }
        return catalanNumbers;
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

        public void flush() {
            writer.flush();
        }
    }
}
