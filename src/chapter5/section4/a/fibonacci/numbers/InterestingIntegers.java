package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/12/25.
 */
// Based on https://github.com/BrandonTang89/Competitive_Programming_4_Solutions/blob/main/Chapter_5_Mathematics/Combinatorics/kattis_interestingintegers.cpp
public class InterestingIntegers {

    private static class Result {
        long firstTerm;
        long secondTerm;

        public Result(long firstTerm, long secondTerm) {
            this.firstTerm = firstTerm;
            this.secondTerm = secondTerm;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            Result result = computeFirstTerms(number);
            outputWriter.printLine(result.firstTerm + " " + result.secondTerm);
        }
        outputWriter.flush();
    }

    private static Result computeFirstTerms(int number) {
        long[] fibonacci = new long[45];
        long[] bezoutCoefficient1 = new long[45];
        long[] bezoutCoefficient2 = new long[45];
        computeValues(fibonacci, bezoutCoefficient1, bezoutCoefficient2);

        long firstTerm = Long.MAX_VALUE;
        long secondTerm = Long.MAX_VALUE;

        for (int i = 2; i < fibonacci.length; i++) {
            double multiplier = number *
                    (bezoutCoefficient2[i] - bezoutCoefficient1[i]) / (double) (fibonacci[i - 1] + fibonacci[i]);
            long multiplierFloor = (long) Math.floor(multiplier);

            long firstTermCandidate = number * bezoutCoefficient1[i] + multiplierFloor * fibonacci[i];
            long secondTermCandidate = number * bezoutCoefficient2[i] - multiplierFloor * fibonacci[i - 1];

            while (firstTermCandidate > secondTermCandidate) {
                firstTermCandidate -= fibonacci[i];
                secondTermCandidate += fibonacci[i - 1];
            }
            if (firstTermCandidate <= 0) {
                continue;
            }

            if (secondTermCandidate < secondTerm) {
                secondTerm = secondTermCandidate;
                firstTerm = firstTermCandidate;
            } else if (secondTermCandidate == secondTerm) {
                firstTerm = Math.min(firstTerm, firstTermCandidate);
            }
        }
        return new Result(firstTerm, secondTerm);
    }

    private static void computeValues(long[] fibonacci, long[] bezoutCoefficient1, long[] bezoutCoefficient2) {
        fibonacci[0] = 0;
        fibonacci[1] = 1;

        for (int i = 2; i < fibonacci.length; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
            extendedEuclid(fibonacci[i - 1], fibonacci[i], bezoutCoefficient1, bezoutCoefficient2, i);
        }
    }

    private static void extendedEuclid(long number1, long number2, long[] bezoutCoefficient1, long[] bezoutCoefficient2,
                                       int index) {
        if (number2 == 0) {
            bezoutCoefficient1[index] = 1;
            bezoutCoefficient2[index] = 0;
            return;
        }

        extendedEuclid(number2, number1 % number2, bezoutCoefficient1, bezoutCoefficient2, index);

        long nextBezoutCoefficient1 = bezoutCoefficient2[index];
        long nextBezoutCoefficient2 = bezoutCoefficient1[index] - (number1 / number2) * bezoutCoefficient2[index];

        bezoutCoefficient1[index] = nextBezoutCoefficient1;
        bezoutCoefficient2[index] = nextBezoutCoefficient2;
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
