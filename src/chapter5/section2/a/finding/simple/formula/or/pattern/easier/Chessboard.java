package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/09/24.
 */
public class Chessboard {

    private static final double DIAGONAL_LENGTH = Math.sqrt(2);

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int chessboardSize = FastReader.nextInt();
            double tourLength = computeTourLength(chessboardSize);

            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("%.3f", tourLength));
        }
        outputWriter.flush();
    }

    private static double computeTourLength(int chessboardSize) {
        if (chessboardSize == 1) {
            return 0;
        }
        int straightLines = 4 * (chessboardSize - 1);
        int diagonalLines = (chessboardSize * chessboardSize) - straightLines;
        return straightLines + (diagonalLines * DIAGONAL_LENGTH);
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
