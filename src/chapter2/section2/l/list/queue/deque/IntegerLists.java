package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/04/21.
 */
public class IntegerLists {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String commands = FastReader.next();
            int listSize = FastReader.nextInt();
            String listString = FastReader.next();
            String[] list = listString.substring(1, listString.length() - 1).split(",");

            runBAPC(commands, list, outputWriter);
        }
        outputWriter.flush();
    }

    private static void runBAPC(String commands, String[] list, OutputWriter outputWriter) {
        int startIndex = 0;
        int endIndex = list.length - 1;
        boolean isReversed = false;
        boolean errorOccurred = false;

        for (char command : commands.toCharArray()) {
            if (command == 'R') {
                isReversed = !isReversed;
            } else {
                if (startIndex > endIndex || list[startIndex].isEmpty()) {
                    errorOccurred = true;
                    break;
                }

                if (isReversed) {
                    endIndex--;
                } else {
                    startIndex++;
                }
            }
        }

        if (errorOccurred) {
            outputWriter.printLine("error");
        } else {
            outputWriter.print("[");
            int indexToPrintStart;
            int indexToPrintEnd;
            int increment;

            if (isReversed) {
                indexToPrintStart = endIndex;
                indexToPrintEnd = startIndex - 1;
                increment = -1;
            } else {
                indexToPrintStart = startIndex;
                indexToPrintEnd = endIndex + 1;
                increment = 1;
            }

            for (int i = indexToPrintStart; i != indexToPrintEnd; i += increment) {
                outputWriter.print(list[i]);

                if (i + increment != indexToPrintEnd) {
                    outputWriter.print(",");
                }
            }
            outputWriter.printLine("]");
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
