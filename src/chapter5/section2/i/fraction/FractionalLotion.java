package chapter5.section2.i.fraction;

import java.io.*;

/**
 * Created by Rene Argento on 28/04/25.
 */
public class FractionalLotion {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split("/");
            int n = Integer.parseInt(data[1]);

            int lotionPairs = countLotionPairs(n);
            outputWriter.printLine(lotionPairs);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countLotionPairs(int n) {
        int count = 0;

        for (int y = n + 1; y <= 2 * n; y++) {
            int nominator = n * y;
            int denominator = y - n;

            if (denominator > 0 && nominator % denominator == 0) {
                count++;
            }
        }
        return count;
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