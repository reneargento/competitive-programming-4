package chapter5.section3.c.finding.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/06/25.
 */
public class PrimeFactors {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int number = FastReader.nextInt();
        while (number != 0) {
            List<Integer> primeFactors = new ArrayList<>();
            int convertedNumber = number;

            if (number < 0) {
                primeFactors.add(-1);
                convertedNumber = number * -1;
            }

            primeFactors.addAll(primeFactors(convertedNumber));
            outputWriter.print(number + " = " + primeFactors.get(0));
            for (int i = 1; i < primeFactors.size(); i++) {
                int factor = primeFactors.get(i);
                outputWriter.print(" x " + factor);
            }
            outputWriter.printLine();
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> primeFactors(int number) {
        List<Integer> primeFactors = new ArrayList<>();

        for (long i = 2; i * i <= number; i++) {
            while (number % i == 0) {
                primeFactors.add((int) i);
                number /= i;
            }
        }

        // Special case where number is a prime number
        if (number > 1) {
            primeFactors.add(number);
        }
        return primeFactors;
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
