package chapter5.section8.section4;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/04/26.
 */
public class PowersAndModulus {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int mod = FastReader.nextInt();
        int power = FastReader.nextInt();

        long powerSum = computePowerSum(mod, power);
        outputWriter.printLine(powerSum);
        outputWriter.flush();
    }

    private static long computePowerSum(int mod, int power) {
        if (mod % 2 == 0) {
            BigInteger baseBI = BigInteger.valueOf(mod);
            BigInteger powerBI = BigInteger.valueOf(power);
            return baseBI.divide(BigInteger.valueOf(2)).modPow(powerBI, baseBI).longValue();
        }
        return 0;
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
