package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 20/12/21.
 */
public class VideoSpeedup {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int events = inputReader.nextInt();
        int speedUp = inputReader.nextInt();
        int videoLength = inputReader.nextInt();

        int lastTimestamp = 0;
        double currentSpeed = 1;
        double originalVideoTime = 0;

        for (int e = 1; e <= events; e++) {
            int timestamp = inputReader.nextInt();
            originalVideoTime += (timestamp - lastTimestamp) * currentSpeed;
            currentSpeed = (100 + (speedUp * e)) / 100.0;
            lastTimestamp = timestamp;
        }
        originalVideoTime += (videoLength - lastTimestamp) * currentSpeed;

        outputWriter.printLine(originalVideoTime);
        outputWriter.flush();
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
