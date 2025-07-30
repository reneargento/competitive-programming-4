package chapter5.section3.e.modified.sieve;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/07/25.
 */
public class Divisors {

    private static class Result {
        int divisorsCount;
        long divisorsSum;

        public Result(int divisorsCount, long divisorsSum) {
            this.divisorsCount = divisorsCount;
            this.divisorsSum = divisorsSum;
        }
    }

    private static final int MAX_N = 100001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        int[] divNumbersCount = new int[MAX_N];
        long[] divNumbersSum = new long[MAX_N];
        sumDivArrays(divNumbersCount, divNumbersSum);

        for (int t = 0; t < tests; t++) {
            int start = FastReader.nextInt();
            int end = FastReader.nextInt();
            int multiple = FastReader.nextInt();

            Result result = computeDivisorSums(divNumbersCount, divNumbersSum, start, end, multiple);
            outputWriter.printLine(result.divisorsCount + " " + result.divisorsSum);
        }
        outputWriter.flush();
    }

    private static Result computeDivisorSums(int[] divNumbersCount, long[] divNumbersSum, int start, int end,
                                             int multiple) {
        int divisorsCount = 0;
        long divisorsSum = 0;
        for (int i = start; i <= end; i++) {
            if (i % multiple == 0) {
                divisorsCount += divNumbersCount[i];
                divisorsSum += divNumbersSum[i];
            }
        }
        return new Result(divisorsCount, divisorsSum);
    }

    private static void sumDivArrays(int[] divNumbersCount, long[] divNumbersSum) {
        Arrays.fill(divNumbersCount, 1);
        Arrays.fill(divNumbersSum, 1);
        int[] currentNumber = new int[MAX_N];
        for (int i = 0; i < currentNumber.length; i++) {
            currentNumber[i] = i;
        }

        for (int i = 2; i < MAX_N; i++) {
            if (divNumbersSum[i] == 1) {
                for (int j = i; j < MAX_N; j += i) {
                    int power = 0;
                    long multiplier = i;
                    long total = 1;
                    while (currentNumber[j] % i == 0) {
                        currentNumber[j] /= i;
                        total += multiplier;
                        multiplier *= i;
                        power++;
                    }
                    divNumbersSum[j] *= total;
                    divNumbersCount[j] *= power + 1;
                }
            }
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
