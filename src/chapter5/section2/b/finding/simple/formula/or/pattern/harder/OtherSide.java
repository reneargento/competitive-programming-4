package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/01/25.
 */
public class OtherSide {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int wolves = FastReader.nextInt();
        int sheep = FastReader.nextInt();
        int cabbages = FastReader.nextInt();
        int itemsOnBoat = FastReader.nextInt();

        boolean canTransport = canTransport(wolves, sheep, cabbages, itemsOnBoat);
        outputWriter.printLine(canTransport ? "YES" : "NO");
        outputWriter.flush();
    }

    private static boolean canTransport(int wolves, int sheep, int cabbages, int itemsOnBoat) {
        if (itemsOnBoat == 0
                || (sheep > itemsOnBoat && wolves > itemsOnBoat)
                || (sheep > itemsOnBoat && cabbages > itemsOnBoat)
                || (sheep > 2 * itemsOnBoat && (wolves + cabbages == itemsOnBoat))
                || (sheep == itemsOnBoat && (wolves + cabbages > 2 * itemsOnBoat))) {
            return false;
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

        public void flush() {
            writer.flush();
        }
    }
}
