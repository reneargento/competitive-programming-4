package chapter5.section4.e.others.harder;

import java.io.*;

/**
 * Created by Rene Argento on 04/01/26.
 */
public class AGraphProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] subsets = countSubsets();

        String line = FastReader.getLine();
        while (line != null) {
            int nodes = Integer.parseInt(line);
            outputWriter.printLine(subsets[nodes + 6]);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    // https://oeis.org/A000931
    private static int[] countSubsets() {
        int[] subsets = new int[85];
        subsets[0] = 1;

        for (int i = 3; i < subsets.length; i++) {
            subsets[i] = subsets[i - 2] + subsets[i - 3];
        }
        return subsets;
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
