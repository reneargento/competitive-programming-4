package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/12/21.
 */
public class CookingWater {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int times = FastReader.nextInt();
        int[] secondsAway = new int[1001];

        for (int t = 0; t < times; t++) {
            int start = FastReader.nextInt();
            int end = FastReader.nextInt();

            for (int i = start; i <= end; i++) {
                secondsAway[i]++;
            }
        }
        boolean isEdwardRight = isEdwardRight(times, secondsAway);
        outputWriter.printLine(isEdwardRight ? "edward is right" : "gunilla has a point");
        outputWriter.flush();
    }

    private static boolean isEdwardRight(int times, int[] secondsAway) {
        for (int timesAwayInSecond : secondsAway) {
            if (timesAwayInSecond == times) {
                return false;
            }
        }
        return true;
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
