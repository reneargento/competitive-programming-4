package chapter2.section3.c.direct.addressing.table.dat.others;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/05/21.
 */
public class FourEightSevenThreeTwoSevenNine {

    private static class TelephoneFrequency implements Comparable<TelephoneFrequency> {
        String phoneNumber;
        int frequency;

        public TelephoneFrequency(String phoneNumber, int frequency) {
            this.phoneNumber = phoneNumber;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(TelephoneFrequency other) {
            return phoneNumber.compareTo(other.phoneNumber);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] letterToDigitMapper = createLetterToDigitMapper();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            int telephoneNumbers = FastReader.nextInt();
            Map<String, Integer> phoneFrequencyMap = new HashMap<>();

            for (int i = 0; i < telephoneNumbers; i++) {
                String phoneNumber = FastReader.next().replace("-", "");
                String standardFormPhone = getStandardFormPhone(phoneNumber, letterToDigitMapper);

                int frequency = phoneFrequencyMap.getOrDefault(standardFormPhone, 0);
                phoneFrequencyMap.put(standardFormPhone, frequency + 1);
            }
            printDuplicatePhoneNumbers(phoneFrequencyMap, outputWriter);
        }
        outputWriter.flush();
    }

    private static String getStandardFormPhone(String phoneNumber, int[] letterToDigitMapper) {
        StringBuilder standardFormPhone = new StringBuilder();

        for (char character : phoneNumber.toCharArray()) {
            if (Character.isLetter(character)) {
                int index = (int) character - 'A';
                int phoneDigit = letterToDigitMapper[index];
                standardFormPhone.append(phoneDigit);
            } else {
                standardFormPhone.append(character);
            }

            if (standardFormPhone.length() == 3) {
                standardFormPhone.append("-");
            }
        }
        return standardFormPhone.toString();
    }

    private static void printDuplicatePhoneNumbers(Map<String, Integer> phoneFrequencyMap,
                                                   OutputWriter outputWriter) {
        List<TelephoneFrequency> duplicatePhones = new ArrayList<>();

        for (String phoneNumber : phoneFrequencyMap.keySet()) {
            int frequency = phoneFrequencyMap.get(phoneNumber);
            if (frequency > 1) {
                TelephoneFrequency telephoneFrequency = new TelephoneFrequency(phoneNumber, frequency);
                duplicatePhones.add(telephoneFrequency);
            }
        }

        if (duplicatePhones.isEmpty()) {
            outputWriter.printLine("No duplicates.");
        } else {
            Collections.sort(duplicatePhones);
            for (TelephoneFrequency telephoneFrequency : duplicatePhones) {
                outputWriter.printLine(telephoneFrequency.phoneNumber + " " + telephoneFrequency.frequency);
            }
        }
    }

    private static int[] createLetterToDigitMapper() {
        return new int[] {
                2, 2, 2,
                3, 3, 3,
                4, 4, 4,
                5, 5, 5,
                6, 6, 6,
                7, -1, 7, 7,
                8, 8, 8,
                9, 9, 9
        };
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
