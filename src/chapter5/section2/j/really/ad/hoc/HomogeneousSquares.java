package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/05/25.
 */
public class HomogeneousSquares {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dimension = FastReader.nextInt();

        while (dimension != 0) {
            int[][] square = new int[dimension][dimension];
            for (int row = 0; row < square.length; row++) {
                for (int column = 0; column < square[0].length; column++) {
                    square[row][column] = FastReader.nextInt();
                }
            }
            outputWriter.printLine(isHomogeneous(square) ? "homogeneous" : "not homogeneous");
            dimension = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean isHomogeneous(int[][] square) {
        for (int row = 0; row < square.length - 1; row++) {
            for (int column = 0; column < square[0].length - 1; column++) {
                if (square[row][column] + square[row + 1][column + 1]
                        != square[row + 1][column] + square[row][column + 1]) {
                    return false;
                }
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

        public void flush() {
            writer.flush();
        }
    }
}
