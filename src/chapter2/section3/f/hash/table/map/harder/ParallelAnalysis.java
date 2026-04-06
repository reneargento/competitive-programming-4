package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/06/21.
 */
public class ParallelAnalysis {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int traces = inputReader.nextInt();

        for (int t = 1; t <= traces; t++) {
            int traceLength = inputReader.nextInt();
            int totalTime = 0;
            Map<Integer, Integer> memoryWrittenToTimeMap = new HashMap<>();

            for (int i = 0; i < traceLength; i++) {
                int memoryReferences = inputReader.nextInt();
                int highestCurrentTime = 0;

                for (int m = 0; m < memoryReferences - 1; m++) {
                    int memoryRead = inputReader.nextInt();
                    int highestTimeToWriteMemory = memoryWrittenToTimeMap.getOrDefault(memoryRead, 0);
                    highestCurrentTime = Math.max(highestCurrentTime, highestTimeToWriteMemory);
                }
                int memoryWritten = inputReader.nextInt();
                highestCurrentTime++;
                memoryWrittenToTimeMap.put(memoryWritten, highestCurrentTime);

                totalTime = Math.max(totalTime, highestCurrentTime);
            }
            outputWriter.printLine(t + " " + totalTime);
        }
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
