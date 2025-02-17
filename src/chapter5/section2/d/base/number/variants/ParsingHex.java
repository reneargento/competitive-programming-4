package chapter5.section2.d.base.number.variants;

import java.io.*;

/**
 * Created by Rene Argento on 15/02/25.
 */
public class ParsingHex {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            parseNumbers(line, outputWriter);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void parseNumbers(String line, OutputWriter outputWriter) {
        StringBuilder currentNumber = new StringBuilder();
        boolean isNumberStarted = false;

        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);

            if (isNumberStarted) {
                if (isValidSymbol(symbol)) {
                    currentNumber.append(symbol);
                } else {
                    if (currentNumber.length() > 2) {
                        String hexadecimalNumber = currentNumber.toString();
                        printNumbers(hexadecimalNumber, outputWriter);
                    }
                    currentNumber = new StringBuilder();
                    isNumberStarted = false;
                }
            } else if (symbol == '0' &&
                    i < line.length() - 1 &&
                    (line.charAt(i + 1) == 'x' || line.charAt(i + 1) == 'X')) {
                currentNumber.append(symbol).append(line.charAt(i + 1));
                isNumberStarted = true;
                i++;
            }
        }

        if (isNumberStarted && currentNumber.length() > 2) {
            String hexadecimalNumber = currentNumber.toString();
            printNumbers(hexadecimalNumber, outputWriter);
        }
    }

    private static void printNumbers(String hexadecimalNumber, OutputWriter outputWriter) {
        long decimalNumber = Long.parseLong(hexadecimalNumber.substring(2), 16);
        outputWriter.printLine(String.format("%s %d", hexadecimalNumber, decimalNumber));
    }

    private static boolean isValidSymbol(char symbol) {
        char uppercaseSymbol = Character.toUpperCase(symbol);
        return Character.isDigit(symbol) ||
                (Character.isLetter(symbol) && 'A'<= uppercaseSymbol && uppercaseSymbol <= 'F');
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
