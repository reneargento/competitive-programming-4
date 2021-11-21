package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/11/21.
 */
public class ThreeSquare {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] squares = computeSquares(FastReader.nextInt());
            if (squares == null) {
                outputWriter.printLine("-1");
            } else {
                outputWriter.printLine(String.format("%d %d %d", squares[0], squares[1], squares[2]));
            }
        }
        outputWriter.flush();
    }

    private static int[] computeSquares(int number) {
        // Optimization
        if (number % 8 == 7) {
            return null;
        }

        for (int i = 0; i < number; i++) {
            int firstNumber = i * i;

            if (firstNumber * 3 > number) {
                return null;
            }

            for (int j = i; j < number; j++) {
                int secondNumber = j * j;
                if (firstNumber  + (secondNumber * 2) > number) {
                    break;
                }

                int thirdNumber = number - firstNumber - secondNumber;
                double sqrtThirdNumber = Math.sqrt(thirdNumber);
                int intSqrtThirdNumber = (int) sqrtThirdNumber;

                if (sqrtThirdNumber == intSqrtThirdNumber) {
                    return new int[] { i ,j, intSqrtThirdNumber };
                }
            }
        }
        return null;
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
