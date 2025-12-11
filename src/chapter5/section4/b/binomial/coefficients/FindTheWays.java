package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 08/12/25.
 */
public class FindTheWays {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int n = Integer.parseInt(data[0]);
            int k = Integer.parseInt(data[1]);

            int waysDigits = computeWaysDigits(n, k);
            outputWriter.printLine(waysDigits);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeWaysDigits(int n, int k) {
        BigInteger result = BigInteger.ONE;

        for (int i = 0; i < k; i++) {
            result = result.multiply(BigInteger.valueOf(n - i))
                    .divide(BigInteger.valueOf(i + 1));
        }
        return result.toString().length();
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

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
