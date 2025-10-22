package chapter5.section3.k.divisibility.test;

import java.io.*;

/**
 * Created by Rene Argento on 19/10/25.
 */
public class TwoThe9s {

    private static class Result {
        boolean isMultiple;
        int depth;

        public Result(boolean isMultiple, int depth) {
            this.isMultiple = isMultiple;
            this.depth = depth;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String number = FastReader.getLine();
        while (!number.equals("0")) {
            Result result = isNumberMultiple(number);
            outputWriter.print(number + " is ");
            if (result.isMultiple) {
                outputWriter.printLine("a multiple of 9 and has 9-degree " + result.depth + ".");
            } else {
                outputWriter.printLine("not a multiple of 9.");
            }
            number = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result isNumberMultiple(String number) {
        boolean isMultiple;
        int depth = 0;

        while (true) {
            int sum = 0;
            depth++;
            for (int i = 0; i < number.length(); i++) {
                sum += Character.getNumericValue(number.charAt(i));
            }

            String nextNumber = String.valueOf(sum);
            if (nextNumber.length() == 1) {
                isMultiple = sum % 9 == 0;
                break;
            }
            number = nextNumber;
        }
        return new Result(isMultiple, depth);
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
