package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/01/25.
 */
public class SkewBinary {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("0")) {
            int decimal = skewBinaryToDecimal(line);
            outputWriter.printLine(decimal);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int skewBinaryToDecimal(String number) {
        int decimal = 0;
        int multiplier = 2;

        for (int i = number.length() - 1; i >= 0; i--) {
            int bit = Character.getNumericValue(number.charAt(i));
            decimal += bit * (multiplier - 1);
            multiplier *= 2;
        }
        return decimal;
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
