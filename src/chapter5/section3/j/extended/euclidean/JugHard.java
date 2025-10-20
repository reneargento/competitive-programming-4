package chapter5.section3.j.extended.euclidean;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/10/25.
 */
public class JugHard {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int volume1 = FastReader.nextInt();
            int volume2 = FastReader.nextInt();
            int target = FastReader.nextInt();

            String result = canPlaceWater(volume1, volume2, target);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String canPlaceWater(long volume1, long volume2, long target) {
        long gcd = gcd(volume1, volume2);
        if (target % gcd == 0) {
            return "Yes";
        } else {
            return "No";
        }
    }

    private static long gcd(long number1, long number2) {
        number1 = Math.abs(number1);
        number2 = Math.abs(number2);

        while (number2 > 0) {
            long temp = number2;
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
