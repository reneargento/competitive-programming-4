package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/03/22.
 */
public class TheNewRuleInEuphomia {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Integer> primeNumbers = eratosthenesSieve(1000000);
        int caseId = 1;
        int price = FastReader.nextInt();

        while (price != 0) {
            long waysToPay = countWaysToPay(price, primeNumbers);
            outputWriter.printLine(String.format("Case %d: %d", caseId, waysToPay));

            caseId++;
            price = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long countWaysToPay(int price, List<Integer> primeNumbers) {
        long waysToPay = 0;
        for (int i = 0; i < primeNumbers.size(); i++) {
            int currentValue = primeNumbers.get(i);
            if (currentValue < price) {
                int upperBound = binarySearchUpperBound(primeNumbers, price, currentValue, i + 1, primeNumbers.size());
                if (upperBound != -1) {
                    waysToPay += upperBound - i;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return waysToPay;
    }

    private static int binarySearchUpperBound(List<Integer> numbers, int targetSum, int currentValue,
                                              int low, int high) {
        while (low <= high) {
            int middle = low + (high - low) / 2;
            int sum = currentValue + numbers.get(middle);

            if (sum > targetSum) {
                high = middle - 1;
            } else {
                int result = middle;
                int candidate = binarySearchUpperBound(numbers, targetSum, currentValue, middle + 1, high);
                if (candidate != -1) {
                    result = candidate;
                }
                return result;
            }
        }
        return -1;
    }

    private static List<Integer> eratosthenesSieve(long number) {
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
        return primeNumbers;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
