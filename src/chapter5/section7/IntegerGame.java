package chapter5.section7;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/04/26.
 */
public class IntegerGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String digits = FastReader.getLine();
            boolean doesSWin = doesSWin(digits);
            outputWriter.print(String.format("Case %d: ", t));
            outputWriter.printLine(doesSWin ? "S" : "T");
        }
        outputWriter.flush();
    }

    private static boolean doesSWin(String digits) {
        int digitsSum = sumDigits(digits);
        int modThatCanBeRemoved = digitsSum % 3;

        for (int i = 0; i < digits.length(); i++) {
            int digit = Character.getNumericValue(digits.charAt(i));
            if (digit % 3 == modThatCanBeRemoved) {
                String nextDigits = removeDigit(digits, i);
                return !doesSWin(nextDigits);
            }
        }
        return false;
    }

    private static int sumDigits(String digits) {
        int sum = 0;
        for (int i = 0; i < digits.length(); i++) {
            sum += Character.getNumericValue(digits.charAt(i));
        }
        return sum;
    }

    private static String removeDigit(String digits, int index) {
        if (digits.length() == 1) {
            return "";
        }
        String digitsAfterRemoval = digits.substring(0, index);
        if (index < digits.length()) {
            digitsAfterRemoval += digits.substring(index + 1);
        }
        return digitsAfterRemoval;
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
