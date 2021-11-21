package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/11/21.
 */
public class GoblinGardenGuards {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int goblins = FastReader.nextInt();
        int[][] garden = new int[10001][10001];

        for (int i = 0; i < goblins; i++) {
            int x = FastReader.nextInt();
            int y = FastReader.nextInt();
            garden[y][x]++;
        }

        int sprinklers = FastReader.nextInt();
        for (int i = 0; i < sprinklers; i++) {
            int x = FastReader.nextInt();
            int y = FastReader.nextInt();
            int radius = FastReader.nextInt();

            int goblinsWet = turnSprinklerOn(garden, x, y, radius);
            goblins -= goblinsWet;
        }
        outputWriter.printLine(goblins);
        outputWriter.flush();
    }

    private static int turnSprinklerOn(int[][] garden, int x, int y, int radius) {
        int startRow = Math.max(0, y - radius);
        int endRow = Math.min(garden.length - 1, y + radius);
        int startColumn = Math.max(0, x - radius);
        int endColumn = Math.min(garden[y].length - 1, x + radius);

        int goblinsWet = 0;

        for (int row = startRow; row <= endRow; row++) {
            for (int column = startColumn; column <= endColumn; column++) {
                if (garden[row][column] > 0) {
                    if (isInsideRadius(x, y, column, row, radius)) {
                        goblinsWet += garden[row][column];
                        garden[row][column] = 0;
                    }
                }
            }
        }
        return goblinsWet;
    }

    private static boolean isInsideRadius(int x1, int y1, int x2, int y2, int radius) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) <= (radius * radius);
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
