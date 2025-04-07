package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;

/**
 * Created by Rene Argento on 05/04/25.
 */
public class SierpinskiCircumference {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseId = 1;
        String line = FastReader.getLine();
        while (line != null) {
            int iterations = Integer.parseInt(line);
            int decimalDigitsNeeded = computeDecimalDigitsNeeded(iterations);
            outputWriter.printLine(String.format("Case %d: %d", caseId, decimalDigitsNeeded));

            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeDecimalDigitsNeeded(int iterations) {
        return (int) (iterations * Math.log10(1.5) + Math.log10(3)) + 1;
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
