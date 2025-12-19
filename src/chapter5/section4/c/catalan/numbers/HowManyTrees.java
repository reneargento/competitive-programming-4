package chapter5.section4.c.catalan.numbers;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 15/12/25.
 */
public class HowManyTrees {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        BigInteger[] catalanNumbers = catalanNumbers();

        String line = FastReader.getLine();
        while (line != null) {
            int treeSize = Integer.parseInt(line);
            outputWriter.printLine(catalanNumbers[treeSize]);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigInteger[] catalanNumbers() {
        BigInteger[] catalanNumbers = new BigInteger[1001];
        catalanNumbers[0] = BigInteger.ONE;

        for (int i = 0; i < catalanNumbers.length - 1; i++) {
            catalanNumbers[i + 1] = catalanNumbers[i]
                    .multiply(BigInteger.valueOf(4 * i + 2))
                    .divide(BigInteger.valueOf(i + 2));
        }
        return catalanNumbers;
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
