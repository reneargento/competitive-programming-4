package chapter5.section2.c.base.number.conversion;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/01/25.
 */
public class ArithmeticUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int expressions = FastReader.nextInt();

        for (int t = 0; t < expressions; t++) {
            String number1 = FastReader.next();
            FastReader.next(); // ignore operator
            String number2 = FastReader.next();
            FastReader.next(); // ignore operator
            String resultNumber = FastReader.next();

            int smallestCorrectBase = computeSmallestCorrectBase(number1, number2, resultNumber);
            outputWriter.printLine(smallestCorrectBase);
        }
        outputWriter.flush();
    }

    private static int computeSmallestCorrectBase(String number1, String number2, String result) {
        if (isCorrectInBase1(number1, number2, result)) {
            return 1;
        }
        for (int base = 2; base <= 36; base++) {
            try {
                int number1BaseB = Integer.parseInt(number1, base);
                int number2BaseB = Integer.parseInt(number2, base);
                int resultBaseB = Integer.parseInt(result, base);
                if (number1BaseB + number2BaseB == resultBaseB) {
                    return base;
                }
            } catch (NumberFormatException exception) {
                // Ignore
            }
        }
        return 0;
    }

    private static boolean isCorrectInBase1(String number1, String number2, String result) {
        return containsOnly1s(number1) && containsOnly1s(number2) && containsOnly1s(result)
                && number1.length() + number2.length() == result.length();
    }

    private static boolean containsOnly1s(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) != '1') {
                return false;
            }
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

        public void flush() {
            writer.flush();
        }
    }
}