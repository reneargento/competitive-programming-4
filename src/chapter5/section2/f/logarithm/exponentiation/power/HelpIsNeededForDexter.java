package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;

/**
 * Created by Rene Argento on 18/03/25.
 */
public class HelpIsNeededForDexter {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int numbers = Integer.parseInt(line);
            outputWriter.printLine(log2Floor(numbers) + 1);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int log2Floor(int number) {
        return (int) Math.floor(Math.log(number) / Math.log(2));
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
