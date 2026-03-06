package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/02/26.
 */
// Based on https://blog.sengxian.com/solutions/uva-557
public class Burger {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        double[] differentBurgerProbabilities = computeDifferentBurgerProbabilities();

        for (int t = 0; t < tests; t++) {
            int guests = FastReader.nextInt();
            double sameBurgerProbability = 1 - differentBurgerProbabilities[guests];
            outputWriter.printLine(String.format("%.4f", sameBurgerProbability));
        }
        outputWriter.flush();
    }

    private static double[] computeDifferentBurgerProbabilities() {
        double[] differentBurgerProbabilities = new double[1000001];
        differentBurgerProbabilities[2] = 1;
        for (int guests = 4; guests < differentBurgerProbabilities.length; guests += 2) {
            differentBurgerProbabilities[guests] = differentBurgerProbabilities[guests - 2] * (guests - 3) / (guests - 2);
        }
        return differentBurgerProbabilities;
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
