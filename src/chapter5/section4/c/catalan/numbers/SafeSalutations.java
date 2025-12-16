package chapter5.section4.c.catalan.numbers;

import java.io.*;

/**
 * Created by Rene Argento on 12/12/25.
 */
public class SafeSalutations {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long[] catalanNumbers = catalanNumbers();

        String line = FastReader.getLine();
        int dataSet = 1;
        while (line != null) {
            if (line.isEmpty()) {
                line = FastReader.getLine();
            }
            int pairs = Integer.parseInt(line);

            if (dataSet > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(catalanNumbers[pairs]);

            line = FastReader.getLine();
            dataSet++;
        }
        outputWriter.flush();
    }

    private static long[] catalanNumbers() {
        long[] catalanNumbers = new long[11];
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
