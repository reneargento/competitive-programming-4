package chapter5.section4.d.others.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/12/25.
 */
public class DeliveryDebacle {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        long[] waysDp = computeWaysToPack();

        for (int t = 0; t < tests; t++) {
            int length = FastReader.nextInt();
            long waysToPack = waysDp[length];
            outputWriter.printLine(waysToPack);
        }
        outputWriter.flush();
    }

    private static long[] computeWaysToPack() {
        long[] waysDp = new long[41];
        waysDp[0] = 1;
        waysDp[1] = 1;
        waysDp[2] = 5;

        for (int i = 3; i < waysDp.length; i++) {
            waysDp[i] = waysDp[i - 1] + waysDp[i - 2] * 4 + waysDp[i - 3] * 2;
        }
        return waysDp;
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
