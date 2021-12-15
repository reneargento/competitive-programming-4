package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/12/21.
 */
public class TheOwlAndTheFox {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            int previousNumber = getPreviousNumber(number);
            outputWriter.printLine(previousNumber);
        }
        outputWriter.flush();
    }

    private static int getPreviousNumber(int number) {
        int sum = sumDigits(number);

        for (int previousNumber = number - 1; previousNumber >= 0; previousNumber--) {
            int previousNumberSum = sumDigits(previousNumber);
            if (previousNumberSum == sum - 1) {
                return previousNumber;
            }
        }
        return 0;
    }

    private static int sumDigits(int number) {
        int sum = 0;

        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
