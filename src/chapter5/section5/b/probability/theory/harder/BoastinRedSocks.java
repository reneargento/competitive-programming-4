package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/03/26.
 */
public class BoastinRedSocks {

    private static class Result {
        long redSocks;
        long blackSocks;

        public Result(long redSocks, long blackSocks) {
            this.redSocks = redSocks;
            this.blackSocks = blackSocks;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long p = FastReader.nextLong();
        long q = FastReader.nextLong();

        while (p != 0 || q != 0) {
            Result result = computeSocks(p, q);
            if (result == null) {
                outputWriter.printLine("impossible");
            } else {
                outputWriter.printLine(result.redSocks + " " + result.blackSocks);
            }

            p = FastReader.nextLong();
            q = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    private static Result computeSocks(long p, long q) {
        if (p == 0) {
            return new Result(0, 2);
        }
        if (p == q) {
            return new Result(2, 0);
        }

        for (int redSocks = 2; redSocks <= 50000; redSocks++) {
            long expression = 4L * redSocks * (redSocks - 1L);
            long gcd = gcd(expression, p);
            long expressionReduced = expression / gcd;
            long pReduced = p / gcd;

            if (q % pReduced != 0) {
                continue;
            }

            long aDivP = expressionReduced * (q / pReduced);
            long redSocksDoubleMinus1 = redSocks * 2L - 1;
            long d = redSocksDoubleMinus1 * redSocksDoubleMinus1 + aDivP - expression;
            if (d < 0) {
                continue;
            }

            long sqrt = (long) (Math.sqrt(d));
            if (sqrt * sqrt == d) {
                long blackSocksDouble = 1 - 2 * redSocks + sqrt;
                if (blackSocksDouble % 2 != 0) {
                    continue;
                }
                long blackSocks = blackSocksDouble / 2;
                if (redSocks + blackSocks > 50000) {
                    continue;
                }
                return new Result(redSocks, blackSocks);
            }
        }
        return null;
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
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
