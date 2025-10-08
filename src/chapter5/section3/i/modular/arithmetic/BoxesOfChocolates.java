package chapter5.section3.i.modular.arithmetic;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/10/25.
 */
public class BoxesOfChocolates {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int friends = FastReader.nextInt();
            int boxes = FastReader.nextInt();
            int chocolates = 0;

            for (int b = 0; b < boxes; b++) {
                int boxInformation = FastReader.nextInt();
                int boxMultiplier = 1;
                for (int i = 0; i < boxInformation; i++) {
                    boxMultiplier *= FastReader.nextInt();
                    boxMultiplier %= friends;
                }
                chocolates += boxMultiplier;
            }
            chocolates %= friends;
            outputWriter.printLine(chocolates);
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
