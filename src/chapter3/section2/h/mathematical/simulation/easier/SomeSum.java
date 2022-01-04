package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class SomeSum {

    private enum Result {
        ODD, EVEN, EITHER
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Result result = checkSum(FastReader.nextInt());
        if (result.equals(Result.ODD)) {
            outputWriter.printLine("Odd");
        } else if (result.equals(Result.EVEN)) {
            outputWriter.printLine("Even");
        } else {
            outputWriter.printLine("Either");
        }
        outputWriter.flush();
    }

    private static Result checkSum(int numbers) {
        int sumStartingInOdd = getSum(1, numbers);
        int sumStartingInEven = getSum(2, numbers);

        if (sumStartingInOdd % 2 == 1 && sumStartingInEven % 2 == 1) {
            return Result.ODD;
        } else if (sumStartingInOdd % 2 == 0 && sumStartingInEven % 2 == 0) {
            return Result.EVEN;
        } else {
            return Result.EITHER;
        }
    }

    private static int getSum(int startNumber, int numbers) {
        int sum = 0;

        for (int i = 0; i < numbers; i++) {
            sum += startNumber + i;
        }
        return sum;
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
