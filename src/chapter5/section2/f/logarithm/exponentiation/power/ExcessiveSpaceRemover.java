package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;

/**
 * Created by Rene Argento on 18/03/25.
 */
public class ExcessiveSpaceRemover {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int actionsNeeded = countActionsNeeded(line);
            outputWriter.printLine(actionsNeeded);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countActionsNeeded(String line) {
        int maxConsecutiveSpaces = 0;
        int consecutiveSpaces = 0;
        boolean isSpaceSequence = false;

        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);

            if (symbol == ' ') {
                if (isSpaceSequence) {
                    consecutiveSpaces++;
                } else {
                    isSpaceSequence = true;
                    consecutiveSpaces = 1;
                }
            } else {
                if (isSpaceSequence) {
                    maxConsecutiveSpaces = Math.max(maxConsecutiveSpaces, consecutiveSpaces);
                }
                isSpaceSequence = false;
            }
        }
        return log2Ceiling(maxConsecutiveSpaces);
    }

    private static int log2Ceiling(int number) {
        return (int) Math.ceil(Math.log(number) / Math.log(2));
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
