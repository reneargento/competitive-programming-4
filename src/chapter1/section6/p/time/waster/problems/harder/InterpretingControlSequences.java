package chapter1.section6.p.time.waster.problems.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/20.
 */
public class InterpretingControlSequences {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int caseNumber = 1;
        int lines = FastReader.nextInt();

        while (lines != 0) {
            char[][] screen = new char[10][10];
            clearScreen(screen);
            int row = 0;
            int column = 0;
            boolean isOverrideMode = true;

            for (int l = 0; l < lines; l++) {
                String input = FastReader.getLine();

                for (int i = 0; i < input.length(); i++) {
                    char symbol = input.charAt(i);
                    if (symbol != '^'
                            || (i < input.length() - 1 && input.charAt(i + 1) == '^')) {
                        if (!isOverrideMode) {
                            shiftSymbolsRight(screen, row, column);
                        }
                        screen[row][column] = symbol;
                        if (column < 9) {
                            column++;
                        }

                        if (symbol == '^' && i < input.length() - 1 && input.charAt(i + 1) == '^') {
                            i++;
                        }
                    } else {
                        // Handle command
                        i++;
                        char command = input.charAt(i);
                        if (Character.isDigit(command)) {
                            i++;
                            row = Character.getNumericValue(command);
                            column = Character.getNumericValue(input.charAt(i));
                        } else {
                            switch (command) {
                                case 'b': column = 0; break;
                                case 'c': clearScreen(screen); break;
                                case 'd':
                                    if (row < screen.length - 1) {
                                        row++;
                                    }
                                    break;
                                case 'e': eraseSymbols(screen, row, column); break;
                                case 'h':
                                    row = 0;
                                    column = 0;
                                    break;
                                case 'i': isOverrideMode = false; break;
                                case 'l':
                                    if (column > 0) {
                                        column--;
                                    }
                                    break;
                                case 'o': isOverrideMode = true; break;
                                case 'r':
                                    if (column < screen[0].length - 1) {
                                        column++;
                                    }
                                    break;
                                case 'u':
                                    if (row > 0) {
                                        row--;
                                    }
                            }
                        }
                    }
                }
            }
            outputWriter.printLine("Case " + caseNumber);
            printTerminalScreen(screen, outputWriter);

            caseNumber++;
            lines = FastReader.nextInt();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void clearScreen(char[][] screen) {
        for (int r = 0; r < screen.length; r++) {
            for (int c = 0; c < screen[0].length; c++) {
                screen[r][c] = ' ';
            }
        }
    }

    private static void shiftSymbolsRight(char[][] screen, int row, int column) {
        for (int c = screen[0].length - 1; c > column; c--) {
            screen[row][c] = screen[row][c - 1];
        }
    }

    private static void eraseSymbols(char[][] screen, int row, int column) {
        for (int c = column; c < screen[0].length; c++) {
            screen[row][c] = ' ';
        }
    }

    private static void printTerminalScreen(char[][] screen, OutputWriter outputWriter) {
        outputWriter.printLine("+----------+");
        for (int r = 0; r < screen.length; r++) {
            outputWriter.print('|');
            for (int c = 0; c < screen[0].length; c++) {
                outputWriter.print(screen[r][c]);
            }
            outputWriter.printLine('|');
        }
        outputWriter.printLine("+----------+");
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
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
