package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/11/21.
 */
public class Dreamer {

    private static class Date implements Comparable<Date> {
        int day;
        int month;
        int year;

        public Date(int day, int month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Date date = (Date) o;
            return day == date.day && month == date.month && year == date.year;
        }

        @Override
        public int hashCode() {
            return Objects.hash(day, month, year);
        }

        @Override
        public int compareTo(Date other) {
            if (year != other.year) {
                return Integer.compare(year, other.year);
            }
            if (month != other.month) {
                return Integer.compare(month, other.month);
            }
            return Integer.compare(day, other.day);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine();
            char[] digits = getDigits(line);

            Set<Date> validDates = computeValidDates(digits);
            if (validDates.isEmpty()) {
                outputWriter.printLine("0");
            } else {
                List<Date> dateList = new ArrayList<>(validDates);
                Collections.sort(dateList);
                Date earliestDate = dateList.get(0);
                outputWriter.printLine(String.format("%d %02d %02d %d", validDates.size(),
                        earliestDate.day, earliestDate.month, earliestDate.year));
            }
        }
        outputWriter.flush();
    }

    private static Set<Date> computeValidDates(char[] digits) {
        Set<Date> validDates = new HashSet<>();
        char[] currentDigits = new char[digits.length];
        computeValidDates(digits, currentDigits, 0, 0, validDates);
        return validDates;
    }

    private static void computeValidDates(char[] digits, char[] currentDigits, int index,
                                          int mask, Set<Date> validDates) {
        if (mask == (1 << digits.length) - 1) {
            Date date = createValidDate(currentDigits);
            if (date != null) {
                validDates.add(date);
            }
            return;
        }

        for (int i = 0; i < digits.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                currentDigits[index] = digits[i];
                computeValidDates(digits, currentDigits, index + 1, newMask, validDates);
            }
        }
    }

    private static Date createValidDate(char[] digits) {
        String allDigits = new String(digits);
        int day = Integer.parseInt(allDigits.substring(0, 2));
        int month = Integer.parseInt(allDigits.substring(2, 4));
        int year = Integer.parseInt(allDigits.substring(4));

        if (year < 2000) {
            return null;
        }
        if (month > 12) {
            return null;
        }
        if (day == 0 || month == 0) {
            return null;
        }
        if (monthHas30Days(month) && day > 30) {
            return null;
        }
        if (month == 2
                && ((isLeapYear(year) && day > 29)
                        || (!isLeapYear(year) && day > 28))) {
            return null;
        }
        if (monthHas30Days(month) || day <= 31) {
            return new Date(day, month, year);
        }
        return null;
    }

    private static boolean monthHas30Days(int month) {
        return month == 4
                || month == 6
                || month == 9
                || month == 11;
    }

    private static boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }

    private static char[] getDigits(String line) {
        char[] digits = new char[8];
        digits[0] = line.charAt(0);
        digits[1] = line.charAt(1);
        digits[2] = line.charAt(3);
        digits[3] = line.charAt(4);
        digits[4] = line.charAt(6);
        digits[5] = line.charAt(7);
        digits[6] = line.charAt(8);
        digits[7] = line.charAt(9);
        return digits;
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
