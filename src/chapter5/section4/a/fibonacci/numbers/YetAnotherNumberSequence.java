package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/11/25.
 */
public class YetAnotherNumberSequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] mods = { 10, 100, 1000, 10000 };
        int[] pisanoPeriod = { 60, 300, 1500, 15000 };

        for (int t = 0; t < tests; t++) {
            int a = FastReader.nextInt();
            int b = FastReader.nextInt();
            int n = FastReader.nextInt();
            int lastDigits = FastReader.nextInt();

            int targetLastDigits = getLastDigits(a, b, n, lastDigits, mods, pisanoPeriod);
            outputWriter.printLine(targetLastDigits);
        }
        outputWriter.flush();
    }

    private static int getLastDigits(int a, int b, int n, int lastDigits, int[] mods, int[] pisanoPeriod) {
        n %= pisanoPeriod[lastDigits - 1];

        if (n == 0) {
            return a % mods[lastDigits - 1];
        }
        if (n == 1) {
            return b % mods[lastDigits - 1];
        }
        int previous = a % mods[lastDigits - 1];
        int current = b % mods[lastDigits - 1];

        for (int i = 2; i <= n; i++) {
            int currentCopy = current;
            current = (previous + current) % mods[lastDigits - 1];
            previous = currentCopy;
        }
        return current;
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
