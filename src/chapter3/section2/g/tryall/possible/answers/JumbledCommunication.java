package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/12/21.
 */
public class JumbledCommunication {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] bytes = new int[FastReader.nextInt()];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = FastReader.nextInt();
        }

        int[] message = unscramble(bytes);
        for (int i = 0; i < message.length; i++) {
            outputWriter.print(message[i]);

            if (i != message.length - 1) {
                outputWriter.print(" ");
            }
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static int[] unscramble(int[] bytes) {
        int[] message = new int[bytes.length];
        int[] uncoveredBytes = new int[256];

        for (int i = 0; i < bytes.length; i++) {
            int number = bytes[i];
            if (uncoveredBytes[number] != 0) {
                message[i] = uncoveredBytes[number];
            } else {
                for (int j = 0; j <= 255; j++) {
                    if ((j ^ (j << 1) & 255) == number) {
                        message[i] = j;
                        uncoveredBytes[number] = j;
                    }
                }
            }
        }
        return message;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
