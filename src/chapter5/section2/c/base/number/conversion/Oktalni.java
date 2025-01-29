package chapter5.section2.c.base.number.conversion;

import java.io.*;

/**
 * Created by Rene Argento on 28/01/25.
 */
public class Oktalni {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String binaryNumber = FastReader.getLine();
        String octalNumber = binaryToOctal(binaryNumber);
        outputWriter.printLine(octalNumber);
        outputWriter.flush();
    }

    private static String binaryToOctal(String binaryNumber) {
        StringBuilder octalNumber = new StringBuilder();
        StringBuilder binaryNumberSb = new StringBuilder(binaryNumber);

        binaryNumberSb.reverse();
        while (binaryNumberSb.length() % 3 != 0) {
            binaryNumberSb.append("0");
        }
        binaryNumberSb.reverse();

        for (int i = 0; i < binaryNumberSb.length(); i += 3) {
            String bits = binaryNumberSb.substring(i, i + 3);
            if (bits.equals("000")) {
                octalNumber.append("0");
            } else if (bits.equals("001")) {
                octalNumber.append("1");
            } else if (bits.equals("010")) {
                octalNumber.append("2");
            } else if (bits.equals("011")) {
                octalNumber.append("3");
            } else if (bits.equals("100")) {
                octalNumber.append("4");
            } else if (bits.equals("101")) {
                octalNumber.append("5");
            } else if (bits.equals("110")) {
                octalNumber.append("6");
            } else if (bits.equals("111")) {
                octalNumber.append("7");
            }
        }
        return octalNumber.toString();
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
