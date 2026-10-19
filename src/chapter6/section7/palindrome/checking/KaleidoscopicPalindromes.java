package chapter6.section7.palindrome.checking;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/10/2026.
 */
public class KaleidoscopicPalindromes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int start = FastReader.nextInt();
        int end = FastReader.nextInt();
        int maxBase = FastReader.nextInt();

        int palindromicNumbersCount = countPalindromicNumbers(start, end, maxBase);
        outputWriter.printLine(palindromicNumbersCount);
        outputWriter.flush();
    }

    private static int countPalindromicNumbers(int start, int end, int maxBase) {
        int palindromicNumbersCount = 0;

        for (int number = start; number <= end; number++) {
            boolean areAllBasesPalindrome = true;

            for (int base = 2; base <= maxBase; base++) {
                String numberInBaseString = toRadix(number, base);
                if (!isPalindrome(numberInBaseString)) {
                    areAllBasesPalindrome = false;
                    break;
                }
            }

            if (areAllBasesPalindrome) {
                palindromicNumbersCount++;
            }
        }
        return palindromicNumbersCount;
    }

    private static String toRadix(int number, int base) {
        StringBuilder convertedNumber = new StringBuilder();
        while (number > 0) {
            convertedNumber.append(number % base);
            number /= base;
        }
        return convertedNumber.toString();
    }

    private static boolean isPalindrome(String string) {
        for (int i = 0; i < string.length() / 2; i++) {
            if (string.charAt(i) != string.charAt(string.length() - 1 - i)) {
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