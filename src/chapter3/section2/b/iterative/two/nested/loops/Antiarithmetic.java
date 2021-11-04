package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 25/10/21.
 */
// Based on http://acm.student.cs.uwaterloo.ca/~acm00/040925/data/Ddk.cc
public class Antiarithmetic {

    public static void main(String[] args) throws IOException {
        InputReader reader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] positions = new int[10001];

        int numberOfElements = reader.readInt();

        while (numberOfElements != 0) {
            for (int i = 0; i < numberOfElements; i++) {
                positions[reader.readInt()] = i;
            }

            if (isAntiarithmetic(positions, numberOfElements)) {
                outputWriter.printLine("yes");
            } else {
                outputWriter.printLine("no");
            }
            numberOfElements = reader.readInt();
        }
        outputWriter.flush();
    }

    private static boolean isAntiarithmetic(int[] positions, int numberOfElements) {
        for (int i = 1; i < numberOfElements; i++) {
            for (int j = 0; j + i + i < numberOfElements; j++) {
                if( (positions[j] < positions[j + i]) ^ (positions[j + i] > positions[j + i + i])) {
                    return false;
                }
            }
        }
        return true;
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

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c) && c != ':');
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        // Returns null on EOF
        public String readLine() {
            int c = read();
            if (c == -1) {
                return null;
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == -1;
        }

        public String next() {
            return readString();
        }
    }
}
