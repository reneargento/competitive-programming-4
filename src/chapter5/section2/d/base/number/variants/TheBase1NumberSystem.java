package chapter5.section2.d.base.number.variants;

import java.io.*;

/**
 * Created by Rene Argento on 12/02/25.
 */
public class TheBase1NumberSystem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("~")) {
            StringBuilder number = new StringBuilder("0");

            while (!line.equals("~")) {
                String[] data = line.split(" ");
                int flag = 0;

                for (String block : data) {
                    if (block.length() == 1) {
                        flag = 1;
                    } else if (block.length() == 2) {
                        flag = 0;
                    } else {
                        int digitsToAppend = block.length() - 2;
                        for (int i = 0; i < digitsToAppend; i++) {
                            number.append(flag);
                        }
                    }
                }

                if (line.endsWith("#") ) {
                    break;
                }
                line = FastReader.getLine();
            }

            int decimalNumber = Integer.parseInt(number.toString(), 2);
            outputWriter.printLine(decimalNumber);
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
