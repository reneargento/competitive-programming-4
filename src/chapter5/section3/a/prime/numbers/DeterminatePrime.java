package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/05/25.
 */
public class DeterminatePrime {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int start = FastReader.nextInt();
        int end = FastReader.nextInt();

        Integer[] primeNumbers = eratosthenesSieve(32000);
        List<List<Integer>> determinatePrimes = computeDeterminatePrimes(primeNumbers);

        while (start != 0 || end != 0) {
            printDeterminatePrimesInRange(determinatePrimes, outputWriter, start, end);
            start = FastReader.nextInt();
            end = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Integer[] eratosthenesSieve(long number) {
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[(int) number + 1];

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

    private static List<List<Integer>> computeDeterminatePrimes(Integer[] primeNumbers) {
        List<List<Integer>> determinatePrimes = new ArrayList<>();

        for (int i = 0; i < primeNumbers.length - 2; i++) {
            int distance1 = primeNumbers[i + 1] - primeNumbers[i];
            int distance2 = primeNumbers[i + 2] - primeNumbers[i + 1];

            if (distance1 == distance2) {
                List<Integer> sequence = new ArrayList<>();
                int lastComputedIndex = i + 2;
                sequence.add(primeNumbers[i]);
                sequence.add(primeNumbers[i + 1]);
                sequence.add(primeNumbers[i + 2]);

                for (int j = i + 3; j < primeNumbers.length; j++) {
                    int candidateDistance = primeNumbers[j] - primeNumbers[j - 1];
                    if (candidateDistance == distance1) {
                        sequence.add(primeNumbers[j]);
                    } else {
                        break;
                    }
                }

                determinatePrimes.add(sequence);
                i = lastComputedIndex - 1;
            }
        }
        return determinatePrimes;
    }

    private static void printDeterminatePrimesInRange(List<List<Integer>> determinatePrimes, OutputWriter outputWriter,
                                                      int start, int end) {
        int min = Math.min(start, end);
        int max = Math.max(start, end);

        for (List<Integer> sequence : determinatePrimes) {
            if (sequence.get(0) >= min && sequence.get(sequence.size() - 1) <= max) {
                outputWriter.print(sequence.get(0));
                for (int i = 1; i < sequence.size(); i++) {
                    outputWriter.print(" " + sequence.get(i));
                }
                outputWriter.printLine();
            }
        }
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
