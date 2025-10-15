package chapter5.section3.j.extended.euclidean;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/10/25.
 */
public class PlayWithFloorAndCeil {

    private static class Result {
        long p;
        long q;

        public Result(long p, long q) {
            this.p = p;
            this.q = q;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            long x = FastReader.nextLong();
            long k = FastReader.nextLong();

            Result result = computePQ(x, k);
            outputWriter.printLine(result.p + " " + result.q);
        }
        outputWriter.flush();
    }

    private static Result computePQ(long x, long k) {
        long a = (long) Math.floor(x / (double) k);
        long b = (long) Math.ceil(x / (double) k);

        long[] result = diophantineEquation(a, b, x);
        return new Result(result[1], result[2]);
    }

    private static long[] diophantineEquation(long a, long b, long c) {
        long[] solution = { 1, -1, -1 };

        extendedEuclid(a, b);

        // The equation only has integer solutions if gcd(a, b) divides c
        if (c % gcd != 0) {
            return solution;
        }

        // First solutions are given by
        // x0 = bezoutCoefficient1 * (c / gcd)
        // y0 = bezoutCoefficient2 * (c / gcd)

        long x = bezoutCoefficient1 * (c / gcd);
        long y = bezoutCoefficient2 * (c / gcd);

        // If both values are negative, return
        if (x < 0 && y < 0) {
            solution[0] = 2;
            return solution;
        }

        // Make sure there are no negative values
        if (x < 0 || y < 0) {
            boolean isXNegative = x < 0;
            int factorToGetAPositiveSolution;
            double equalOrHigherThan;
            double equalOrLowerThan;

            if (isXNegative) {
                equalOrHigherThan = Math.abs(x) / (double) b;
                equalOrLowerThan = Math.abs(y) / (double) a;
            } else {
                equalOrHigherThan = Math.abs(y) / (double) a;
                equalOrLowerThan = Math.abs(x) / (double) b;
            }

            if (equalOrHigherThan == (int) equalOrHigherThan) {
                factorToGetAPositiveSolution = (int) equalOrHigherThan;
            } else if (equalOrLowerThan == (int) equalOrLowerThan) {
                factorToGetAPositiveSolution = (int) equalOrLowerThan;
            } else {
                factorToGetAPositiveSolution = ((int) equalOrHigherThan) + 1;
            }

            if (isXNegative) {
                x += (b / gcd) * factorToGetAPositiveSolution;
                y -= (a / gcd) * factorToGetAPositiveSolution;
            } else {
                x -= (b / gcd) * factorToGetAPositiveSolution;
                y += (a / gcd) * factorToGetAPositiveSolution;
            }
        }

        solution[0] = 0;
        solution[1] = x;
        solution[2] = y;
        return solution;
    }

    private static long bezoutCoefficient1;
    private static long bezoutCoefficient2;
    private static long gcd;

    private static void extendedEuclid(long number1, long number2) {
        if (number2 == 0) {
            bezoutCoefficient1 = 1;
            bezoutCoefficient2 = 0;
            gcd = number1;
            return;
        }

        extendedEuclid(number2, number1 % number2);

        long nextBezoutCoefficient1 = bezoutCoefficient2;
        long nextBezoutCoefficient2 = bezoutCoefficient1 - (number1 / number2) * bezoutCoefficient2;

        bezoutCoefficient1 = nextBezoutCoefficient1;
        bezoutCoefficient2 = nextBezoutCoefficient2;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
