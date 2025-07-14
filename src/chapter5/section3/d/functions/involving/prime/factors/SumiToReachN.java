package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 13/07/25.
 */
public class SumiToReachN {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Integer[] primeNumbers = eratosthenesSieve();

        String line = FastReader.getLine();
        while (line != null) {
            long number = Long.parseLong(line);
            int representations = computeRepresentations(primeNumbers, number);
            outputWriter.printLine(representations);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeRepresentations(Integer[] primeNumbers, long number) {
        if (number == 0) {
            return 0;
        }

        int representations = 1;
        while ((number & 1) == 0) {
            number >>= 1;
        }

        for (int prime : primeNumbers) {
            int upperLimit = (int) Math.sqrt(number);
            if (prime > upperLimit) {
                break;
            }

            int primeFactors = 1;
            while (number % prime == 0) {
                primeFactors++;
                number /= prime;
            }
            representations *= primeFactors;
        }

        if (number > 1) {
            representations *= 2;
        }
        return representations;
    }

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        int maxNumber = 10000001;
        boolean[] isPrime = new boolean[maxNumber];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < isPrime.length; i++) {
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
