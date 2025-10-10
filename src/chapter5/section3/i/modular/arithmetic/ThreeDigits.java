package chapter5.section3.i.modular.arithmetic;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/10/25.
 */
public class ThreeDigits {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int n = FastReader.nextInt();
        String lastThreeDigits = getLastThreeDigits(n);
        outputWriter.printLine(lastThreeDigits);
        outputWriter.flush();
    }

    private static String getLastThreeDigits(int n) {
        long mod = 1000;
        long factors2 = 0;
        long factors5 = 0;
        long factorial = 1;

        for (long i = 2; i <= n; i++) {
            long numberCopy = i;
            while (numberCopy % 2 == 0) {
                numberCopy /= 2;
                factors2++;
            }
            while (numberCopy % 5 == 0) {
                numberCopy /= 5;
                factors5++;
            }

            factorial *= numberCopy;
            factorial %= mod;
        }

        long factors2ToKeep = factors2 - factors5;
        for (int i = 0; i < factors2ToKeep; i++) {
            factorial = (factorial * 2) % mod;
        }
        String factorialString = String.valueOf(factorial);
        if (factorialString.equals("48")) {
            factorialString = "048";
        }
        return factorialString;
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
