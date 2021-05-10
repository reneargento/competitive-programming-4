package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;

/**
 * Created by Rene Argento on 08/05/21.
 */
public class AlphabetSpam {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String email = FastReader.getLine();

        double[] ratios = computeRatios(email);
        for (double ratio : ratios) {
            outputWriter.printLine(String.format("%.7f", ratio));
        }
        outputWriter.flush();
    }

    private static double[] computeRatios(String email) {
        int whitespaces = 0;
        int lowercaseLetters = 0;
        int uppercaseLetters = 0;
        int symbols = 0;

        for (char character : email.toCharArray()) {
            if (Character.isLetter(character)) {
                if (Character.isLowerCase(character)) {
                    lowercaseLetters++;
                } else {
                    uppercaseLetters++;
                }
            } else if (character == '_') {
                whitespaces++;
            } else {
                symbols++;
            }
        }

        double length = email.length();
        double whitespacesRatio = whitespaces / length;
        double lowercaseLettersRatio = lowercaseLetters / length;
        double uppercaseLettersRatio = uppercaseLetters / length;
        double symbolsRatio = symbols / length;
        return new double[] { whitespacesRatio, lowercaseLettersRatio, uppercaseLettersRatio, symbolsRatio };
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
