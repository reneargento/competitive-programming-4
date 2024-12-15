package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/12/24.
 */
public class VeryBigPerfectSquares {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String number = FastReader.next();

        while (!number.equals("0")) {
            String perfectSquares = countPerfectSquares(number);
            outputWriter.printLine(perfectSquares);
            number = FastReader.next();
        }
        outputWriter.flush();
    }

    private static String countPerfectSquares(String number) {
        int numberFirstDigits = Character.getNumericValue(number.charAt(0));
        int length = number.length();

        if (length > 1 && length % 2 == 0) {
            numberFirstDigits *= 10;
            numberFirstDigits += Character.getNumericValue(number.charAt(1));
        }

        int zeroesToAppend = (length - 1) / 2;
        int sqrt = (int) Math.sqrt(numberFirstDigits);

        StringBuilder result = new StringBuilder();
        result.append(sqrt);
        for (int i = 0; i < zeroesToAppend; i++) {
            result.append("0");
        }
        return result.toString();
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
