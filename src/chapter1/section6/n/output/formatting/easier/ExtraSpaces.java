package chapter1.section6.n.output.formatting.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/12/20.
 */
public class ExtraSpaces {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            if (t > 1) {
                outputWriter.printLine();
            }

            outputWriter.printLine("Case " + t + ":");

            int lines = FastReader.nextInt();
            for (int l = 0; l < lines; l++) {
                String line = FastReader.getLine();
                printFormattedLine(line, outputWriter);
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void printFormattedLine(String line, OutputWriter outputWriter) {
        boolean foundSpace = false;

        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);

            if (symbol != ' ') {
                foundSpace = false;
                outputWriter.print(symbol);
            } else {
                if (!foundSpace) {
                    outputWriter.print(symbol);
                    foundSpace = true;
                }
            }
        }
        outputWriter.printLine();
    }

    public static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        // Used to check EOF
        // If getLine() == null, it is a EOF
        // Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
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
