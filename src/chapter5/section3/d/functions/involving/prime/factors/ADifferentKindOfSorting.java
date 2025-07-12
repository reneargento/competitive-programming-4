package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/07/25.
 */
public class ADifferentKindOfSorting {

    private static class Number implements Comparable<Number> {
        int value;
        int primeFactors;

        public Number(int value) {
            this.value = value;
            primeFactors = primeFactors(value);
        }

        @Override
        public int compareTo(Number other) {
            if (primeFactors != other.primeFactors) {
                return Integer.compare(primeFactors, other.primeFactors);
            }
            return Integer.compare(value, other.value);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Number[] numbers = sortNumbers();
        int index = FastReader.nextInt();
        int caseId = 1;

        while (index != 0) {
            int value = numbers[index].value;
            outputWriter.printLine(String.format("Case %d: %d", caseId, value));
            caseId++;
            index = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Number[] sortNumbers() {
        Number[] numbers = new Number[2000001];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = new Number(i);
        }
        Arrays.sort(numbers);
        return numbers;
    }

    private static int primeFactors(int number) {
        int primeFactors = 0;

        for (int i = 2; i * i <= number; i++) {
            while (number % i == 0) {
                primeFactors++;
                number /= i;
            }
        }

        if (number > 1) {
            primeFactors++;
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
