package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/03/25.
 */
public class SquareNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int low = FastReader.nextInt();
        int high = FastReader.nextInt();
        boolean[] squareNumbers = generateSquareNumbers();

        while (low != 0 || high != 0) {
            int squareNumbersCount = countSquareNumbers(squareNumbers, low, high);
            outputWriter.printLine(squareNumbersCount);

            low = FastReader.nextInt();
            high = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean[] generateSquareNumbers() {
        boolean[] squareNumbers = new boolean[100001];
        int maxValue = (int) Math.floor(Math.sqrt(squareNumbers.length));

        for (int i = 1; i <= maxValue; i++) {
            int squareNumber = i * i;
            squareNumbers[squareNumber] = true;
        }
        return squareNumbers;
    }

    private static int countSquareNumbers(boolean[] squareNumbers, int low, int high) {
        int squareNumbersCount = 0;
        for (int i = low; i <= high; i++) {
            if (squareNumbers[i]) {
                squareNumbersCount++;
            }
        }
        return squareNumbersCount;
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
