package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/06/2026.
 */
public class DetailedDifferences {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string1 = FastReader.next();
            String string2 = FastReader.next();
            compareStrings(string1, string2, outputWriter);
        }
        outputWriter.flush();
    }

    private static void compareStrings(String string1, String string2, OutputWriter outputWriter) {
        outputWriter.printLine(string1);
        outputWriter.printLine(string2);
        for (int i = 0; i < string1.length(); i++) {
            if (string1.charAt(i) == string2.charAt(i)) {
                outputWriter.print(".");
            } else {
                outputWriter.print("*");
            }
        }
        outputWriter.printLine();
        outputWriter.printLine();
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