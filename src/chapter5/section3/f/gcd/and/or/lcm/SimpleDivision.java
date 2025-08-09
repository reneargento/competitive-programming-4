package chapter5.section3.f.gcd.and.or.lcm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/08/25.
 */
public class SimpleDivision {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();

        while (number != 0) {
            List<Integer> differences = new ArrayList<>();
            int nextNumber = FastReader.nextInt();

            while (nextNumber != 0) {
                differences.add(nextNumber - number);
                number = nextNumber;
                nextNumber = FastReader.nextInt();
            }

            long maxDivisor = getMaxDivisor(differences);
            outputWriter.printLine(maxDivisor);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long getMaxDivisor(List<Integer> differences) {
        if (differences.size() == 1) {
            return differences.get(0);
        }

        long gcd = gcd(differences.get(0), differences.get(1));
        for (int i = 2; i < differences.size(); i++) {
            gcd = gcd(gcd, differences.get(i));
        }
        return gcd;
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
