package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class SodaSurplerUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int sodaBottles = FastReader.nextInt() + FastReader.nextInt();
            int bottlesToRecycle = FastReader.nextInt();

            int bottlesDrank = computeBottlesDrank(sodaBottles, bottlesToRecycle);
            outputWriter.printLine(bottlesDrank);
        }
        outputWriter.flush();
    }

    private static int computeBottlesDrank(int sodaBottles, int bottlesToRecycle) {
        int bottlesDrank = 0;

        while (sodaBottles >= bottlesToRecycle) {
            int newBottles = sodaBottles / bottlesToRecycle;
            bottlesDrank += newBottles;

            sodaBottles = newBottles + (sodaBottles % bottlesToRecycle);
        }
        return bottlesDrank;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
