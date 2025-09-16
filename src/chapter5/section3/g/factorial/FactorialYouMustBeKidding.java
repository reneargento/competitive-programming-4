package chapter5.section3.g.factorial;

import java.io.*;

/**
 * Created by Rene Argento on 11/09/25.
 */
public class FactorialYouMustBeKidding {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int value = Integer.parseInt(line);
            String result = computeFactorial(value);
            outputWriter.printLine(result);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computeFactorial(int n) {
        if (n > 13
                || (n < 0 && Math.abs(n) % 2 == 1)) {
            return "Overflow!";
        }
        if (n < 8) {
            return "Underflow!";
        }

        long factorial = 1;
        for (int i = 2; i <= n; i++) {
            factorial *= i;
        }
        return String.valueOf(factorial);
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
