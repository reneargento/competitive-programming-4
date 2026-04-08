package chapter5.section7;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 05/04/26.
 */
public class CuttingBrownies {

    private static final int HARRY_ID = 0;
    private static final int VICKY_ID = 1;

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = inputReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int width = inputReader.nextInt();
            int height = inputReader.nextInt();
            String name = inputReader.next();
            int playerId = name.equals("Harry") ? HARRY_ID : VICKY_ID;

            outputWriter.print(name + " ");
            outputWriter.print(canWin(playerId, width, height) ? "can" : "cannot");
            outputWriter.printLine(" win");
        }
        outputWriter.flush();
    }

    private static boolean canWin(int playerId, int width, int height) {
        if (width == 1 && height == 1) {
            return false;
        }
        if (width == 1) {
            return playerId == HARRY_ID;
        }
        if (height == 1) {
            return playerId == VICKY_ID;
        }

        int nextPlayerId = playerId ^ 1;
        boolean canWin;
        if (playerId == HARRY_ID) {
            int height1 = height / 2;
            int height2 = height - height1;
            canWin = !canWin(nextPlayerId, width, height1) &&
                    !canWin(nextPlayerId, width, height2);
        } else {
            int width1 = width  / 2;
            int width2 = width - width1;
            canWin = !canWin(nextPlayerId, width1, height) &&
                    !canWin(nextPlayerId, width2, height);
        }
        return canWin;
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

        public String next() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
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
