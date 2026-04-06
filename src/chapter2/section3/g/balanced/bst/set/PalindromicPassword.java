package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by Rene Argento on 07/06/21.
 */
public class PalindromicPassword {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int numbers = inputReader.nextInt();
        TreeSet<Integer> palindromes = generatePalindromes();

        for (int n = 0; n < numbers; n++) {
            int number = inputReader.nextInt();
            Integer floor = palindromes.floor(number);
            Integer ceiling = palindromes.ceiling(number);

            if (floor != null && ceiling != null) {
                int floorDifference = Math.abs(floor - number);
                int ceilingDifference = Math.abs(ceiling - number);
                if (floorDifference <= ceilingDifference) {
                    outputWriter.printLine(floor);
                } else {
                    outputWriter.printLine(ceiling);
                }
            } else if (floor != null) {
                outputWriter.printLine(floor);
            } else {
                outputWriter.printLine(ceiling);
            }
        }
        outputWriter.flush();
    }

    private static TreeSet<Integer> generatePalindromes() {
        TreeSet<Integer> palindromes = new TreeSet<>();
        for (int number = 100; number <= 999; number++) {
            StringBuilder palindrome = new StringBuilder(String.valueOf(number));

            for (int i = 2; i >= 0; i--) {
                palindrome.append(palindrome.charAt(i));
            }
            palindromes.add(Integer.parseInt(palindrome.toString()));
        }
        return palindromes;
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        private InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        private int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
