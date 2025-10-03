package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/10/25.
 */
public class PerfectPthPowers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();

        while (number != 0) {
            int largestPower = computeLargestPower(number);
            outputWriter.printLine(largestPower);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeLargestPower(long number) {
        boolean isNegative = number < 0;
        number = Math.abs(number);
        int largestPower = -1;

        for (long i = 2; i * i <= number; i++) {
            int power = 0;

            while (number % i == 0) {
                power++;
                number /= i;
            }

            if (power > 0) {
                if (largestPower == -1) {
                    largestPower = power;
                } else {
                    largestPower = gcd(largestPower, power);
                }
            }
        }

        if (number > 1) {
            return 1;
        }
        if (isNegative) {
            while (largestPower % 2 == 0) {
                largestPower /= 2;
            }
        }
        return largestPower;
    }

    private static int gcd(int number1, int number2) {
        number1 = Math.abs(number1);
        number2 = Math.abs(number2);

        while (number2 > 0) {
            int temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
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
