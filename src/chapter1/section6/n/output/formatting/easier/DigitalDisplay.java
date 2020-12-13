package chapter1.section6.n.output.formatting.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/12/20.
 */
public class DigitalDisplay {

    private static final char[][] DIGIT_1 = {
            "    +".toCharArray(),
            "    |".toCharArray(),
            "    |".toCharArray(),
            "    +".toCharArray(),
            "    |".toCharArray(),
            "    |".toCharArray(),
            "    +".toCharArray()
    };

    private static final char[][] DIGIT_2 = {
            "+---+".toCharArray(),
            "    |".toCharArray(),
            "    |".toCharArray(),
            "+---+".toCharArray(),
            "|    ".toCharArray(),
            "|    ".toCharArray(),
            "+---+".toCharArray()
    };

    private static final char[][] DIGIT_3 = {
            "+---+".toCharArray(),
            "    |".toCharArray(),
            "    |".toCharArray(),
            "+---+".toCharArray(),
            "    |".toCharArray(),
            "    |".toCharArray(),
            "+---+".toCharArray()
    };

    private static final char[][] DIGIT_4 = {
            "+   +".toCharArray(),
            "|   |".toCharArray(),
            "|   |".toCharArray(),
            "+---+".toCharArray(),
            "    |".toCharArray(),
            "    |".toCharArray(),
            "    +".toCharArray()
    };

    private static final char[][] DIGIT_5 = {
            "+---+".toCharArray(),
            "|    ".toCharArray(),
            "|    ".toCharArray(),
            "+---+".toCharArray(),
            "    |".toCharArray(),
            "    |".toCharArray(),
            "+---+".toCharArray()
    };

    private static final char[][] DIGIT_6 = {
            "+---+".toCharArray(),
            "|    ".toCharArray(),
            "|    ".toCharArray(),
            "+---+".toCharArray(),
            "|   |".toCharArray(),
            "|   |".toCharArray(),
            "+---+".toCharArray()
    };

    private static final char[][] DIGIT_7 = {
            "+---+".toCharArray(),
            "    |".toCharArray(),
            "    |".toCharArray(),
            "    +".toCharArray(),
            "    |".toCharArray(),
            "    |".toCharArray(),
            "    +".toCharArray()
    };

    private static final char[][] DIGIT_8 = {
            "+---+".toCharArray(),
            "|   |".toCharArray(),
            "|   |".toCharArray(),
            "+---+".toCharArray(),
            "|   |".toCharArray(),
            "|   |".toCharArray(),
            "+---+".toCharArray()
    };

    private static final char[][] DIGIT_9 = {
            "+---+".toCharArray(),
            "|   |".toCharArray(),
            "|   |".toCharArray(),
            "+---+".toCharArray(),
            "    |".toCharArray(),
            "    |".toCharArray(),
            "+---+".toCharArray()
    };

    private static final char[][] DIGIT_0 = {
            "+---+".toCharArray(),
            "|   |".toCharArray(),
            "|   |".toCharArray(),
            "+   +".toCharArray(),
            "|   |".toCharArray(),
            "|   |".toCharArray(),
            "+---+".toCharArray()
    };

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        String timeDigits = FastReader.next();

        while (timeDigits.charAt(0) != 'e') {
            displayTime(timeDigits, outputWriter);

            outputWriter.printLine();
            outputWriter.printLine();
            timeDigits = FastReader.next();
        }
        outputWriter.printLine("end");
        outputWriter.flush();
        outputWriter.close();
    }

    private static void displayTime(String timeDigits, OutputWriter outputWriter) {
        char[][] time = new char[7][29];
        initializeTime(time);
        time[2][14] = 'o';
        time[4][14] = 'o';

        for (int i = 0; i < timeDigits.length() - 1; i++) {
            int index = i < 2 ? i : i + 1;

            char digit = timeDigits.charAt(index);
            int column = i * 7;

            if (i >= 2) {
                column += 3;
            }

            addDigit(time, digit, column);
        }
        display(time, outputWriter);
    }

    private static void initializeTime(char[][] time) {
        for (int row = 0; row < time.length; row++) {
            for (int column = 0; column < time[0].length; column++) {
                time[row][column] = ' ';
            }
        }
    }

    private static void addDigit(char[][] time, char digit, int startColumn) {
        switch (digit) {
            case '0': addDigit(time, DIGIT_0, startColumn); return;
            case '1': addDigit(time, DIGIT_1, startColumn); return;
            case '2': addDigit(time, DIGIT_2, startColumn); return;
            case '3': addDigit(time, DIGIT_3, startColumn); return;
            case '4': addDigit(time, DIGIT_4, startColumn); return;
            case '5': addDigit(time, DIGIT_5, startColumn); return;
            case '6': addDigit(time, DIGIT_6, startColumn); return;
            case '7': addDigit(time, DIGIT_7, startColumn); return;
            case '8': addDigit(time, DIGIT_8, startColumn); return;
            default: addDigit(time, DIGIT_9, startColumn);
        }
    }

    private static void addDigit(char[][] time, char[][] digit, int startColumn) {
        for (int row = 0; row < 7; row++) {
            for (int column = startColumn; column < startColumn + 5; column++) {
                time[row][column] = digit[row][column - startColumn];
            }
        }
    }

    private static void display(char[][] time, OutputWriter outputWriter) {
        for (int row = 0; row < time.length; row++) {
            for (int column = 0; column < time[0].length; column++) {
                outputWriter.print(time[row][column]);
            }
            outputWriter.printLine();
        }
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
