package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/01/22.
 */
public class BarbarianTribes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int garedMaids = FastReader.nextInt();
        int kekaMaids = FastReader.nextInt();
        int skipLength = FastReader.nextInt();

        while (garedMaids != 0 || kekaMaids != 0 || skipLength != 0) {
            if (kekaMaids % 2 == 0) {
                outputWriter.printLine("Gared");
            } else {
                outputWriter.printLine("Keka");
            }

            garedMaids = FastReader.nextInt();
            kekaMaids = FastReader.nextInt();
            skipLength = FastReader.nextInt();
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
