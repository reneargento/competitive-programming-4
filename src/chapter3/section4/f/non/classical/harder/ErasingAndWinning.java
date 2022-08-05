package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/07/22.
 */
public class ErasingAndWinning {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int digits = FastReader.nextInt();
        int digitsToDelete = FastReader.nextInt();

        while (digits != 0 || digitsToDelete != 0) {
            String number = FastReader.next();
            Deque<Character> highestPrize = computeHighestPrize(number, digitsToDelete);
            while (!highestPrize.isEmpty()) {
                outputWriter.print(highestPrize.removeFirst());
            }
            outputWriter.printLine();

            digits = FastReader.nextInt();
            digitsToDelete = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Deque<Character> computeHighestPrize(String number, int digitsToDelete) {
        Deque<Character> highestPrize = new ArrayDeque<>();

        for (int i = 0; i < number.length(); i++) {
            char currentDigit = number.charAt(i);
            while (digitsToDelete > 0
                    && !highestPrize.isEmpty()
                    && currentDigit > highestPrize.peekLast()) {
                highestPrize.removeLast();
                digitsToDelete--;
            }
            highestPrize.addLast(currentDigit);
        }

        while (digitsToDelete > 0) {
            highestPrize.removeLast();
            digitsToDelete--;
        }
        return highestPrize;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
