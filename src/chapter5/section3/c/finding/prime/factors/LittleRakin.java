package chapter5.section3.c.finding.prime.factors;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/06/25.
 */
public class LittleRakin {

    private static class PrimeFactor implements Comparable<PrimeFactor> {
        long value;
        long power;

        public PrimeFactor(long value, long power) {
            this.value = value;
            this.power = power;
        }

        @Override
        public int compareTo(PrimeFactor other) {
            return Long.compare(value, other.value);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int n = FastReader.nextInt();
            int a = FastReader.nextInt();
            int b = FastReader.nextInt();

            List<PrimeFactor> primeFactors = computePrimeFactors(a, b, n);
            for (PrimeFactor primeFactor : primeFactors) {
                outputWriter.printLine(primeFactor.value + " " + primeFactor.power);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static List<PrimeFactor> computePrimeFactors(int a, int b, int n) {
        long[] f = computeF();
        Map<Long, Long> allPrimeFactorsMap = new HashMap<>();
        addPrimes(allPrimeFactorsMap, f, n - 1, a);
        addPrimes(allPrimeFactorsMap, f, n, b);

        List<PrimeFactor> allPrimeFactors = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : allPrimeFactorsMap.entrySet()) {
            allPrimeFactors.add(new PrimeFactor(entry.getKey(), entry.getValue()));
        }
        Collections.sort(allPrimeFactors);
        return allPrimeFactors;
    }

    private static void addPrimes(Map<Long, Long> allPrimeFactorsMap, long[] f, int fIndex, int value) {
        List<PrimeFactor> primeFactors = primeFactors(value);
        for (PrimeFactor primeFactor : primeFactors) {
            long power = allPrimeFactorsMap.getOrDefault(primeFactor.value, 0L);
            long newPower = power + (primeFactor.power * f[fIndex]);
            allPrimeFactorsMap.put(primeFactor.value, newPower);
        }
    }

    private static long[] computeF() {
        long[] f = new long[41];
        f[0] = 0;
        f[1] = 1;

        for (int i = 2; i < f.length; i++) {
            f[i] = f[i - 2] + f[i - 1];
        }
        return f;
    }

    private static List<PrimeFactor> primeFactors(int number) {
        List<PrimeFactor> primeFactors = new ArrayList<>();
        int sqrt = (int) Math.sqrt(number);

        for (int i = 2; i <= sqrt; i++) {
            int power = 0;
            boolean hasFactor = false;

            while (number % i == 0) {
                power++;
                number /= i;
                hasFactor = true;
            }

            if (hasFactor) {
                primeFactors.add(new PrimeFactor(i, power));
            }
        }

        if (number > 1) {
            primeFactors.add(new PrimeFactor(number, 1));
        }
        return primeFactors;
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
