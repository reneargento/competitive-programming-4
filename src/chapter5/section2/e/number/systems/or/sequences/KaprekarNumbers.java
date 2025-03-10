package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/02/25.
 */
public class KaprekarNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        boolean[] kaprekarNumbers = computeKaprekarNumbers();

        for (int t = 1; t <= tests; t++) {
            int lowerBound = FastReader.nextInt();
            int upperBound = FastReader.nextInt();
            boolean kaprekarNumberFound = false;

            if (t > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine("case #" + t);

            for (int number = lowerBound; number <= upperBound; number++) {
                if (kaprekarNumbers[number]) {
                    outputWriter.printLine(number);
                    kaprekarNumberFound = true;
                }
            }

            if (!kaprekarNumberFound) {
                outputWriter.printLine("no kaprekar numbers");
            }
        }
        outputWriter.flush();
    }

    private static boolean[] computeKaprekarNumbers() {
        boolean[] kaprekarNumbers = new boolean[40001];

        for (int number = 2; number <= 40000; number++) {
            if (isKaprekarNumber(number)) {
                kaprekarNumbers[number] = true;
            }
        }
        return kaprekarNumbers;
    }

    private static boolean isKaprekarNumber(int number) {
        int square = number * number;
        String squareString = String.valueOf(square);

        for (int i = 0; i < squareString.length() - 1; i++) {
            String firstPartString = squareString.substring(0, i + 1);
            String secondPartString = squareString.substring(i + 1);

            int firstPart = Integer.parseInt(firstPartString);
            int secondPart = Integer.parseInt(secondPartString);

            if (firstPart != 0 &&
                    secondPart != 0 &&
                    firstPart + secondPart == number) {
                return true;
            }
        }
        return false;
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