package chapter3.section4.a.classical;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/05/22.
 */
public class Scarecrow {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            FastReader.nextInt();
            String field = FastReader.next();

            int scarecrowsNeeded = countScarecrows(field);
            outputWriter.printLine(String.format("Case %d: %d", t, scarecrowsNeeded));
        }
        outputWriter.flush();
    }

    private static int countScarecrows(String field) {
        int scarecrowsNeeded = 0;

        for (int i = 0; i < field.length(); i++) {
            if (field.charAt(i) == '.') {
                scarecrowsNeeded++;
                i += 2;
            }
        }
        return scarecrowsNeeded;
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
