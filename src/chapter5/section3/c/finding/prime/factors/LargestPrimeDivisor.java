package chapter5.section3.c.finding.prime.factors;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 22/06/25.
 */
public class LargestPrimeDivisor {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        BigInteger number = new BigInteger(FastReader.next());
        while (number.compareTo(BigInteger.ZERO) != 0) {
            number = number.abs();
            BigInteger largestPrimeDivisor = getLargestPrimeDivisor(number);
            outputWriter.printLine(largestPrimeDivisor);
            number = new BigInteger(FastReader.next());
        }
        outputWriter.flush();
    }

    private static BigInteger getLargestPrimeDivisor(BigInteger number) {
        List<BigInteger> primeFactors = primeFactors(number);
        if (primeFactors.size() == 1) {
            return new BigInteger("-1");
        }

        Collections.sort(primeFactors);
        return primeFactors.get(primeFactors.size() - 1);
    }

    private static List<BigInteger> primeFactors(BigInteger number) {
        Set<BigInteger> primeFactors = new HashSet<>();
        BigInteger iterator = new BigInteger("2");
        BigInteger iteratorSquare = iterator.multiply(iterator);

        while (iteratorSquare.compareTo(number) <= 0) {
            while (number.mod(iterator).equals(BigInteger.ZERO)) {
                primeFactors.add(iterator);
                number = number.divide(iterator);
            }
            iterator = iterator.add(BigInteger.ONE);
            iteratorSquare = iterator.multiply(iterator);
        }

        if (number.compareTo(BigInteger.ONE) > 0) {
            primeFactors.add(number);
        }
        return new ArrayList<>(primeFactors);
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
