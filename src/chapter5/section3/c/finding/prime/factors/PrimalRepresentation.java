package chapter5.section3.c.finding.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 22/06/25.
 */
public class PrimalRepresentation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Integer[] primeNumbers = eratosthenesSieve();

        String line = FastReader.getLine();
        while (line != null) {
            int number = Integer.parseInt(line);
            printPrimeFactors(number, primeNumbers, outputWriter);
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printPrimeFactors(int number, Integer[] primeNumbers, OutputWriter outputWriter) {
        boolean isFirstFactor = true;
        if (number < 0) {
            printPrimeFactor(-1, 1, outputWriter);
            number = Math.abs(number);
            isFirstFactor = false;
        }

        for (int primeNumber : primeNumbers) {
            boolean isFactor = false;
            int exponent = 0;

            while (number % primeNumber == 0) {
                number /= primeNumber;
                exponent++;
                isFactor = true;
            }

            if (isFactor) {
                if (isFirstFactor) {
                    isFirstFactor = false;
                } else {
                    outputWriter.print(" ");
                }
                printPrimeFactor(primeNumber, exponent, outputWriter);
            }
        }

        if (number > 1) {
            if (!isFirstFactor) {
                outputWriter.print(" ");
            }
            printPrimeFactor(number, 1, outputWriter);
        }
    }

    private static void printPrimeFactor(int value, int exponent, OutputWriter outputWriter) {
        outputWriter.print(value);
        if (exponent > 1) {
            outputWriter.print("^" + exponent);
        }
    }

    private static Integer[] eratosthenesSieve() {
        int maxValue = (int) Math.sqrt(Integer.MAX_VALUE);
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[maxValue + 1];
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
