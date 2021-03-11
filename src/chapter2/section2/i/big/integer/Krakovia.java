package chapter2.section2.i.big.integer;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/03/21.
 */
public class Krakovia {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int items = FastReader.nextInt();
        BigInteger friends = BigInteger.valueOf(FastReader.nextInt());
        int billNumber = 1;

        while (items != 0 || friends.longValue() != 0) {
            BigInteger totalCost = BigInteger.ZERO;

            for (int i = 0; i < items; i++) {
                BigInteger itemValue = new BigInteger(FastReader.next());
                totalCost = totalCost.add(itemValue);
            }
            BigInteger valueToPay = totalCost.divide(friends);

            String output = String.format("Bill #%d costs %s: each friend should pay %s\n", billNumber,
                    totalCost, valueToPay);
            outputWriter.printLine(output);

            items = FastReader.nextInt();
            friends = BigInteger.valueOf(FastReader.nextInt());
            billNumber++;
        }
        outputWriter.flush();
        outputWriter.close();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
