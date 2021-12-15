package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/12/21.
 */
public class HeirsDilemma {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int lowerRange = FastReader.nextInt();
        int higherRange = FastReader.nextInt();

        int possibleCombinations = countPossibleCombinations(lowerRange, higherRange);
        outputWriter.printLine(possibleCombinations);
        outputWriter.flush();
    }

    private static int countPossibleCombinations(int lowerRange, int higherRange) {
        int possibleCombinations = 0;

        for (int number = lowerRange; number <= higherRange; number++) {
            if (isValidNumber(number)) {
                possibleCombinations++;
            }
        }
        return possibleCombinations;
    }

    private static boolean isValidNumber(int number) {
        boolean[] digits = new boolean[10];
        int numberCopy = number;

        while (numberCopy > 0) {
            int digit = numberCopy % 10;
            if (digits[digit]) {
                return false;
            }
            if (digit == 0) {
                return false;
            }
            if (number % digit != 0) {
                return false;
            }

            digits[digit] = true;
            numberCopy /= 10;
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
