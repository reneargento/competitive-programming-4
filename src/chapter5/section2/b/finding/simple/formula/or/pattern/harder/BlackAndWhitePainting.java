package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/12/24.
 */
public class BlackAndWhitePainting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int bottomRightCorner = FastReader.nextInt();

        while (rows != 0 || columns != 0 || bottomRightCorner != 0) {
            long embeddedBoards = countEmbeddedBoards(rows, columns, bottomRightCorner);
            outputWriter.printLine(embeddedBoards);

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
            bottomRightCorner = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long countEmbeddedBoards(long rows, int columns, int bottomRightCorner) {
        long boards = (rows - 8 + 1) * (columns - 8 + 1);
        long embeddedBoards = boards / 2;
        if (boards % 2 == 1 && bottomRightCorner == 1) {
            embeddedBoards++;
        }
        return embeddedBoards;
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
