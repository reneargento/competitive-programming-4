package chapter5.section3.b.probabilistic.prime.testing;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 01/06/25.
 */
public class PerfectNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        FastReader.getLine();
        String line = FastReader.getLine();
        String[] data = line.split(",");
        BigInteger bigInteger2 = new BigInteger("2");

        for (String value : data) {
            int number = Integer.parseInt(value);
            boolean generatesPerfectsNumber = generatesPerfectsNumber(number, bigInteger2);
            outputWriter.printLine(generatesPerfectsNumber ? "Yes" : "No");
        }
        outputWriter.flush();
    }

    private static boolean generatesPerfectsNumber(int number, BigInteger bigInteger2) {
        BigInteger numberBI = new BigInteger(String.valueOf(number));
        BigInteger twoPowerNumberMinus1 = bigInteger2.pow(number).subtract(BigInteger.ONE);

        return numberBI.isProbablePrime(10) && twoPowerNumberMinus1.isProbablePrime(10);
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
