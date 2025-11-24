package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 22/11/25.
 */
public class RayThroughGlasses {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        BigInteger[] fibonacci = computeFibonacci();

        String line = FastReader.getLine();
        while (line != null) {
            int directionChanges = Integer.parseInt(line);
            outputWriter.printLine(fibonacci[directionChanges]);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigInteger[] computeFibonacci() {
        BigInteger[] fibonacci = new BigInteger[1001];
        fibonacci[0] = BigInteger.ONE;
        fibonacci[1] = new BigInteger("2");

        for (int i = 2; i < fibonacci.length; i++) {
            fibonacci[i] = fibonacci[i - 1].add(fibonacci[i - 2]);
        }
        return fibonacci;
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
