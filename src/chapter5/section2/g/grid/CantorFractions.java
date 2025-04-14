package chapter5.section2.g.grid;

import java.io.*;

/**
 * Created by Rene Argento on 12/04/25.
 */
public class CantorFractions {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            long index = Long.parseLong(line);

            // https://oeis.org/A004736
            long indexTimes2SqrtRound = Math.round(Math.sqrt(2 * index));
            long numerator = (2 - index * 2L + indexTimes2SqrtRound + (long) Math.pow(indexTimes2SqrtRound, 2)) / 2;

            // https://oeis.org/A002260
            long m = (long) Math.floor((-1 + Math.sqrt(8 * index - 7)) / 2);
            long denominator = index - m * (m + 1) / 2;
            outputWriter.printLine(numerator + "/" + denominator);
            line = FastReader.getLine();
        }
        outputWriter.flush();
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
