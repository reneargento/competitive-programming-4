package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/11/25.
 */
public class Batmanacci {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int n = FastReader.nextInt();
        BigInteger k = BigInteger.valueOf(FastReader.nextLong());

        BigInteger[] fibonacci = computeFibonacci();
        char kthChar = getKthChar(fibonacci, n, k);

        outputWriter.printLine(kthChar);
        outputWriter.flush();
    }

    private static char getKthChar(BigInteger[] fibonacci, int n, BigInteger k) {
        String[] result = { "", "N", "A", "NA" };

        while (n > 3) {
            if (k.compareTo(fibonacci[n - 2]) <= 0) {
                n -= 2;
            } else if (k.compareTo(fibonacci[n - 1]) > 0) {
                k = k.subtract(fibonacci[n - 1]);
                n -= 2;
            } else {
                k = k.subtract(fibonacci[n - 2]);
                n -= 3;
            }
        }
        return result[n].charAt(k.intValue() - 1);
    }

    private static BigInteger[] computeFibonacci() {
        BigInteger[] fibonacci = new BigInteger[100001];
        fibonacci[0] = BigInteger.ZERO;
        fibonacci[1] = BigInteger.ONE;

        for (int i = 2; i < fibonacci.length; i++) {
            fibonacci[i] = fibonacci[i - 1].add(fibonacci[i - 2]);
        }
        return fibonacci;
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

        public void flush() {
            writer.flush();
        }
    }
}
