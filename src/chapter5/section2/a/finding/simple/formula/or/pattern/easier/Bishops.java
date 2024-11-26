package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;

/**
 * Created by Rene Argento on 19/11/24.
 */
public class Bishops {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int chessboardSize = Integer.parseInt(line);
            int maxBishops = computeMaxBishops(chessboardSize);
            outputWriter.printLine(maxBishops);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMaxBishops(int chessboardSize) {
        if (chessboardSize == 1) {
            return 1;
        }
        return (chessboardSize - 1) * 2;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
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
