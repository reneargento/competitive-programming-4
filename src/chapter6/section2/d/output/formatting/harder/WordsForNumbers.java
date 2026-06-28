package chapter6.section2.d.output.formatting.harder;

import java.io.*;

/**
 * Created by Rene Argento on 28/06/2026.
 */
public class WordsForNumbers {

    private static final String[] UNITS = {
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    };

    private static final String[] TEN_UNITS = {
            "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TEN_MULTIPLE_UNITS = {
            "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            modifyLine(line, outputWriter);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void modifyLine(String line, OutputWriter outputWriter) {
        String[] tokens =  line.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (i > 0) {
                outputWriter.print(" ");
            }
            if (Character.isDigit(token.charAt(0))) {
                String convertedNumber = convertNumber(token);
                if (i == 0) {
                    convertedNumber = convertedNumber.substring(0, 1).toUpperCase() + convertedNumber.substring(1);
                }
                outputWriter.print(convertedNumber);
            } else {
                outputWriter.print(token);
            }
        }
        outputWriter.printLine();
    }

    private static String convertNumber(String number) {
        if (number.length() == 2 && number.charAt(0) == '0') {
            number = number.substring(1);
        }

        StringBuilder convertedNumber = new StringBuilder();
        if (number.length() == 2) {
            int numberValue = Integer.parseInt(number);
            if (11 <= numberValue && numberValue <= 19) {
                int decimalIndex = numberValue - 11;
                return TEN_UNITS[decimalIndex];
            }

            int decimalIndex = Integer.parseInt(number.substring(0, 1)) - 1;
            convertedNumber.append(TEN_MULTIPLE_UNITS[decimalIndex]);

            if (number.charAt(1) == '0') {
                return convertedNumber.toString();
            }
            convertedNumber.append("-");
            number = number.substring(1);
        }

        int unitIndex = Integer.parseInt(number);
        convertedNumber.append(UNITS[unitIndex]);
        return convertedNumber.toString();
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