package chapter5.section6;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/03/26.
 */
public class HappyHappyPrimePrime {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        boolean[] isPrime = eratosthenesSieve(10001);

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            int number = FastReader.nextInt();
            String result = isHappyPrime(isPrime, number);
            outputWriter.printLine(String.format("%d %d %s", dataSetNumber, number, result));
        }
        outputWriter.flush();
    }

    private static String isHappyPrime(boolean[] isPrime, int number) {
        return isPrime[number] && isHappy(number) ? "YES" : "NO";
    }

    private static boolean isHappy(int number) {
        int tortoise = number;
        int hare = getNextNumber(number);

        while (tortoise != hare) {
            tortoise = getNextNumber(tortoise);
            hare = getNextNumber(getNextNumber(hare));
        }
        return hare == 1;
    }

    private static int getNextNumber(int number) {
        int nextNumber = 0;
        while (number > 0) {
            int digit = number % 10;
            nextNumber += digit * digit;
            number /= 10;
        }
        return nextNumber;
    }

    private static boolean[] eratosthenesSieve(long number) {
        int maxNumberToCheck = (int) Math.floor(Math.sqrt(number));
        boolean[] isPrime = new boolean[(int) number + 1];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i <= maxNumberToCheck; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
            }
        }
        return isPrime;
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
