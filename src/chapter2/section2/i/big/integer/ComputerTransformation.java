package chapter2.section2.i.big.integer;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 12/03/21.
 */
public class ComputerTransformation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        BigInteger[] zeroPairs = precomputeSequence();

        String numberLine = FastReader.getLine();
        while (numberLine != null) {
            int number = Integer.parseInt(numberLine);
            outputWriter.printLine(zeroPairs[number]);

            numberLine = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigInteger[] precomputeSequence() {
        BigInteger[] zeroPairs = new BigInteger[1001];
        zeroPairs[0] = BigInteger.ZERO;
        zeroPairs[1] = BigInteger.ZERO;
        zeroPairs[2] = BigInteger.ONE;

        for (int i = 3; i < zeroPairs.length; i++) {
            BigInteger pairs = zeroPairs[i - 1].multiply(BigInteger.valueOf(2));

            if (i % 2 == 0) {
                pairs = pairs.add(BigInteger.ONE);
            } else {
                pairs = pairs.subtract(BigInteger.ONE);
            }
            zeroPairs[i] = pairs;
        }
        return zeroPairs;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
