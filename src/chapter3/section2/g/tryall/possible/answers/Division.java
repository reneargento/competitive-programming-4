package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/12/21.
 */
public class Division {

    private static class DivisionValues implements Comparable<DivisionValues> {
        int numerator;
        int denominator;

        public DivisionValues(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        @Override
        public int compareTo(DivisionValues other) {
            return Integer.compare(numerator, other.numerator);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int targetNumber = FastReader.nextInt();
        int caseId = 0;

        while (targetNumber != 0) {
            if (caseId > 0) {
                outputWriter.printLine();
            }

            List<DivisionValues> divisions = getDivisions(targetNumber);
            if (divisions.isEmpty()) {
                outputWriter.printLine(String.format("There are no solutions for %d.", targetNumber));
            } else {
                for (DivisionValues values : divisions) {
                    outputWriter.printLine(String.format("%05d / %05d = %d", values.numerator,
                            values.denominator, targetNumber));
                }
            }

            caseId++;
            targetNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<DivisionValues> getDivisions(int targetNumber) {
        List<DivisionValues> divisions = new ArrayList<>();

        for (int number1 = 1234; number1 <= 99999; number1++) {
            if (number1 % targetNumber == 0) {
                int number2 = number1 / targetNumber;
                if (number2 >= 1234 && hasUniqueDigits(number1, number2)) {
                    divisions.add(new DivisionValues(number1, number2));
                }
            }
        }
        Collections.sort(divisions);
        return divisions;
    }

    private static boolean hasUniqueDigits(int number1, int number2) {
        boolean[] visited = new boolean[10];

        if (number1 < 9999) {
            visited[0] = true;
        }
        if (number2 < 9999) {
            if (visited[0]) {
                return false;
            }
            visited[0] = true;
        }

        while (number1 > 0) {
            int digit = number1 % 10;
            number1 /= 10;
            if (visited[digit]) {
                return false;
            }
            visited[digit] = true;
        }

        while (number2 > 0) {
            int digit = number2 % 10;
            number2 /= 10;
            if (visited[digit]) {
                return false;
            }
            visited[digit] = true;
        }
        return true;
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
