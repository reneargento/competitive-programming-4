package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;

/**
 * Created by Rene Argento on 21/03/25.
 */
public class TheBackslashProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        String[] backslashes = computeBackslashesPerLevel();

        while (line != null) {
            int levels = Integer.parseInt(line);
            String string = FastReader.getLine();

            String stringWithBackslashes = addBackslashes(levels, string, backslashes);
            outputWriter.printLine(stringWithBackslashes);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String addBackslashes(int levels, String string, String[] backslashes) {
        StringBuilder finalString = new StringBuilder();

        for (char character : string.toCharArray()) {
            if (isSpecial(character)) {
                finalString.append(backslashes[levels]);
            }
            finalString.append(character);
        }
        return finalString.toString();
    }

    private static boolean isSpecial(char character) {
        return '!' <= character && character <= '*'
                || '[' <= character && character <= ']';
    }

    private static String[] computeBackslashesPerLevel() {
        String[] backslashes = new String[9];
        backslashes[0] = "";
        int backslashesInLevel = 1;

        for (int i = 1; i < backslashes.length; i++) {
            StringBuilder backslashesString = new StringBuilder();

            for (int b = 0; b < backslashesInLevel; b++) {
                backslashesString.append("\\");
            }
            backslashes[i] = backslashes[i - 1] + backslashesString;
            backslashesInLevel *= 2;
        }
        return backslashes;
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
