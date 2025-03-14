package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;

/**
 * Created by Rene Argento on 11/03/25.
 */
public class BanglaNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String[] types = { "atahs", "rajah", "hkal", "ituk" };

        int caseNumber = 1;
        String line = FastReader.getLine();

        while (line != null) {
            String numberText = computeBanglaNumberText(line, types);
            outputWriter.printLine(String.format("%4d. %s", caseNumber, numberText));
            caseNumber++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computeBanglaNumberText(String number, String[] types) {
        StringBuilder numberText = new StringBuilder();
        int typeIndex = 0;
        int startIndex;

        char lastDigit = number.charAt(number.length() - 1);
        if (lastDigit != '0') {
            numberText.append(lastDigit);
        }

        if (number.length() >= 2) {
            char beforeLastDigit = number.charAt(number.length() - 2);

            if (beforeLastDigit != '0') {
                if (lastDigit == '0') {
                    numberText.append(lastDigit);
                }
                numberText.append(beforeLastDigit);
            }
            startIndex = number.length() - 3;
        } else {
            startIndex = number.length() - 2;
        }

        for (int i = startIndex; i >= 0; i--) {
            StringBuilder currentTerm = new StringBuilder();
            boolean isNonZero = false;

            currentTerm.append(" ").append(types[typeIndex]).append(" ");
            if (number.charAt(i) != '0') {
                isNonZero = true;
            }

            if (typeIndex == 0) {
                currentTerm.append(number.charAt(i));
            } else {
                if (i >= 1) {
                    currentTerm.append(number.charAt(i));
                    if (number.charAt(i - 1) != '0') {
                        currentTerm.append(number.charAt(i - 1));
                        isNonZero = true;
                    }
                    i--;
                } else {
                    currentTerm.append(number.charAt(i));
                }
            }

            if (isNonZero) {
                numberText.append(currentTerm);
            } else if (typeIndex == 3) {
                numberText.append(" ").append(types[typeIndex]);
            }
            typeIndex++;
            typeIndex %= 4;
        }

        if (numberText.length() == 0) {
            return "0";
        }
        return numberText.reverse().toString().trim();
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