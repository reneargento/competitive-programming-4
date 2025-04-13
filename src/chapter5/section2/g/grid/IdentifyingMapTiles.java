package chapter5.section2.g.grid;

import java.io.*;

/**
 * Created by Rene Argento on 10/04/25.
 */
public class IdentifyingMapTiles {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String quadkey = FastReader.getLine();
        int level = quadkey.length();
        int dimension = (int) Math.pow(2, level);
        int column = 0;
        int row = 0;

        for (int i = 0; i < quadkey.length(); i++) {
            char value = quadkey.charAt(i);
            int halfDimension = dimension / 2;

            if (value == '1') {
                column += halfDimension;
            } else if (value == '2') {
                row += halfDimension;
            } else if (value == '3') {
                row += halfDimension;
                column += halfDimension;
            }
            dimension /= 2;
        }

        outputWriter.printLine(level + " " + column + " " + row);
        outputWriter.flush();
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
