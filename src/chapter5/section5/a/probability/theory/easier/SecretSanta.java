package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/02/26.
 */
public class SecretSanta {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long citizens = FastReader.nextLong();
        double probability = computeProbability(citizens);
        outputWriter.printLine(probability);
        outputWriter.flush();
    }

    private static double computeProbability(long citizens) {
        double probability = 0;
        double factorial = 1;
        citizens = Math.min(citizens, 100);

        for (int i = 1; i <= citizens; i++) {
            factorial *= i;
            probability = (i % 2 == 0) ? probability - (1 / factorial) : probability + (1 / factorial);
        }
        return probability;
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
