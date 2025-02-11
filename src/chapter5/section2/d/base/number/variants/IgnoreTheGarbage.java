package chapter5.section2.d.base.number.variants;

import java.io.*;

/**
 * Created by Rene Argento on 04/02/25.
 */
public class IgnoreTheGarbage {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int kIndex = Integer.parseInt(line);
            String kthNumberShown = getKthNumberShown(kIndex);
            outputWriter.printLine(kthNumberShown);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String getKthNumberShown(int kIndex) {
        StringBuilder kthNumberShown = new StringBuilder();
        String alphabet = "0125986";
        int alphabetSize = alphabet.length();

        while (kIndex > 0) {
            int digit = kIndex % alphabetSize;
            kthNumberShown.append(alphabet.charAt(digit));
            kIndex /= alphabetSize;
        }
        return kthNumberShown.toString();
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
