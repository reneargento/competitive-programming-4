package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/02/26.
 */
public class BachelorArithmetic {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long bachelors = FastReader.nextInt();
        long spinsters = FastReader.nextInt();
        int caseId = 1;

        while (bachelors != 0 || spinsters != 0) {
            String probability = computeProbability(bachelors, spinsters);
            outputWriter.printLine(String.format("Case %d: %s", caseId, probability));

            caseId++;
            bachelors = FastReader.nextInt();
            spinsters = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String computeProbability(long bachelors, long spinsters) {
        if (bachelors == 1) {
            return ":-\\";
        }
        if (bachelors > spinsters) {
            return ":-(";
        }
        return ":-|";
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
