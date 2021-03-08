package chapter2.section2.h.bit.manipulation;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/03/21.
 */
public class Bits {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            int maxNumberOf1Bits = getMaxNumberOf1Bits(number);
            outputWriter.printLine(maxNumberOf1Bits);
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static int getMaxNumberOf1Bits(int number) {
        int maxNumberOf1Bits = 0;

        while (number > 0) {
            int numberOf1Bits = count1Bits(number);
            maxNumberOf1Bits = Math.max(maxNumberOf1Bits, numberOf1Bits);
            number /= 10;
        }
        return maxNumberOf1Bits;
    }

    private static int count1Bits(int number) {
        int numberOf1Bits = 0;

        while (number > 0) {
            numberOf1Bits++;
            number = number & (number - 1);
        }
        return numberOf1Bits;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
