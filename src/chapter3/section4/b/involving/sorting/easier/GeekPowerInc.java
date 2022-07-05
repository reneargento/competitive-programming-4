package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/07/22.
 */
public class GeekPowerInc {

    private static class Group implements Comparable<Group> {
        int quantity;
        long rating;

        public Group(int quantity, long rating) {
            this.quantity = quantity;
            this.rating = rating;
        }

        @Override
        public int compareTo(Group other) {
            return Long.compare(rating, other.rating);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            Group[] groups = new Group[FastReader.nextInt()];
            for (int i = 0; i < groups.length; i++) {
                groups[i] = new Group(FastReader.nextInt(), FastReader.nextInt());
            }
            long totalMaxPower = computeTotalMaxPower(groups);
            outputWriter.printLine(String.format("Case %d: %d", t, totalMaxPower));
        }
        outputWriter.flush();
    }

    private static long computeTotalMaxPower(Group[] groups) {
        long maxPower = 0;
        Arrays.sort(groups);

        long[] quantitiesAhead = new long[groups.length];
        quantitiesAhead[quantitiesAhead.length - 1] = groups[groups.length - 1].quantity;
        for (int i = groups.length - 2; i >= 0; i--) {
            quantitiesAhead[i] = quantitiesAhead[i + 1] + groups[i].quantity;
        }

        for (int i = 0; i < groups.length; i++) {
            long power = groups[i].rating * quantitiesAhead[i];
            maxPower = Math.max(maxPower, power);
        }
        return maxPower;
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
