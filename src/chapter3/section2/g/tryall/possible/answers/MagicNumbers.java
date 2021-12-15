package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/12/21.
 */
public class MagicNumbers {

    private static class Equation implements Comparable<Equation> {
        long numerator;
        long denominator;

        public Equation(long numerator, long denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        @Override
        public int compareTo(Equation other) {
            return Long.compare(numerator, other.numerator);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            long targetNumber = FastReader.nextLong();
            List<Equation> equations = getAllEquations(targetNumber);
            for (Equation equation : equations) {
                outputWriter.printLine(String.format("%d / %d = %d", equation.numerator,
                        equation.denominator, targetNumber));
            }
        }
        outputWriter.flush();
    }

    private static List<Equation> getAllEquations(long targetNumber) {
        List<Equation> equations = new ArrayList<>();
        for (int number = 1; number <= 9; number++) {
            int mask = 1 << number;
            getAllEquations(targetNumber, equations, number, mask, 1);
        }
        Collections.sort(equations);
        return equations;
    }

    private static void getAllEquations(long targetNumber, List<Equation> equations, long currentNumber,
                                        int mask, int index) {
        if (index == 11) {
            return;
        }

        long result = currentNumber / targetNumber;
        if (currentNumber % targetNumber == 0
                && hasAllUniqueNumbers(result)) {
            equations.add(new Equation(currentNumber, result));
        }

        long currentNumberTimes10 = currentNumber * 10;
        for (int i = 0; i <= 9; i++) {
            if ((mask & (1 << i)) == 0) {
                long nextNumber = currentNumberTimes10 + i;
                int newMask = mask | (1 << i);
                getAllEquations(targetNumber, equations, nextNumber, newMask, index + 1);
            }
        }
    }

    private static boolean hasAllUniqueNumbers(long number) {
        boolean[] digits = new boolean[10];

        while (number > 0) {
            int digit = (int) number % 10;
            if (digits[digit]) {
                return false;
            }
            digits[digit] = true;
            number /= 10;
        }
        return true;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
