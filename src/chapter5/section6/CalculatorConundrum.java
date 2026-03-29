package chapter5.section6;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/03/26.
 */
public class CalculatorConundrum {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int maxDigits = FastReader.nextInt();
            long number = FastReader.nextLong();

            long highestNumber = getHighestNumber(number, maxDigits);
            outputWriter.printLine(highestNumber);
        }
        outputWriter.flush();
    }

    private static long getHighestNumber(long number, int maxDigits) {
        long highestNumber = number;
        Set<Long> numbersSeen = new HashSet<>();

        while (!numbersSeen.contains(number)) {
            numbersSeen.add(number);
            number = getNextNumber(number, maxDigits);

            if (number > highestNumber) {
                highestNumber = number;
            }
        }
        return highestNumber;
    }

    private static long getNextNumber(long number, int maxDigits) {
        long nextNumber = number * number;
        String nextNumberString = String.valueOf(nextNumber);
        int endIndex = Math.min(nextNumberString.length(), maxDigits);
        String digits = nextNumberString.substring(0, endIndex);
        return Long.parseLong(digits);
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
