package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/12/21.
 */
public class Perfection {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();
        outputWriter.printLine("PERFECTION OUTPUT");

        while (number != 0) {
            String classification = classifyNumber(number);
            outputWriter.printLine(String.format("%5d  %s", number, classification));
            number = FastReader.nextInt();
        }
        outputWriter.printLine("END OF OUTPUT");
        outputWriter.flush();
    }

    private static String classifyNumber(int number) {
        if (number == 1) {
            return "DEFICIENT";
        }

        List<Integer> divisors = computeDivisors(number);
        int sum = 0;

        for (int divisor : divisors) {
            sum += divisor;
        }

        if (sum == number) {
            return "PERFECT";
        } else if (sum > number) {
            return "ABUNDANT";
        } else {
            return "DEFICIENT";
        }
    }

    private static List<Integer> computeDivisors(int number) {
        List<Integer> divisors = new ArrayList<>();
        divisors.add(1);
        int sqrtNumber = (int) Math.sqrt(number);

        for (int i = 2; i <= sqrtNumber; i++) {
            if (number % i == 0) {
                divisors.add(i);

                if (number / i != i) {
                    divisors.add(number / i);
                }
            }
        }
        return divisors;
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
