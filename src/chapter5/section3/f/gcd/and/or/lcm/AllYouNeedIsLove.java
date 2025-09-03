package chapter5.section3.f.gcd.and.or.lcm;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/08/25.
 */
public class AllYouNeedIsLove {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String bits1  = FastReader.next();
            String bits2  = FastReader.next();

            String result = checkLoveMachine(bits1, bits2);
            outputWriter.print(String.format("Pair #%d: ", t));
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String checkLoveMachine(String bits1, String bits2) {
        int value1 = Integer.parseInt(bits1, 2);
        int value2 = Integer.parseInt(bits2, 2);

        if (gcd(value1, value2) == 1) {
            return "Love is not all you need!";
        } else {
            return "All you need is love!";
        }
    }

    private static int gcd(int number1, int number2) {
        while (number2 > 0) {
            int temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
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
