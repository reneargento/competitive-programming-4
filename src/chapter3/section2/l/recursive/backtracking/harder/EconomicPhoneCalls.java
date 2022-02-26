package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/02/22.
 */
// Based on https://morris821028.github.io/2014/11/09/oj/uva/uva-11052/
public class EconomicPhoneCalls {

    private static class Entry implements Comparable<Entry> {
        int month;
        int day;
        int hours;
        int minutes;
        boolean shouldBeKept;
        int year;

        public Entry(int month, int day, int hours, int minutes, boolean shouldBeKept) {
            this.month = month;
            this.day = day;
            this.hours = hours;
            this.minutes = minutes;
            this.shouldBeKept = shouldBeKept;
        }

        @Override
        public int compareTo(Entry other) {
            if (month != other.month) {
                return Integer.compare(month, other.month);
            }
            if (day != other.day) {
                return Integer.compare(day, other.day);
            }
            if (hours != other.hours) {
                return Integer.compare(hours, other.hours);
            }
            if (minutes != other.minutes) {
                return Integer.compare(minutes, other.minutes);
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int entriesNumber = FastReader.nextInt();

        while (entriesNumber != 0) {
            Entry[] entries = new Entry[entriesNumber];

            for (int i = 0; i < entries.length; i++) {
                String entryLine = FastReader.getLine();
                entries[i] = getEntry(entryLine);
            }
            computeYears(entries);
            int minimumEntriesToKeep = computeMinimumEntriesToKeep(entries);
            outputWriter.printLine(minimumEntriesToKeep);

            entriesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Entry getEntry(String entryLine) {
        String[] entryData = entryLine.split(" ");
        String[] date = entryData[0].split(":");
        int month = Integer.parseInt(date[0]);
        int day = Integer.parseInt(date[1]);
        int hours = Integer.parseInt(date[2]);
        int minutes = Integer.parseInt(date[3]);
        boolean shouldBeKept = entryData[2].equals("+");
        return new Entry(month, day, hours, minutes, shouldBeKept);
    }

    private static void computeYears(Entry[] entries) {
        entries[entries.length - 1].year = 1000;

        for (int i = entries.length - 2; i >= 0; i--) {
            int currentYear = entries[i + 1].year;

            if (entries[i].compareTo(entries[i + 1]) < 0) {
                entries[i].year = currentYear;
            } else {
                entries[i].year = currentYear - 1;
            }
        }
    }

    private static int computeMinimumEntriesToKeep(Entry[] entries) {
        int[] minimumEntries = new int[entries.length];
        int firstEntry = -1;
        int lastEntry = -1;
        int currentYear = entries[entries.length - 1].year;

        for (int i = entries.length - 1; i >= 0; i--) {
            if (lastEntry == -1 && entries[i].year == currentYear) {
                minimumEntries[i] = 1;
            } else {
                minimumEntries[i] = entries.length - i;
            }

            if (lastEntry == -1 && (entries[i].shouldBeKept || entries[i].year != currentYear)) {
                lastEntry = i;
            }
            if (entries[i].shouldBeKept) {
                firstEntry = i;
            }
        }

        for (int i = lastEntry; i >= firstEntry; i--) {
            for (int j = i + 1; j < entries.length; j++) {
                if (entries[i].year == entries[j].year
                        || (entries[i].compareTo(entries[j]) >= 0
                            && entries[i].year == entries[j].year - 1)) {
                    minimumEntries[i] = Math.min(minimumEntries[i], minimumEntries[j] + 1);
                } else {
                    break;
                }

                if (entries[j].shouldBeKept) {
                    break;
                }
            }
        }
        return minimumEntries[firstEntry];
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
