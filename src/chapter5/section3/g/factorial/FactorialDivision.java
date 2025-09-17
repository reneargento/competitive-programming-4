package chapter5.section3.g.factorial;

import java.io.*;

/**
 * Created by Rene Argento on 12/09/25.
 */
public class FactorialDivision {

    private static class FactorialPair {
        long factorial1;
        long factorial2;

        public FactorialPair(long factorial1, long factorial2) {
            this.factorial1 = factorial1;
            this.factorial2 = factorial2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();

        int caseId = 1;
        while (line != null) {
            long k = Integer.parseInt(line);
            FactorialPair result = computeDivision(k);

            outputWriter.print(String.format("Case %d: ", caseId));
            if (result == null) {
                outputWriter.printLine("Impossible");
            } else {
                outputWriter.printLine(result.factorial1 + " " + result.factorial2);
            }
            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static FactorialPair computeDivision(long value) {
        if (value == 1) {
            return null;
        }

        for (long i = 2; i * i < value; i++) {
            long factorial = i;
            for (long j = i + 1; factorial <= value; j++) {
                factorial *= j;

                if (factorial == value) {
                    return new FactorialPair(j, i - 1);
                }
            }
        }
        return new FactorialPair(value, value - 1);
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
