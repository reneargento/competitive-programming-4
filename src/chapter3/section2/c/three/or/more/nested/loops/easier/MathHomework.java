package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/11/21.
 */
public class MathHomework {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int legs1 = FastReader.nextInt();
        int legs2 = FastReader.nextInt();
        int legs3 = FastReader.nextInt();
        int totalLegs = FastReader.nextInt();
        boolean possible = false;

        for (int i = 0; i <= 250; i++) {
            for (int j = 0; j <= 250; j++) {
                for (int k = 0; k <= 250; k++) {
                    int currentLegs = legs1 * i + legs2 * j + legs3 * k;
                    if (currentLegs > totalLegs) {
                        break;
                    }

                    if (currentLegs == totalLegs) {
                        possible = true;
                        outputWriter.printLine(String.format("%d %d %d", i, j, k));
                    }
                }
            }
        }
        if (!possible) {
            outputWriter.printLine("impossible");
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
