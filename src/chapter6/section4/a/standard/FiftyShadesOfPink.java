package chapter6.section4.a.standard;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/08/2026.
 */
public class FiftyShadesOfPink {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int packages = FastReader.nextInt();
        int sessions = 0;

        for (int p = 0; p < packages; p++) {
            String color = FastReader.next().toLowerCase();
            if (color.contains("rose") || color.contains("pink")) {
                sessions++;
            }
        }

        if (sessions == 0) {
            outputWriter.printLine("I must watch Star Wars with my daughter");
        } else {
            outputWriter.printLine(sessions);
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