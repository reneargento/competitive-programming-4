package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/04/25.
 */
public class StirlingsApproximation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();

        while (number != 0) {
            double result = checkStirlingsApproximation(number);
            outputWriter.printLine(result);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double checkStirlingsApproximation(int number) {
        double factorialLog = 0;
        for (int i = 1; i <= number; i++) {
            factorialLog += Math.log(i);
        }

        double nDividedByELog = number * Math.log(number) - number * Math.log(Math.E);
        double sqrt2PiN = Math.sqrt(2 * Math.PI * number);

        double stirlingsApproximationLog = Math.log(sqrt2PiN) + nDividedByELog;
        return Math.exp(factorialLog - stirlingsApproximationLog);
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
