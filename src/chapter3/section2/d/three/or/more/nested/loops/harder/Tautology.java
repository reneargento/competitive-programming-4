package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 14/11/21.
 */
public class Tautology {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String wff = FastReader.getLine();

        while (!wff.equals("0")) {
            if (isTautology(wff)) {
                outputWriter.printLine("tautology");
            } else {
                outputWriter.printLine("not");
            }
            wff = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isTautology(String wff) {
        for (int p = 0; p < 2; p++) {
            for (int q = 0; q < 2; q++) {
                for (int r = 0; r < 2; r++) {
                    for (int s = 0; s < 2; s++) {
                        for (int t = 0; t < 2; t++) {
                            String copyWff = assignSymbol(wff, 'p', p);
                            copyWff = assignSymbol(copyWff, 'q', q);
                            copyWff = assignSymbol(copyWff, 'r', r);
                            copyWff = assignSymbol(copyWff, 's', s);
                            copyWff = assignSymbol(copyWff, 't', t);

                            if (!processWFF(copyWff)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private static String assignSymbol(String wff, char symbol, int value) {
        return wff.replace(symbol, Character.forDigit(value, 10));
    }

    private static boolean processWFF(String wff) {
        char[] symbols = wff.toCharArray();
        Deque<Boolean> stack = new ArrayDeque<>();

        for (int i = symbols.length - 1; i >= 0; i--) {
            if (symbols[i] == '0') {
                stack.push(false);
            } else if (symbols[i] == '1') {
                stack.push(true);
            } else if (symbols[i] == 'N') {
                boolean variable = stack.pop();
                stack.push(!variable);
            } else {
                boolean variable1 = stack.pop();
                boolean variable2 = stack.pop();

                switch (symbols[i]) {
                    case 'K':
                        stack.push(variable1 && variable2);
                        break;
                    case 'A':
                        stack.push(variable1 || variable2);
                        break;
                    case 'C':
                        boolean result = !(variable1 && (!variable2));
                        stack.push(result);
                        break;
                    case 'E':
                        stack.push(variable1 == variable2);
                        break;
                }
            }
        }
        return stack.pop();
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
