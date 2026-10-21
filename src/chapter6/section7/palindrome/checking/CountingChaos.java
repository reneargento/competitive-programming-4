package chapter6.section7.palindrome.checking;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/10/2026.
 */
public class CountingChaos {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String currentTime = FastReader.getLine();
            String nextPalindromicTime = getNextPalindromicTime(currentTime);
            outputWriter.printLine(nextPalindromicTime);
        }
        outputWriter.flush();
    }

    private static String getNextPalindromicTime(String currentTime) {
        while (true) {
            int timeMinutes = timeToMinutes(currentTime) + 1;
            if (timeMinutes >= 1440) {
                timeMinutes = 0;
            }
            currentTime = minutesToTime(timeMinutes);
            if (isPalindrome(currentTime)) {
                return currentTime;
            }
        }
    }

    private static int timeToMinutes(String time) {
        int hourMinutes = Integer.parseInt(time.substring(0, 2)) * 60;
        return hourMinutes + Integer.parseInt(time.substring(3));
    }

    private static String minutesToTime(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return formatField(hours) + ":" + formatField(minutes);
    }

    private static String formatField(int value) {
        String fieldString = String.valueOf(value);
        if (fieldString.length() == 1) {
            fieldString = "0" + fieldString;
        }
        return fieldString;
    }

    private static boolean isPalindrome(String time) {
        time = removeLeadingZeroes(time);
        time = time.replace(":", "");
        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) != time.charAt(time.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    private static String removeLeadingZeroes(String time) {
        int startIndex = 0;
        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) != '0' && time.charAt(i) != ':') {
                startIndex = i;
                break;
            }
        }
        return time.substring(startIndex);
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

        public void flush() {
            writer.flush();
        }
    }
}