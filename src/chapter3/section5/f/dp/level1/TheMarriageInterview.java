package chapter3.section5.f.dp.level1;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/01/23.
 */
public class TheMarriageInterview {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int n = FastReader.nextInt();
        int back = FastReader.nextInt();
        int caseId = 1;

        while (n <= 60) {
            if (n < 0) {
                outputWriter.printLine(String.format("Case %d: %d", caseId, 1));
            } else {
                long[][] sumDp = new long[n + 1][back + 1];
                for (long[] values : sumDp) {
                    Arrays.fill(values, -1);
                }
                BigInteger[][] countDp = new BigInteger[n + 1][back + 1];
                for (BigInteger[] values : countDp) {
                    Arrays.fill(values, BigInteger.valueOf(-1));
                }

                trib(n, back, sumDp, countDp);
                BigInteger result = countDp[n][back];
                if (result.equals(BigInteger.valueOf(-1))) {
                    result = BigInteger.ONE;
                }
                outputWriter.printLine(String.format("Case %d: %d", caseId, result));
            }
            caseId++;
            n = FastReader.nextInt();
            back = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long trib(int n, int back, long[][] sumDp, BigInteger[][] countDp) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (sumDp[n][back] != -1) {
            return sumDp[n][back];
        }

        BigInteger tribCalls = BigInteger.ZERO;
        long sum = 0;
        for (int i = 1; i <= back; i++) {
            sum += trib(n - i, back, sumDp, countDp);
            if (n - i <= 1) {
                tribCalls = tribCalls.add(BigInteger.ONE);
            } else {
                tribCalls = tribCalls.add(countDp[n - i][back]);
            }
        }
        sumDp[n][back] = sum;
        countDp[n][back] = tribCalls.add(BigInteger.ONE);
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
