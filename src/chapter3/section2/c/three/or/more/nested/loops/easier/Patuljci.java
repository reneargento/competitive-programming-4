package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/11/21.
 */
public class Patuljci {

    private static class FalseDwarves {
        int index1;
        int index2;

        public FalseDwarves(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] hatNumbers = new int[9];

        for (int i = 0; i < hatNumbers.length; i++) {
            hatNumbers[i] = FastReader.nextInt();
        }

        FalseDwarves falseDwarves = exposeFalseDwarves(hatNumbers);
        for (int i = 0; i < hatNumbers.length; i++) {
            if (i != falseDwarves.index1 && i != falseDwarves.index2) {
                outputWriter.printLine(hatNumbers[i]);
            }
        }
        outputWriter.flush();
    }

    private static FalseDwarves exposeFalseDwarves(int[] hatNumbers) {
        for (int i = 0; i < hatNumbers.length; i++) {
            for (int j = i + 1; j < hatNumbers.length; j++) {
                int sum = 0;

                for (int k = 0; k < hatNumbers.length; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    sum += hatNumbers[k];
                }

                if (sum == 100) {
                    return new FalseDwarves(i, j);
                }
            }
        }
        return null;
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
