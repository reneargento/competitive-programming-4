package chapter5.section6;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/03/26.
 */
public class LastDigit {

    private static class Result {
        int startIndex;
        String digitsBeforeCycle;
        String cycle;

        public Result(int startIndex, String digitsBeforeCycle, String cycle) {
            this.startIndex = startIndex;
            this.digitsBeforeCycle = digitsBeforeCycle;
            this.cycle = cycle;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Result cycle = computeCycle();
        BigInteger n = new BigInteger(FastReader.next());

        while (n.compareTo(BigInteger.ZERO) != 0) {
            char lastDigit = computeLastDigit(n, cycle);

            outputWriter.printLine(lastDigit);
            n = new BigInteger(FastReader.next());
        }
        outputWriter.flush();
    }

    private static char computeLastDigit(BigInteger n, Result cycle) {
        BigInteger cycleStartIndex = BigInteger.valueOf(cycle.startIndex);
        if (n.compareTo(cycleStartIndex) < 0) {
            return cycle.digitsBeforeCycle.charAt(n.intValue() - 1);
        }
        n = n.subtract(cycleStartIndex).subtract(BigInteger.ONE);
        BigInteger cycleLength = BigInteger.valueOf(cycle.cycle.length());
        int index = n.mod(cycleLength).intValue();
        return cycle.cycle.charAt(index);
    }

    private static Result computeCycle() {
        BigInteger sum = BigInteger.ZERO;
        StringBuilder digits = new StringBuilder();
        BigInteger bigInteger10 = BigInteger.valueOf(10);

        for (int i = 1; i <= 500; i++) {
            BigInteger iBI = BigInteger.valueOf(i);
            sum = sum.add(iBI.pow(i));
            sum = sum.mod(bigInteger10);
            digits.append(sum.intValue());
        }

        String digitsString = digits.toString();
        for (int size = 1; size < digitsString.length(); size++) {
            for (int startIndex = 0; startIndex < digitsString.length() - size + 1; startIndex++) {
                String value = digitsString.substring(startIndex, startIndex + size);
                if (isCycle(digitsString, startIndex, value)) {
                    String digitsBeforeCycle = digitsString.substring(0, startIndex);
                    return new Result(startIndex, digitsBeforeCycle, value);
                }
            }
        }
        return new Result(0, "", digitsString);
    }

    private static boolean isCycle(String digits, int startIndex, String value) {
        int repeats = 0;
        for (int i = startIndex; i < digits.length() - value.length(); i += value.length()) {
            String valueCandidate = digits.substring(i, i + value.length());
            if (value.equals(valueCandidate)) {
                repeats++;
            } else {
                return false;
            }
        }
        return repeats > 2;
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
