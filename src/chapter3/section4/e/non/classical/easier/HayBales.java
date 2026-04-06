package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 26/07/22.
 */
public class HayBales {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        String hayBales = inputReader.nextLine();
        int minimumSteps = computeMinimumSteps(hayBales.toCharArray());
        outputWriter.printLine(minimumSteps);
        outputWriter.flush();
    }

    private static int computeMinimumSteps(char[] hayBales) {
        int steps = 0;
        while (!isSorted(hayBales)) {
            boolean didGoodMove = false;

            for (int i = 0; i < hayBales.length - 2; i++) {
                if (hayBales[i] == 'P' &&
                        ((hayBales[i + 1] == 'P' && hayBales[i + 2] == 'C')
                                || (hayBales[i + 1] == 'C' && hayBales[i + 2] == 'C')
                        )) {
                    hayBales[i] = 'C';
                    hayBales[i + 2] = 'P';
                    steps++;
                    didGoodMove = true;
                }
            }

            if (!didGoodMove) {
                for (int i = 0; i < hayBales.length - 1; i++) {
                    if (hayBales[i] == 'P' && hayBales[i + 1] == 'C') {
                        hayBales[i] = 'C';
                        hayBales[i + 1] = 'P';
                        steps++;
                        break;
                    }
                }
            }
        }
        return steps;
    }

    private static boolean isSorted(char[] hayBales) {
        for (int i = 0; i < hayBales.length - 1; i++) {
            if (hayBales[i] == 'P' && hayBales[i + 1] == 'C') {
                return false;
            }
        }
        return true;
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

        private String nextLine() throws IOException {
            int c = snext();
            while (isSpaceChar(c))
                c = snext();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
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
