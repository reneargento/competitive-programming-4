package chapter5.section6;

import java.io.*;

/**
 * Created by Rene Argento on 21/03/26.
 */
public class DragonDropped {

    public static void main(String[] args) throws IOException {
        FastReaderInteger fastReaderInteger = new FastReaderInteger();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int locations = fastReaderInteger.nextInt();
        int sameLocation = 0;
        int stepsTaken = 0;

        if (locations < 30000) {
            for (int i = 0; i < locations; i++) {
                outputWriter.printLine("NEXT GABBY");
                outputWriter.flush();
            }
            outputWriter.printLine("ASK GABBY");
            outputWriter.flush();
            return;
        }

        while (sameLocation == 0) {
            outputWriter.printLine("NEXT GABBY");
            outputWriter.flush();
            readAndDiscardInput(fastReaderInteger);

            outputWriter.printLine("NEXT SPIKE");
            outputWriter.flush();
            readAndDiscardInput(fastReaderInteger);

            outputWriter.printLine("NEXT SPIKE");
            outputWriter.flush();
            fastReaderInteger.nextInt();
            sameLocation = fastReaderInteger.nextInt();

            stepsTaken++;
        }

        int lambda = 0;
        sameLocation = 0;
        while (sameLocation == 0) {
            outputWriter.printLine("NEXT SPIKE");
            outputWriter.flush();

            fastReaderInteger.nextInt();
            sameLocation = fastReaderInteger.nextInt();
            lambda++;
        }

        int stepsToEnd = (locations - stepsTaken) % lambda;
        for (int i = 0; i < stepsToEnd; i++) {
            outputWriter.printLine("NEXT SPIKE");
            outputWriter.flush();
        }
        outputWriter.printLine("ASK SPIKE");
        outputWriter.flush();
    }

    private static void readAndDiscardInput(FastReaderInteger fastReaderInteger) throws IOException {
        fastReaderInteger.nextInt();
        fastReaderInteger.nextInt();
    }

    private static class FastReaderInteger {
        private static final InputStream in = System.in;
        private static final int bufferSize = 30000;
        private static final byte[] buffer = new byte[bufferSize];
        private static int position = 0;
        private static int byteCount = bufferSize;
        private static byte character;

        FastReaderInteger() throws IOException {
            fill();
        }

        private void fill() throws IOException {
            byteCount = in.read(buffer, 0, bufferSize);
        }

        private int nextInt() throws IOException {
            while (character < '-') {
                character = readByte();
            }
            boolean isNegative = (character == '-');
            if (isNegative) {
                character = readByte();
            }
            int value = character - '0';
            while ((character = readByte()) >= '0' && character <= '9') {
                value = value * 10 + character - '0';
            }
            return isNegative ? -value : value;
        }

        private byte readByte() throws IOException {
            if (position == byteCount) {
                fill();
                position = 0;
            }
            return buffer[position++];
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
