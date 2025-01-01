package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;

/**
 * Created by Rene Argento on 27/12/24.
 */
public class DissectingAHexagon {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            boolean canDissect;
            if (line.charAt(0) == '-') {
                canDissect = false;
            } else {
                long targetParallelograms = Long.parseLong(line);
                canDissect = (targetParallelograms != 0 && targetParallelograms % 3 == 0);
            }
            outputWriter.printLine(canDissect ? "1" : "0");
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
