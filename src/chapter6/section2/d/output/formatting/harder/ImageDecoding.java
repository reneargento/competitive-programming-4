package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/05/26.
 */
public class ImageDecoding {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int lines = FastReader.nextInt();

        int imageId = 1;
        while (lines != 0) {
            int lineLength = -1;
            boolean hasError = false;
            if (imageId > 1) {
                outputWriter.printLine();
            }

            for (int i = 0; i < lines; i++) {
                String[] data = FastReader.getLine().split(" ");
                int totalLength = 0;
                char currentToken = data[0].charAt(0);

                for (int t = 1; t < data.length; t++) {
                    int repeat = Integer.parseInt(data[t]);
                    totalLength += repeat;
                    for (int r = 0; r < repeat; r++) {
                        outputWriter.print(currentToken);
                    }
                    currentToken = switchToken(currentToken);
                }
                outputWriter.printLine();
                if (lineLength == -1) {
                    lineLength = totalLength;
                } else if (lineLength != totalLength) {
                    hasError = true;
                }
            }
            if (hasError) {
                outputWriter.printLine("Error decoding image");
            }
            imageId++;
            lines = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static char switchToken(char token) {
        if (token == '#') {
            return '.';
        } else {
            return '#';
        }
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

        public void flush() {
            writer.flush();
        }
    }
}