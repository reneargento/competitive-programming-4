package chapter3.section4.e.non.classical.easier;

import java.io.*;

/**
 * Created by Rene Argento on 26/07/22.
 */
public class HayBales {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String hayBales = FastReader.getLine();
        int minimumSteps = computeMinimumSteps(hayBales.toCharArray());
        outputWriter.printLine(minimumSteps);
        outputWriter.flush();
    }

    private static int computeMinimumSteps(char[] hayBales) {
        int steps = 0;
        while (!isSorted(hayBales)) {
            boolean didGoodMove = false;

            for (int i = 0; i < hayBales.length - 2; i++) {
                if (hayBales[i] == 'P' &&
                        ((hayBales[i + 1] == 'P' && hayBales[i + 2] == 'C')
                                || (hayBales[i + 1] == 'C' && hayBales[i + 2] == 'C')
                        )) {
                    hayBales[i] = 'C';
                    hayBales[i + 2] = 'P';
                    steps++;
                    didGoodMove = true;
                }
            }

            if (!didGoodMove) {
                for (int i = 0; i < hayBales.length - 1; i++) {
                    if (hayBales[i] == 'P' && hayBales[i + 1] == 'C') {
                        hayBales[i] = 'C';
                        hayBales[i + 1] = 'P';
                        steps++;
                        break;
                    }
                }
            }
        }
        return steps;
    }

    private static boolean isSorted(char[] hayBales) {
        for (int i = 0; i < hayBales.length - 1; i++) {
            if (hayBales[i] == 'P' && hayBales[i + 1] == 'C') {
                return false;
            }
        }
        return true;
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
