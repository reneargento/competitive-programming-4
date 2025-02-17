package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/02/25.
 */
public class HumbleNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numberIndex = FastReader.nextInt();
        long[] humbleNumbers = computeHumbleNumbers();

        while (numberIndex != 0) {
            long nthHumbleNumber = humbleNumbers[numberIndex - 1];
            String numberSuffix = computeNumberSuffix(numberIndex);
            outputWriter.printLine(String.format("The %d%s humble number is %d.", numberIndex, numberSuffix,
                    nthHumbleNumber));

            numberIndex = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long[] computeHumbleNumbers() {
        long[] humbleNumbers = new long[5842];
        List<Long> humbleNumbersList = new ArrayList<>();

        long power2 = 1;
        for (int power2Index = 0; power2Index <= 30; power2Index++) {
            long power3 = 1;
            for (int power3Index = 0; power3Index <= 20; power3Index++) {
                long power5 = 1;
                for (int power5Index = 0; power5Index <= 15; power5Index++) {
                    long power7 = 1;
                    for (int power7Index = 0; power7Index <= 15; power7Index++) {
                        long humbleNumber = power2 * power3 * power5 * power7;
                        if (humbleNumber < 0) {
                            // Discard overflowed values
                            break;
                        }
                        humbleNumbersList.add(humbleNumber);
                        power7 *= 7;
                    }
                    power5 *= 5;
                }
                power3 *= 3;
            }
            power2 *= 2;
        }

        Collections.sort(humbleNumbersList);
        for (int i = 0; i < humbleNumbers.length; i++) {
            humbleNumbers[i] = humbleNumbersList.get(i);
        }
        return humbleNumbers;
    }

    private static String computeNumberSuffix(int numberIndex) {
        String numberString = String.valueOf(numberIndex);
        if (numberString.length() >= 2) {
            String lastDigits = numberString.substring(numberString.length() - 2);
            if (lastDigits.equals("11") ||
                    lastDigits.equals("12") ||
                    lastDigits.equals("13")) {
                return "th";
            }
        }

        char lastDigit = numberString.charAt(numberString.length() - 1);
        if (lastDigit == '1') {
            return "st";
        } else if (lastDigit == '2') {
            return "nd";
        } else if (lastDigit == '3') {
            return "rd";
        } else {
            return "th";
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
