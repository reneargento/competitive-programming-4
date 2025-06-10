package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 30/05/25.
 */
public class TwinPrimes {

    private static class TwinPrime {
        int prime1;
        int prime2;

        public TwinPrime(int prime1, int prime2) {
            this.prime1 = prime1;
            this.prime2 = prime2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        TwinPrime[] twinPrimes = computeTwinPrimes();
        String line = FastReader.getLine();

        while (line != null) {
            int sIndex = Integer.parseInt(line) - 1;
            TwinPrime twinPrime = twinPrimes[sIndex];
            outputWriter.printLine(String.format("(%d, %d)", twinPrime.prime1, twinPrime.prime2));

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static TwinPrime[] computeTwinPrimes() {
        TwinPrime[] twinPrimes = new TwinPrime[100001];
        int index = 0;

        Integer[] primeNumbers = eratosthenesSieve();
        for (int i = 1; i < primeNumbers.length && index < twinPrimes.length; i++) {
            int difference = primeNumbers[i] - primeNumbers[i - 1];
            if (difference == 2) {
                twinPrimes[index] = new TwinPrime(primeNumbers[i - 1], primeNumbers[i]);
                index++;
            }
        }
        return twinPrimes;
    }

    private static Integer[] eratosthenesSieve() {
        long maxNumber = 20000001;
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[(int) maxNumber];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < maxNumber; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                primeNumbers.add((int) i);
            }
        }
        return primeNumbers.toArray(new Integer[0]);
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
