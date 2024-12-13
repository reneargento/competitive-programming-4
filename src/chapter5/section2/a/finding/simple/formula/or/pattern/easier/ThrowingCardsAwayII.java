package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/12/24.
 */
public class ThrowingCardsAwayII {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long cardsNumber = FastReader.nextLong();

        while (cardsNumber != 0) {
            long lastRemainingCard = josephusSkip2(cardsNumber);
            outputWriter.printLine(lastRemainingCard);
            cardsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    // Special case of josephus with skip 2 can be solved in O(1) by:
    // 1- Converting the circle size to binary
    // 2- Moving the most significant bit to the end
    // 3- Converting the bits to decimal. That is the survivor position.
    private static int josephusSkip2(long circleSize) {
        if (circleSize == 1) {
            return 1;
        }
        String bits = toBinary(circleSize);
        bits = bits.substring(1) + bits.charAt(0);
        return Integer.parseInt(bits, 2) - 1;
    }

    private static String toBinary(long number) {
        StringBuilder bits = new StringBuilder();
        while (number > 0) {
            long bit = number % 2;
            number /= 2;
            bits.append(bit);
        }
        return bits.reverse().toString();
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
