package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;

/**
 * Created by Rene Argento on 23/03/25.
 */
public class BestCompressionEverUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            long files = Long.parseLong(data[0]);
            int maxBits = Integer.parseInt(data[1]);

            outputWriter.printLine(canCompress(files, maxBits));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String canCompress(long files, int maxBits) {
        long totalBitsPossible = (long) Math.pow(2, maxBits + 1) - 1;

        if (totalBitsPossible >= files) {
            return "yes";
        } else {
            return "no";
        }
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
