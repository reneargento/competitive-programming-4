package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/02/25.
 */
public class CarmichaelNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();
        Set<Integer> carmichaelNumbers = computeCarmichaelNumbers();

        while (number != 0) {
            if (carmichaelNumbers.contains(number)) {
                outputWriter.printLine(String.format("The number %d is a Carmichael number.", number));
            } else {
                outputWriter.printLine(number + " is normal.");
            }
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Set<Integer> computeCarmichaelNumbers() {
        boolean[] isPrime = eratosthenesSieve();
        Set<Integer> carmichaelNumbers = new HashSet<>();

        for (int number = 2; number <= 65000; number++) {
            if (isCarmichaelNumber(isPrime, number)) {
                carmichaelNumbers.add(number);
            }
        }
        return carmichaelNumbers;
    }

    private static boolean isCarmichaelNumber(boolean[] isPrime, int number) {
        if (isPrime[number]) {
            return false;
        }
        for (int a = 2; a < number; a++) {
            long fermatTest = fastExponentiation(a, number, number);
            if (fermatTest != a) {
                return false;
            }
        }
        return true;
    }

    private static boolean[] eratosthenesSieve() {
        int upperBound = 65001;
        int maxNumberToCheck = (int) Math.floor(Math.sqrt(upperBound));
        boolean[] isPrime = new boolean[upperBound];

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

    private static long fastExponentiation(long base, int exponent, int mod) {
        if (exponent == 0) {
            return 1;
        }

        long baseSquared = base * base;
        long result = fastExponentiation(baseSquared % mod, exponent / 2, mod);
        if (exponent % 2 == 0) {
            return result;
        } else {
            return (base * result) % mod;
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