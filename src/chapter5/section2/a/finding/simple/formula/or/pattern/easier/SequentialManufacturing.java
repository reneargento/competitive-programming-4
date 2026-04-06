package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 16/12/24.
 */
public class SequentialManufacturing {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int machines = inputReader.nextInt();
        int itemsToProduce = inputReader.nextInt();
        int[] processingTimes = new int[machines];

        for (int i = 0; i < processingTimes.length; i++) {
            processingTimes[i] = inputReader.nextInt();
        }
        long manufactureTime = computeManufactureTime(itemsToProduce, processingTimes);
        outputWriter.printLine(manufactureTime);
        outputWriter.flush();
    }

    private static long computeManufactureTime(int itemsToProduce, int[] processingTimes) {
        long totalTime = 0;
        long maxProcessingTime = 0;

        for (int time : processingTimes) {
            totalTime += time;
            maxProcessingTime = Math.max(maxProcessingTime, time);
        }
        totalTime += maxProcessingTime * (itemsToProduce - 1);
        return totalTime;
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
