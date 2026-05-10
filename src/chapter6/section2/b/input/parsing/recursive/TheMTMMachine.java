package chapter6.section2.b.input.parsing.recursive;

import java.io.*;

/**
 * Created by Rene Argento on 08/05/26.
 */
public class TheMTMMachine {

    private static final String NOT_ACCEPTABLE = "NOT ACCEPTABLE";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String digits = FastReader.getLine();
        while (!digits.equals("0")) {
            String result = processNumber(digits);
            outputWriter.printLine(result);
            digits = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String processNumber(String digits) {
        if (digits.contains("0")) {
            return NOT_ACCEPTABLE;
        }
        return processNumber(digits, 0, false, false);
    }

    private static String processNumber(String digits, int index, boolean isInside2Rule, boolean isInside3Rule) {
        if (index >= digits.length() - 1 && isInside2Rule) {
            return digits;
        }

        if (digits.charAt(index) == '2') {
            if (isInside3Rule && !isInside2Rule) {
                String nextNumber = processNumber(digits.substring(index + 1), 0, false, true);
                return getAssociate(nextNumber);
            } else {
                if (index < digits.length() - 1) {
                    String newNumber = digits.substring(index + 1);
                    return processNumber(newNumber, 0, true, false);
                } else {
                    if (isInside2Rule) {
                        return "2";
                    } else {
                        return NOT_ACCEPTABLE;
                    }
                }
            }
        } else if (!isInside2Rule
                && digits.charAt(index) == '3'
                && index < digits.length() - 1) {
            if (digits.charAt(index + 1) == '3') {
                String nextNumber = processNumber(digits, index + 1, false, true);
                return getAssociate(nextNumber);
            } else if (digits.charAt(index + 1) == '2' && index < digits.length() - 2) {
                String nextNumber = processNumber(digits.substring(index + 2), 0, true, true);
                return getAssociate(nextNumber);
            } else {
                return NOT_ACCEPTABLE;
            }
        } else {
            if (isInside2Rule) {
                return digits.substring(index);
            } else {
                return NOT_ACCEPTABLE;
            }
        }
    }

    private static String getAssociate(String number) {
        if (number.equals(NOT_ACCEPTABLE)) {
            return NOT_ACCEPTABLE;
        } else {
            return number + "2" + number;
        }
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
