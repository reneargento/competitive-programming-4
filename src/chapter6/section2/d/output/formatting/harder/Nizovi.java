package chapter6.section2.d.output.formatting.harder;

import java.io.*;

/**
 * Created by Rene Argento on 20/06/2026.
 */
public class Nizovi {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String array = FastReader.getLine();
        formatArray(array, outputWriter, 0, 0, true, false);
        outputWriter.flush();
    }

    private static void formatArray(String array, OutputWriter outputWriter, int index, int level, boolean indent,
                                    boolean hasInsideContent) {
        if (index == array.length()) {
            outputWriter.printLine();
            return;
        }

        char symbol = array.charAt(index);
        if (symbol == '}') {
            if (hasInsideContent) {
                outputWriter.print("\n");
            }
            level--;
        }
        if (indent || symbol == '}') {
            for (int i = 0; i < level; i++) {
                outputWriter.print("  ");
            }
        }

        outputWriter.print(symbol);
        if (symbol == '{') {
            outputWriter.print("\n");
            formatArray(array, outputWriter, index + 1, level + 1, true, false);
        } else if (symbol == ',') {
            outputWriter.print("\n");
            formatArray(array, outputWriter, index + 1, level, true, true);
        } else {
            formatArray(array, outputWriter, index + 1, level, false, true);
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