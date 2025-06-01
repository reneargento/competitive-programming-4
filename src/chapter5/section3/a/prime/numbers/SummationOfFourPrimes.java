package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 29/05/25.
 */
public class SummationOfFourPrimes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Integer[] primes = eratosthenesSieve();

        String line = FastReader.getLine();
        while (line != null) {
            int number = Integer.parseInt(line);

            List<Integer> result = computeFourSum(primes, number);
            if (result == null) {
                outputWriter.printLine("Impossible.");
            } else {
                outputWriter.print(result.get(0));
                for (int i = 1; i < result.size(); i++) {
                    outputWriter.print(" " + result.get(i));
                }
                outputWriter.printLine();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean[] isPrime;

    private static List<Integer> computeFourSum(Integer[] primes, int number) {
        if (number < 8) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        result.add(2);

        if (number % 2 == 0) {
            result.add(2);
            number -= 4;
        } else {
            result.add(3);
            number -= 5;
        }

        for (int primeCandidate : primes) {
            int lastNumber = number - primeCandidate;
            if (isPrime[lastNumber]) {
                result.add(primeCandidate);
                result.add(lastNumber);
                return result;
            }
        }
        return null;
    }

    private static Integer[] eratosthenesSieve() {
        long number = 10000001;
        List<Integer> primeNumbers = new ArrayList<>();
        isPrime = new boolean[(int) number + 1];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i <= number; i++) {
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
