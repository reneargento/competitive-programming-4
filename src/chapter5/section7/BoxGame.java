package chapter5.section7;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/04/26.
 */
public class BoxGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int balls = FastReader.nextInt();
        while (balls != 0) {
           boolean isPowerOf2Minus1 = isPowerOf2Minus1(balls);

            if (isPowerOf2Minus1) {
                outputWriter.printLine("Bob");
            } else {
                outputWriter.printLine("Alice");
            }
            balls = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean isPowerOf2Minus1(int balls) {
        int log2 = (int) (Math.log(balls + 1) / Math.log(2));
        int power = (int) Math.pow(2, log2);
        return power == balls + 1;
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
