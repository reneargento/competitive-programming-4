package chapter5.section3.i.modular.arithmetic;

import java.io.*;

/**
 * Created by Rene Argento on 09/10/25.
 */
public class SoftwareCRC {

    private static class Result {
        String byte1;
        String byte2;

        public Result(String byte1, String byte2) {
            this.byte1 = byte1;
            this.byte2 = byte2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("#")) {
            byte[] bytes = line.getBytes();
            Result crc = computeCRC(bytes);
            outputWriter.printLine(crc.byte1 + " " + crc.byte2);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeCRC(byte[] bytes) {
        int crc = 0;
        int mod = 34943;
        for (byte byteValue : bytes) {
            crc = (crc * 256) + byteValue;
            crc %= mod;
        }
        crc = ((mod - ((crc * 256) % mod * 256)) % mod + mod) % mod;

        int byte1 = crc / 256;
        int byte2 = crc % 256;

        String byte1Value = Integer.toHexString(byte1).toUpperCase();
        String byte2Value = Integer.toHexString(byte2).toUpperCase();

        if (byte1Value.length() == 1) {
            byte1Value = "0" + byte1Value;
        }
        if (byte2Value.length() == 1) {
            byte2Value = "0" + byte2Value;
        }
        return new Result(byte1Value, byte2Value);
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
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
