package chapter5.section4.e.others.harder;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 07/01/26.
 */
public class Tiling {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        BigInteger[] ways = countTilingWays();

        while (line != null) {
            int columns = Integer.parseInt(line);
            outputWriter.printLine(ways[columns]);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigInteger[] countTilingWays() {
        BigInteger[] ways = new BigInteger[251];
        ways[0] = BigInteger.ONE;
        ways[1] = BigInteger.ONE;
        BigInteger bigInteger2 = new BigInteger("2");

        for (int i = 2; i < ways.length; i++) {
            ways[i] = ways[i - 1].add(ways[i - 2].multiply(bigInteger2));
        }
        return ways;
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
