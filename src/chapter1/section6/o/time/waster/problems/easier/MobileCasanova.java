package chapter1.section6.o.time.waster.problems.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/12/20.
 */
public class MobileCasanova {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int phoneNumbers = FastReader.nextInt();
        int caseNumber = 1;

        while (phoneNumbers != 0) {
            outputWriter.printLine("Case " + caseNumber + ":");
            String rangeStart = null;
            String rangeEnd = null;

            String previousNumber = FastReader.next();

            for (int i = 1; i < phoneNumbers; i++) {
                String currentNumber = FastReader.next();

                if (isNextNumber(previousNumber, currentNumber)) {
                    if (rangeStart == null) {
                        rangeStart = previousNumber;
                    }
                    rangeEnd = currentNumber;
                } else {
                    if (rangeStart != null) {
                        printNumberRange(rangeStart, rangeEnd, outputWriter);
                        rangeStart = null;
                        rangeEnd = null;
                    } else {
                        outputWriter.printLine(previousNumber);
                    }
                }
                previousNumber = currentNumber;
            }

            if (rangeStart != null) {
                printNumberRange(rangeStart, rangeEnd, outputWriter);
            } else {
                outputWriter.printLine(previousNumber);
            }

            outputWriter.printLine();
            caseNumber++;
            phoneNumbers = FastReader.nextInt();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static boolean isNextNumber(String number1, String number2) {
        StringBuilder nextNumber = new StringBuilder(number1);
        boolean incremented = false;

        for (int i = nextNumber.length() - 1; i >= 0; i--) {
            char digit = nextNumber.charAt(i);
            int digitValue = Character.getNumericValue(digit);

            if (digitValue != 9) {
                String nextDigit = String.valueOf(digitValue + 1);
                nextNumber.replace(i, i + 1, nextDigit);
                incremented = true;
                break;
            }

            nextNumber.replace(i, i + 1, "0");
        }

        if (!incremented) {
            nextNumber.insert(0, 1);
        }
        return nextNumber.toString().equals(number2);
    }

    private static void printNumberRange(String rangeStart, String rangeEnd, OutputWriter outputWriter) {
        StringBuilder distinctDigits = new StringBuilder();
        boolean differentDigitsStarted = false;

        for (int i = 0; i < rangeStart.length(); i++) {
            if (rangeStart.charAt(i) == rangeEnd.charAt(i) && !differentDigitsStarted) {
                continue;
            } else {
                differentDigitsStarted = true;
                distinctDigits.append(rangeEnd.charAt(i));
            }
        }
        outputWriter.printLine(rangeStart + "-" + distinctDigits.toString());
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        // Used to check EOF
        // If getLine() == null, it is a EOF
        // Otherwise, it returns the next line
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
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
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
