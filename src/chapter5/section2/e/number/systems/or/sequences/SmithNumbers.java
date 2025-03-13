package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/02/25.
 */
public class SmithNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            int nextSmithNumber = getNextSmithNumber(number);
            outputWriter.printLine(nextSmithNumber);
        }
        outputWriter.flush();
    }

    private static int getNextSmithNumber(int number) {
        for (int numberCandidate = number + 1; ; numberCandidate++) {
            List<Integer> primeFactors = primeFactors(numberCandidate);
            if (primeFactors.size() == 1) {
                continue;
            }

            int numberDigitsSum = getDigitsSum(numberCandidate);
            int primeFactorsDigitSum = 0;

            for (int primeFactor : primeFactors) {
                primeFactorsDigitSum += getDigitsSum(primeFactor);
            }

            if (numberDigitsSum == primeFactorsDigitSum) {
                return numberCandidate;
            }
        }
    }

    private static List<Integer> primeFactors(int number) {
        List<Integer> primeFactors = new ArrayList<>();

        for (int i = 2; i * i <= number; i++) {
            while (number % i == 0) {
                primeFactors.add(i);
                number /= i;
            }
        }

        // Special case where number is a prime number
        if (number > 1) {
            primeFactors.add(number);
        }
        return primeFactors;
    }

    private static int getDigitsSum(int number) {
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
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