package chapter5.section3.g.factorial;

import java.io.*;

/**
 * Created by Rene Argento on 13/09/25.
 */
public class HowManyDigits {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] digitsCount = countDigits();

        String line = FastReader.getLine();
        while (line != null) {
            int number = Integer.parseInt(line);
            outputWriter.printLine(digitsCount[number]);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[] countDigits() {
        int[] digitsCount = new int[1000001];
        digitsCount[0] = 1;

        double digits = 1;
        for (int i = 1; i < digitsCount.length; i++) {
            digits += Math.log10(i);
            digitsCount[i] = (int) digits;
        }
        return digitsCount;
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
