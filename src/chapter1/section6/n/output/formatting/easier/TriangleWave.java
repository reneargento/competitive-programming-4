package chapter1.section6.n.output.formatting.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/12/20.
 */
public class TriangleWave {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter out = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                out.printLine();
            }

            int amplitude = FastReader.nextInt();
            int frequency = FastReader.nextInt();

            for (int f = 0; f < frequency; f++) {
                if (f > 0) {
                    out.printLine();
                }

                for (int a = 1; a <= amplitude; a++) {
                    printAmplitude(a, out);
                }
                for (int a = amplitude - 1; a >= 1; a--) {
                    printAmplitude(a, out);
                }
            }
        }
        out.flush();
        out.close();
    }

    private static void printAmplitude(int amplitude, OutputWriter out) {
        for (int i = 0; i < amplitude; i++) {
            out.print(amplitude);
        }
        out.printLine();
    }

    private static class FastReader {
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
            for (int i = 0; i < objects.length; i++)
            {
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
