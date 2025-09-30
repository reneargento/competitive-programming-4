package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 19/09/25.
 */
public class FundamentalNeighbors {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Integer[] primeNumbers = eratosthenesSieve();

        String line = FastReader.getLine();
        while (line != null) {
            int number = Integer.parseInt(line);
            long neighbor = getNeighbor(primeNumbers, number);
            outputWriter.printLine(number + " " + neighbor);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long getNeighbor(Integer[] primeNumbers, long number) {
        long neighbor = 1;
        int maxNumber = (int) Math.ceil(Math.sqrt(number));

        for (int primeNumber : primeNumbers) {
            if (primeNumber > maxNumber) {
                break;
            }
            int exponent = 0;

            while (number % primeNumber == 0) {
                exponent++;
                number /= primeNumber;
            }

            if (exponent > 0) {
                neighbor *= (long) Math.pow(exponent, primeNumber);
            }
        }
        return neighbor;
    }

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        int maxNumber = 100000;
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
