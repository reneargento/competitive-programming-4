package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/12/25.
 */
public class Diagonal {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long diagonals = FastReader.nextLong();
        int caseId = 1;

        while (diagonals != 0) {
            double[] result = quadraticFormula(1, -3, -2 * diagonals);
            long minimumSides = (long) Math.ceil(result[0]);
            outputWriter.printLine(String.format("Case %d: %d", caseId, minimumSides));

            caseId++;
            diagonals = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    private static double[] quadraticFormula(long a, long b, long c) {
        double[] result = new double[2];
        double delta = b * b - 4 * a * c;
        result[0] = (-b + Math.sqrt(delta)) / (2 * a);
        result[1] = (-b - Math.sqrt(delta)) / (2 * a);
        return result;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
