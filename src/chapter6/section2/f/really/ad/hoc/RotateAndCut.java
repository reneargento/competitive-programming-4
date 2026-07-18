package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/07/2026.
 */
public class RotateAndCut {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int cuts = FastReader.nextInt();
            String sentence = FastReader.next();

            String result = rotateAndCut(sentence, cuts);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String rotateAndCut(String sentence, int cuts) {
        int start = 0;
        int end = sentence.length() - 1;

        for (int i = 0; i < cuts; i++) {
            int length = end - start + 1;
            int sizeToRemove = length / 4;

            if (sizeToRemove == 0) {
                break;
            }
            if (i % 2 == 0) {
                start += sizeToRemove;
            } else {
                end -= sizeToRemove;
            }
        }
        return sentence.substring(start, end + 1);
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