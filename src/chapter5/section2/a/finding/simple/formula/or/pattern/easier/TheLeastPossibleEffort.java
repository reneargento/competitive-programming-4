package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/12/24.
 */
public class TheLeastPossibleEffort {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            double halfRows = Math.ceil(rows / 2.0);

            int startingPositions = (int) halfRows * (int) Math.ceil(columns / 2.0);
            if (rows == columns) {
                startingPositions -= (int) (halfRows * (halfRows - 1) / 2.0);
            }
            outputWriter.printLine(startingPositions);
        }
        outputWriter.flush();
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
