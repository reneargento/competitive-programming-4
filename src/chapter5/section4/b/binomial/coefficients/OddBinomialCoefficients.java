package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/12/25.
 */
public class OddBinomialCoefficients {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<Double, Long> nToT2Map = new HashMap<>();
        long n = FastReader.nextLong();
        long t2 = computeT2(nToT2Map, n);

        outputWriter.printLine(t2);
        outputWriter.flush();
    }

    // https://oeis.org/A006046
    private static long computeT2(Map<Double, Long> nToT2Map, double n) {
        if (nToT2Map.containsKey(n)) {
            return nToT2Map.get(n);
        }
        if (n == 1) {
            return 1;
        }

        long t2 = 2 * computeT2(nToT2Map, Math.floor(n / 2)) + computeT2(nToT2Map, Math.ceil(n / 2));
        nToT2Map.put(n, t2);
        return t2;
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
