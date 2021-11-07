package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/10/21.
 */
public class HappyTelephones {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int phoneCalls = FastReader.nextInt();
        int intervals = FastReader.nextInt();

        while (phoneCalls != 0 && intervals != 0) {
            int[] start = new int[phoneCalls];
            int[] end = new int[phoneCalls];

            for (int i = 0; i < phoneCalls; i++) {
                FastReader.nextInt();
                FastReader.nextInt();
                start[i] = FastReader.nextInt();
                end[i] = start[i] + FastReader.nextInt();
            }

            for (int i = 0; i < intervals; i++) {
                int intervalStart = FastReader.nextInt();
                int intervalEnd = intervalStart + FastReader.nextInt();

                int activeCalls = 0;
                for (int call = 0; call < phoneCalls; call++) {
                    if (intersects(intervalStart, intervalEnd, start[call], end[call])) {
                        activeCalls++;
                    }
                }
                outputWriter.printLine(activeCalls);
            }

            phoneCalls = FastReader.nextInt();
            intervals = FastReader.nextInt();
        }

        outputWriter.flush();
    }

    private static boolean intersects(int start1, int end1, int start2, int end2) {
        return (start1 >= start2 && end1 <= end2) ||
                (end1 > start2 && end1 <= end2) ||
                (start1 >= start2 && start1 < end2) ||
                (start1 <= start2 && end1 >= end2);
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
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