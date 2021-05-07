package chapter2.section3.a.priority.queue;

import java.io.*;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by Rene Argento on 01/05/21.
 */
public class JugglingPatterns {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String pattern = FastReader.getLine();

        while (pattern != null) {
            int balls = countBalls(pattern);

            outputWriter.print(pattern + ": ");
            if (balls == -1) {
                outputWriter.printLine("invalid # of balls");
            } else {
                boolean isValid = evaluatePattern(pattern);
                if (isValid) {
                    outputWriter.printLine(String.format("valid with %d balls", balls));
                } else {
                    outputWriter.printLine("invalid pattern");
                }
            }
            pattern = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countBalls(String pattern) {
        int sum = 0;

        for (int i = 0; i < pattern.length(); i++) {
            int digit = Character.getNumericValue(pattern.charAt(i));
            sum += digit;
        }

        int balls = sum / pattern.length();
        if (balls * pattern.length() != sum) {
            return -1;
        }
        return balls;
    }

    private static boolean evaluatePattern(String pattern) {
        PriorityQueue<Integer> catchTimes = new PriorityQueue<>();
        Set<Integer> catchTimesSet = new HashSet<>();

        int digit = Character.getNumericValue(pattern.charAt(0));
        int catchTime = digit + 1;
        catchTimes.offer(catchTime);

        for (int i = 1; i < 1000; i++) {
            int nextCatchTime = catchTimes.poll();

            if (catchTimesSet.contains(nextCatchTime)) {
                return false;
            }
            catchTimesSet.add(nextCatchTime);

            int nextIndex = i % pattern.length();
            digit = Character.getNumericValue(pattern.charAt(nextIndex));
            catchTime = digit + (i + 1);
            catchTimes.offer(catchTime);
        }
        return true;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
