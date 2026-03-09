package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/03/26.
 */
public class Probability {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            double a = FastReader.nextDouble();
            double b = FastReader.nextDouble();
            double targetArea = FastReader.nextDouble();

            double probability = computeProbability(a, b, targetArea);
            outputWriter.printLine(String.format("%.6f%%", probability));
        }
        outputWriter.flush();
    }

    private static double computeProbability(double a, double b, double targetArea) {
        if (a * b <= targetArea) {
            return 0;
        }
        if (targetArea == 0) {
            return 100;
        }

        double targetAreaByB = targetArea / b;
        double area = targetArea + targetArea * (Math.log(a) - Math.log(targetAreaByB));
        return 100 - (area * 100) / (a * b);
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
