package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 26/05/25.
 */
public class PrimeDistance {

    private static class Result {
        int closestPairPrime1;
        int closestPairPrime2;
        int furthestPairPrime1;
        int furthestPairPrime2;

        public Result(int closestPairPrime1, int closestPairPrime2, int furthestPairPrime1, int furthestPairPrime2) {
            this.closestPairPrime1 = closestPairPrime1;
            this.closestPairPrime2 = closestPairPrime2;
            this.furthestPairPrime1 = furthestPairPrime1;
            this.furthestPairPrime2 = furthestPairPrime2;
        }
    }

    private static final long MAX_SIEVE_VALUE = 1000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Integer[] primeNumbers = eratosthenesSieve();

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int lowerLimit = Integer.parseInt(data[0]);
            int upperLimit = Integer.parseInt(data[1]);

            Result result = computeAdjacentPairs(primeNumbers, lowerLimit, upperLimit);
            if (result.closestPairPrime1 == -1) {
                outputWriter.printLine("There are no adjacent primes.");
            } else {
                outputWriter.printLine(String.format("%d,%d are closest, %d,%d are most distant.",
                        result.closestPairPrime1, result.closestPairPrime2, result.furthestPairPrime1,
                        result.furthestPairPrime2));
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeAdjacentPairs(Integer[] primeNumbers, int lowerLimit, int upperLimit) {
        int closestDistance = Integer.MAX_VALUE;
        int furthestDistance = 0;
        int closestPairPrime1 = -1;
        int closestPairPrime2 = -1;
        int furthestPairPrime1 = -1;
        int furthestPairPrime2 = -1;

        int startIndex = binarySearch(primeNumbers, lowerLimit, true);
        int endIndex = binarySearch(primeNumbers, upperLimit, false);

        if (upperLimit < MAX_SIEVE_VALUE) {
            for (int i = startIndex + 1; i <= endIndex; i++) {
                int distance = primeNumbers[i] - primeNumbers[i - 1];

                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPairPrime1 = primeNumbers[i - 1];
                    closestPairPrime2 = primeNumbers[i];
                }
                if (distance > furthestDistance) {
                    furthestDistance = distance;
                    furthestPairPrime1 = primeNumbers[i - 1];
                    furthestPairPrime2 = primeNumbers[i];
                }
            }
        } else {
            int previousPrime = -1;

            for (int number = lowerLimit; number <= upperLimit && number > 0; number++) {
                if (isPrime(primeNumbers, number)) {
                    if (previousPrime != -1) {
                        int distance = number - previousPrime;

                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestPairPrime1 = previousPrime;
                            closestPairPrime2 = number;
                        }
                        if (distance > furthestDistance) {
                            furthestDistance = distance;
                            furthestPairPrime1 = previousPrime;
                            furthestPairPrime2 = number;
                        }
                    }
                    previousPrime = number;
                }
            }
        }
        return new Result(closestPairPrime1, closestPairPrime2, furthestPairPrime1, furthestPairPrime2);
    }

    private static boolean isPrime(Integer[] primeNumbers, int number) {
        if (number == 1) {
            return false;
        }
        int numberSqrt = (int) Math.sqrt(number);

        for (int primeNumber : primeNumbers) {
            if (primeNumber > numberSqrt) {
                break;
            }
            if (number % primeNumber == 0) {
                return false;
            }
        }
        return true;
    }

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[(int) MAX_SIEVE_VALUE];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < MAX_SIEVE_VALUE; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                primeNumbers.add((int) i);
            }
        }
        return primeNumbers.toArray(new Integer[0]);
    }

    private static int binarySearch(Integer[] values, int target, boolean isLowerBound) {
        int low = 0;
        int high = values.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (isLowerBound) {
                if (values[middle] < target) {
                    low = middle + 1;
                } else {
                    result = middle;
                    high = middle - 1;
                }
            } else {
                if (values[middle] > target) {
                    high = middle - 1;
                } else {
                    result = middle;
                    low = middle + 1;
                }
            }
        }
        return result;
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
