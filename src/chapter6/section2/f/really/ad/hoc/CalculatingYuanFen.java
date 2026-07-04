package chapter6.section2.f.really.ad.hoc;

import java.io.*;

/**
 * Created by Rene Argento on 03/07/2026.
 */
public class CalculatingYuanFen {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String names = FastReader.getLine();
        while (names != null) {
            String result = computeYuanFen(names);
            outputWriter.printLine(result);
            names = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computeYuanFen(String names) {
        for (int st = 1; st <= 10000; st++) {
            StringBuilder digits = new StringBuilder();
            for (char character : names.toCharArray()) {
                String value = convertCharacter(character, st);
                digits.append(value);
            }

            while (true) {
                if (digits.length() == 2 || digits.toString().equals("100")) {
                    if (digits.toString().equals("100")) {
                        return String.valueOf(st);
                    }
                    break;
                }

                StringBuilder nextDigits = new StringBuilder();
                for (int i = 0; i < digits.length() - 1; i++) {
                    int sum = Character.getNumericValue(digits.charAt(i))
                            + Character.getNumericValue(digits.charAt(i + 1));
                    String sumString = String.valueOf(sum);
                    char digit;
                    if (sumString.length() == 1) {
                        digit = sumString.charAt(0);
                    } else {
                        digit = sumString.charAt(1);
                    }
                    nextDigits.append(digit);
                }
                digits = nextDigits;
            }
        }
        return ":(";
    }

    private static String convertCharacter(char character, int st) {
        int value = st + (character - 'A');
        return String.valueOf(value);
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