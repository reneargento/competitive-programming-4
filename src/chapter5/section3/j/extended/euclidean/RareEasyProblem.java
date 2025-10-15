package chapter5.section3.j.extended.euclidean;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/10/25.
 */
public class RareEasyProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long number = FastReader.nextLong();
        while (number != 0) {
            List<Long> nValues = computeNValues(number);

            outputWriter.print(nValues.get(0));
            for (int i = 1; i < nValues.size(); i++) {
                outputWriter.print(" " + nValues.get(i));
            }
            outputWriter.printLine();
            number = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    private static List<Long> computeNValues(long number) {
        List<Long> nValues = new ArrayList<>();

        for (int i = 9; i >= 0; i--) {
            if ((number - i) % 9 == 0) {
                long nValue = (number - i) / 9L * 10L + i;
                nValues.add(nValue);
            }
        }
        return nValues;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
