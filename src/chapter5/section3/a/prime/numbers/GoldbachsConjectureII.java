package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/05/25.
 */
public class GoldbachsConjectureII {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Integer[] primeNumbers = eratosthenesSieve();

        int number = FastReader.nextInt();
        while (number != 0) {
            int primePairs = countPrimePairs(primeNumbers, number);
            outputWriter.printLine(primePairs);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int countPrimePairs(Integer[] primeNumbers, int number) {
        int primePairs = 0;

        Set<Integer> primeSet = new HashSet<>();
        for (int primeNumber : primeNumbers) {
            primeSet.add(primeNumber);
        }

        for (int primeNumber : primeNumbers) {
            int targetNumber = number - primeNumber;
            if (primeSet.contains(targetNumber)) {
                primePairs++;

                if (primeNumber == targetNumber) {
                    primePairs++;
                }
            }
        }
        return primePairs / 2;
    }

    private static Integer[] eratosthenesSieve() {
        int maxNumber = (int) Math.pow(2, 15);
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[maxNumber];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (int i = 2; i < maxNumber; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < isPrime.length; j += i) {
                    isPrime[j] = false;
                }
                primeNumbers.add(i);
            }
        }
        return primeNumbers.toArray(new Integer[0]);
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

        public void flush() {
            writer.flush();
        }
    }
}
