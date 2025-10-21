package chapter5.section3.j.extended.euclidean;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 18/10/25.
 */
public class WipeYourWhiteboards {

    private static class Solution {
        long x;
        long y;

        public Solution(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = inputReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int r = inputReader.nextInt();
            int s = inputReader.nextInt();
            int q = inputReader.nextInt();

            Solution solution = diophantineEquationSmallest(r, s, q);
            outputWriter.printLine(solution.x + " " + solution.y);
        }
        outputWriter.flush();
    }

    private static Solution diophantineEquationSmallest(long a, long b, long c) {
        extendedEuclid(a, b);

        long x = bezoutCoefficient1 * (c / gcd);
        long y = bezoutCoefficient2 * (c / gcd);

        long deltaX = b / gcd;
        long deltaY = a / gcd;

        long moveXForwardL = deltaX < 0 ? deltaX * -1 : deltaX;
        long moveXForwardR = deltaX < 0 ? deltaY * -1 : deltaY;
        long moveYForwardL = deltaY < 0 ? deltaX * -1 : deltaX;
        long moveYForwardR = deltaY < 0 ? deltaY * -1 : deltaY;

        if (x < 1) {
            if (x == 0) {
                x += moveXForwardL;
                y -= moveXForwardR;
            } else {
                long multiplier = Math.abs(x) / moveXForwardL + 1;
                x += moveXForwardL * multiplier;
                y -= moveXForwardR * multiplier;
            }
        }
        if (x > 1) {
            long multiplier = (x - 1) / moveXForwardL;
            x -= moveXForwardL * multiplier;
            y += moveXForwardR * multiplier;
        }

        if (y < 1) {
            if (y == 0) {
                x -= moveYForwardL;
                y += moveYForwardR;
            } else {
                long multiplier = Math.abs(y) / moveYForwardR + 1;
                x -= moveYForwardL * multiplier;
                y += moveYForwardR * multiplier;
            }
        }
        return new Solution(x, y);
    }

    private static long bezoutCoefficient1;
    private static long bezoutCoefficient2;
    private static long gcd;

    private static void extendedEuclid(long number1, long number2) {
        if (number2 == 0) {
            bezoutCoefficient1 = 1;
            bezoutCoefficient2 = 0;
            gcd = number1;
            return;
        }

        extendedEuclid(number2, number1 % number2);

        long nextBezoutCoefficient1 = bezoutCoefficient2;
        long nextBezoutCoefficient2 = bezoutCoefficient1 - (number1 / number2) * bezoutCoefficient2;

        bezoutCoefficient1 = nextBezoutCoefficient1;
        bezoutCoefficient2 = nextBezoutCoefficient2;
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
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

        public int nextInt() throws IOException {
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

        public boolean isSpaceChar(int c) {
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

        public void flush() {
            writer.flush();
        }
    }
}
