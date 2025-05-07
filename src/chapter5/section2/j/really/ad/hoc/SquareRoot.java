package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 04/05/25.
 */
public class SquareRoot {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            BigInteger number = new BigInteger(FastReader.getLine());
            BigInteger squareRoot = computeSquareRoot(number);

            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(squareRoot);
        }
        outputWriter.flush();
    }

    // Newton's method
    private static BigInteger computeSquareRoot(BigInteger number) {
        BigInteger squareRoot;
        BigInteger x = number;
        BigInteger divider = new BigInteger("2");

        while (true) {
            BigInteger sum = x.add(number.divide(x));
            squareRoot = sum.divide(divider);

            if (squareRoot.subtract(x).abs().compareTo(BigInteger.ONE) < 0) {
                break;
            }
            x = squareRoot;
        }
        return squareRoot;
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
