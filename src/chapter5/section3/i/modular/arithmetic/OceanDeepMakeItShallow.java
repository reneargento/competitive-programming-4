package chapter5.section3.i.modular.arithmetic;

import java.io.*;

/**
 * Created by Rene Argento on 05/10/25.
 */
public class OceanDeepMakeItShallow {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int number = 0;
        while (line != null) {
            for (char character : line.toCharArray()) {
                if (character == '#') {
                    if (number == 0) {
                        outputWriter.printLine("YES");
                    } else {
                        outputWriter.printLine("NO");
                    }
                    number = 0;
                } else {
                    int digit = Character.getNumericValue(character);
                    number = (number * 2 + digit) % 131071;
                }
            }
            line = FastReader.getLine();
        }
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
