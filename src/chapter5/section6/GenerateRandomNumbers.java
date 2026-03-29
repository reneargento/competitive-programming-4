package chapter5.section6;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/03/26.
 */
public class GenerateRandomNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int number = FastReader.nextInt();

        while (number != 0) {
            int numbersProduced = countNumbersProduced(number);
            outputWriter.printLine(numbersProduced);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int countNumbersProduced(int number) {
        int tortoise = getNextNumber(number);
        int hare = getNextNumber(getNextNumber(number));

        while (tortoise != hare) {
            tortoise = getNextNumber(tortoise);
            hare = getNextNumber(getNextNumber(hare));
        }

        int mu = 0;
        hare = number;
        while (tortoise != hare) {
            tortoise = getNextNumber(tortoise);
            hare = getNextNumber(hare);
            mu++;
        }

        int cycleLength = 1;
        hare = getNextNumber(hare);
        while (tortoise != hare) {
            hare = getNextNumber(hare);
            cycleLength++;
        }
        return mu + cycleLength;
    }

    private static int getNextNumber(int number) {
        return ((number * number) / 100) % 10000;
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

        public void flush() {
            writer.flush();
        }
    }
}
