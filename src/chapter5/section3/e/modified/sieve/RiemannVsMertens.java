package chapter5.section3.e.modified.sieve;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/07/25.
 */
public class RiemannVsMertens {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] mu = computeMu();
        int[] m = computeM(mu);

        int number = FastReader.nextInt();
        while (number != 0) {
            outputWriter.printLine(String.format("%8d%8d%8d", number, mu[number], m[number]));
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[] computeMu() {
        int maxNumber = 1000001;
        int[] uniquePFs = eratosthenesSieveCountUniquePFs(maxNumber);
        int[] primeFactorsCount = eratosthenesSieveCountPFs(maxNumber);

        int[] mu = new int[uniquePFs.length];
        for (int i = 1; i < mu.length; i++) {
            if (uniquePFs[i] == primeFactorsCount[i]) {
                if (primeFactorsCount[i] % 2 == 0) {
                    mu[i] = 1;
                } else {
                    mu[i] = -1;
                }
            }
        }
        return mu;
    }

    private static int[] computeM(int[] mu) {
        int[] m = new int[mu.length];
        m[1] = mu[1];

        for (int i = 2; i < mu.length; i++) {
            m[i] = m[i - 1] + mu[i];
        }
        return m;
    }

    private static int[] eratosthenesSieveCountUniquePFs(int maxNumber) {
        int[] uniquePFs = new int[maxNumber];

        for (int i = 2; i < uniquePFs.length; i++) {
            if (uniquePFs[i] == 0) {
                for (int j = i; j < uniquePFs.length; j += i) {
                    uniquePFs[j]++;
                }
            }
        }
        return uniquePFs;
    }

    private static int[] eratosthenesSieveCountPFs(int maxNumber) {
        int[] primeFactorsCount = new int[maxNumber];
        int[] currentNumber = new int[maxNumber];
        for (int i = 0; i < currentNumber.length; i++) {
            currentNumber[i] = i;
        }

        for (int i = 2; i < primeFactorsCount.length; i++) {
            if (primeFactorsCount[i] == 0) {
                for (int j = i; j < primeFactorsCount.length; j += i) {
                    while (currentNumber[j] % i == 0) {
                        primeFactorsCount[j]++;
                        currentNumber[j] /= i;
                    }
                }
            }
        }
        return primeFactorsCount;
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
