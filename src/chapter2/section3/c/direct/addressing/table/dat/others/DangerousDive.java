package chapter2.section3.c.direct.addressing.table.dat.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/05/21.
 */
public class DangerousDive {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] values = line.split(" ");
            int volunteersNumber = Integer.parseInt(values[0]);
            int volunteersReturned = Integer.parseInt(values[1]);

            boolean[] returned = new boolean[volunteersNumber + 1];
            for (int i = 0; i < volunteersReturned; i++) {
                int id = FastReader.nextInt();
                returned[id] = true;
            }

            if (volunteersNumber == volunteersReturned) {
                outputWriter.printLine("*");
            } else {
                for (int i = 1; i < returned.length; i++) {
                    if (!returned[i]) {
                        outputWriter.print(i + " ");
                    }
                }
                outputWriter.printLine();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
