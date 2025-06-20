package chapter5.section3.b.probabilistic.prime.testing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/06/25.
 */
public class GoldbachsConjecture {

    private static class Sum {
        int prime1;
        int prime2;

        public Sum(int prime1, int prime2) {
            this.prime1 = prime1;
            this.prime2 = prime2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        Integer[] primeNumbers = eratosthenesSieve();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            List<Sum> representations = computeRepresentations(primeNumbers, number);

            outputWriter.printLine(String.format("%d has %d representation(s)", number, representations.size()));
            for (Sum sum : representations) {
                outputWriter.printLine(sum.prime1 + "+" + sum.prime2);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static List<Sum> computeRepresentations(Integer[] primeNumbers, int number) {
        List<Sum> representations = new ArrayList<>();
        int halfNumber = number / 2;

        for (int primeNumber : primeNumbers) {
            if (primeNumber > halfNumber) {
                break;
            }
            int otherPrime = number - primeNumber;
            if (isPrime[otherPrime]) {
                representations.add(new Sum(primeNumber, otherPrime));
            }
        }
        return representations;
    }

    private static boolean[] isPrime;

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        int maxNumber = 32001;
        isPrime = new boolean[(int) maxNumber];

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