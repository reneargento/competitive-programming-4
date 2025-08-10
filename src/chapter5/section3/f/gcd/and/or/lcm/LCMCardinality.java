package chapter5.section3.f.gcd.and.or.lcm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/08/25.
 */
public class LCMCardinality {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();

        while (number != 0) {
            int lCMCardinality = computeLCMCardinality(number);
            outputWriter.printLine(number + " " + lCMCardinality);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeLCMCardinality(int number) {
        int lCMCardinality = 0;
        List<Long> factors = getFactors(number);

        for (int i = 0; i < factors.size(); i++) {
            long factor1 = factors.get(i);

            for (int j = i; j < factors.size(); j++) {
                if (lcm(factor1, factors.get(j)) == number) {
                    lCMCardinality++;
                }
            }
        }
        return lCMCardinality;
    }

    private static List<Long> getFactors(long number) {
        List<Long> factors = new ArrayList<>();
        int upperLimit = (int) Math.sqrt(number);

        for (int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                factors.add((long) i);

                if (i != number / i) {
                    factors.add(number / i);
                }
            }
        }
        return factors;
    }

    private static long gcd(long number1, long number2) {
        number1 = Math.abs(number1);
        number2 = Math.abs(number2);

        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    public static long lcm(long number1, long number2) {
        return number1 * (number2 / gcd(number1, number2));
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
