package chapter2.section3.c.direct.addressing.table.dat.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/05/21.
 */
public class Hardware {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int orders = FastReader.nextInt();

        for (int o = 0; o < orders; o++) {
            String roadName = FastReader.getLine();
            int addressesNumber = FastReader.nextInt();
            String addressesString = FastReader.next();

            int addressesComputed = 0;
            int[] digitsNeeded = new int[10];
            int totalDigits = 0;

            while (addressesComputed < addressesNumber) {
                String addressString = FastReader.next();
                if (addressString.equals("+")) {
                    int addressStart = FastReader.nextInt();
                    int addressEnd = FastReader.nextInt();
                    int interval = FastReader.nextInt();

                    for (int address = addressStart; address <= addressEnd; address += interval) {
                        String addressStringValue = String.valueOf(address);
                        processDigits(digitsNeeded, addressStringValue);
                        addressesComputed++;
                        totalDigits += addressStringValue.length();
                    }
                } else {
                    processDigits(digitsNeeded, addressString);
                    addressesComputed++;
                    totalDigits += addressString.length();
                }
            }
            outputResults(roadName, addressesNumber, addressesString, digitsNeeded, totalDigits, outputWriter);
        }
        outputWriter.flush();
    }

    private static void processDigits(int[] digitsNeeded, String address) {
        for (char digit : address.toCharArray()) {
            int digitValue = Character.getNumericValue(digit);
            digitsNeeded[digitValue]++;
        }
    }

    private static void outputResults(String roadName, int addressesNumber, String addressesString, int[] digitsNeeded,
                                      int totalDigits, OutputWriter outputWriter) {
        outputWriter.printLine(roadName);
        outputWriter.printLine(addressesNumber + " " + addressesString);
        for (int digit = 0; digit <= 9; digit++) {
            outputWriter.printLine("Make " + digitsNeeded[digit] + " digit " + digit);
        }
        if (totalDigits == 1) {
            outputWriter.printLine("In total 1 digit");
        } else {
            outputWriter.printLine("In total " + totalDigits + " digits");
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
