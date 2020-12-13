package chapter1.section6.n.output.formatting.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/12/20.
 */
public class InBraille {

    private static final char[][] BRAILLE_1 = {
            {'*', '.'},
            {'.', '.'},
            {'.', '.'}
    };

    private static final char[][] BRAILLE_2 = {
            {'*', '.'},
            {'*', '.'},
            {'.', '.'}
    };

    private static final char[][] BRAILLE_3 = {
            {'*', '*'},
            {'.', '.'},
            {'.', '.'}
    };

    private static final char[][] BRAILLE_4 = {
            {'*', '*'},
            {'.', '*'},
            {'.', '.'}
    };

    private static final char[][] BRAILLE_5 = {
            {'*', '.'},
            {'.', '*'},
            {'.', '.'}
    };

    private static final char[][] BRAILLE_6 = {
            {'*', '*'},
            {'*', '.'},
            {'.', '.'}
    };

    private static final char[][] BRAILLE_7 = {
            {'*', '*'},
            {'*', '*'},
            {'.', '.'}
    };

    private static final char[][] BRAILLE_8 = {
            {'*', '.'},
            {'*', '*'},
            {'.', '.'}
    };

    private static final char[][] BRAILLE_9 = {
            {'.', '*'},
            {'*', '.'},
            {'.', '.'}
    };

    private static final char[][] BRAILLE_0 = {
            {'.', '*'},
            {'*', '*'},
            {'.', '.'}
    };

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int digitsLength = FastReader.nextInt();

        while (digitsLength != 0) {
            char type = FastReader.next().charAt(0);
            int columns = (digitsLength * 3) - 1;

            if (type == 'S') {
                String digits = FastReader.next();
                char[][] output = new char[3][columns];
                initializeMatrix(output);

                for (int i = 0; i < digits.length(); i++) {
                    char digit = digits.charAt(i);
                    char[][] braileDigit = getBraileFromDigit(digit);

                    int startColumn = i * 3;
                    for (int row = 0; row < 3; row++) {
                        for (int column = startColumn; column < startColumn + 2; column++) {
                            output[row][column] = braileDigit[row][column - startColumn];
                        }
                    }
                }
                printBraileDigits(output, outputWriter);
            } else {
                char[][] input = new char[3][columns];

                for (int row = 0; row < 3; row++) {
                    input[row] = FastReader.getLine().toCharArray();
                }

                for (int column = 0; column < input[0].length; column += 3) {
                    int digit = getDigitFromBraile(input, column);
                    outputWriter.print(digit);
                }
                outputWriter.printLine();
            }
            digitsLength = FastReader.nextInt();
        }

        outputWriter.flush();
        outputWriter.close();
    }

    private static void printBraileDigits(char[][] digits, OutputWriter outputWriter) {
        for (int row = 0; row < digits.length; row++) {
            for (int column = 0; column < digits[0].length; column++) {
                outputWriter.print(digits[row][column]);
            }
            outputWriter.printLine();
        }
    }

    private static int getDigitFromBraile(char[][] digits, int startColumn) {
        if (isBraileDigit(digits, startColumn, BRAILLE_0)) {
            return 0;
        }
        if (isBraileDigit(digits, startColumn, BRAILLE_1)) {
            return 1;
        }
        if (isBraileDigit(digits, startColumn, BRAILLE_2)) {
            return 2;
        }
        if (isBraileDigit(digits, startColumn, BRAILLE_3)) {
            return 3;
        }
        if (isBraileDigit(digits, startColumn, BRAILLE_4)) {
            return 4;
        }
        if (isBraileDigit(digits, startColumn, BRAILLE_5)) {
            return 5;
        }
        if (isBraileDigit(digits, startColumn, BRAILLE_6)) {
            return 6;
        }
        if (isBraileDigit(digits, startColumn, BRAILLE_7)) {
            return 7;
        }
        if (isBraileDigit(digits, startColumn, BRAILLE_8)) {
            return 8;
        }
        return 9;
    }

    private static boolean isBraileDigit(char[][] digits, int startColumn, char[][] digit) {
        for (int row = 0; row < 3; row++) {
            for (int column = startColumn; column < startColumn + 2; column++) {
                if (digits[row][column] != digit[row][column - startColumn]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static char[][] getBraileFromDigit(char digit) {
        switch (digit) {
            case '0': return BRAILLE_0;
            case '1': return BRAILLE_1;
            case '2': return BRAILLE_2;
            case '3': return BRAILLE_3;
            case '4': return BRAILLE_4;
            case '5': return BRAILLE_5;
            case '6': return BRAILLE_6;
            case '7': return BRAILLE_7;
            case '8': return BRAILLE_8;
            default: return BRAILLE_9;
        }
    }

    private static void initializeMatrix(char[][] digits) {
        for (int row = 0; row < digits.length; row++) {
            for (int column = 0; column < digits[0].length; column++) {
                digits[row][column] = ' ';
            }
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
