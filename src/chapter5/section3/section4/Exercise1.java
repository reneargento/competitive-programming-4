package chapter5.section3.section4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 09/05/25.
 */
public class Exercise1 {

    private static final Integer[] primes = eratosthenesSieve(10000000);

    private static int numDiffPf(int number) {
        int differentPrimeFactors = 0;

        for (int i = 0; i < primes.length && primes[i] * primes[i] <= number; i++) {
            if (number % primes[i] == 0) {
                differentPrimeFactors++;

                while (number % primes[i] == 0) {
                    number /= primes[i];
                }
            }
        }

        if (number > 1) {
            differentPrimeFactors++;
        }
        return differentPrimeFactors;
    }

    private static long sumPf(int number) {
        long primesSum = 0;

        for (int i = 0; i < primes.length && primes[i] * primes[i] <= number; i++) {
            while (number % primes[i] == 0) {
                primesSum += primes[i];
                number /= primes[i];
            }
        }

        if (number > 1) {
            primesSum += number;
        }
        return primesSum;
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

    public static void main(String[] args) {
        int differentPrimes = numDiffPf(60);
        System.out.println("Different prime factors: " + differentPrimes);
        System.out.println("Expected: 3");

        long primesSum = sumPf(60);
        System.out.println("\nPrime factors sum: " + primesSum);
        System.out.println("Expected: 12");
    }
}
