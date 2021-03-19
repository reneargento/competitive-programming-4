package chapter2.section2.i.big.integer;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 18/03/21.
 */
public class GeneralizedRecursiveFunctions {

    private static class Pair {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            Pair pair = (Pair) other;
            return a == pair.a && b == pair.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            String[] values = FastReader.getLine().split(" ");
            BigInteger c = new BigInteger(values[values.length - 2]);
            int d = Integer.parseInt(values[values.length - 1]);

            List<Pair> abPairs = new ArrayList<>();
            for (int i = 0; i < values.length - 2; i += 2) {
                int a = Integer.parseInt(values[i]);
                int b = Integer.parseInt(values[i + 1]);
                abPairs.add(new Pair(a, b));
            }

            Map<Pair, BigInteger> sumDp = new HashMap<>();
            String[] xyValues = FastReader.getLine().split(" ");
            for (int i = 0; i < xyValues.length; i += 2) {
                int x = Integer.parseInt(xyValues[i]);
                int y = Integer.parseInt(xyValues[i + 1]);
                BigInteger result = solveFunction(abPairs, c, d, x, y, sumDp);
                outputWriter.printLine(result);
            }
        }
        outputWriter.flush();
    }

    private static BigInteger solveFunction(List<Pair> abPairs, BigInteger c, int d, int x, int y,
                                            Map<Pair, BigInteger> sumDp) {
        Pair xyPair = new Pair(x, y);
        if (sumDp.containsKey(xyPair)) {
            return sumDp.get(xyPair);
        }

        if (x <= 0 || y <= 0) {
            return BigInteger.valueOf(d);
        }
        BigInteger sum = BigInteger.ZERO;

        for (Pair abPair : abPairs) {
            BigInteger functionValue = solveFunction(abPairs, c, d, x - abPair.a, y - abPair.b, sumDp);
            sum = sum.add(functionValue);
        }
        sum = sum.add(c);
        sumDp.put(xyPair, sum);
        return sum;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
