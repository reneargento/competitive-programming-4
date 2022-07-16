package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Created by Rene Argento on 14/07/22.
 */
public class WFFNPROOF {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String symbols = FastReader.getLine();

        while (!symbols.equals("0")) {
            String longestWWF = computeLongestWWF(symbols.toCharArray());
            outputWriter.printLine(longestWWF);
            symbols = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computeLongestWWF(char[] symbols) {
        Deque<Character> wwfDeque = new ArrayDeque<>();
        int numberOfNs = countNs(symbols);

        Arrays.sort(symbols);
        if (Character.isUpperCase(symbols[symbols.length - 1])) {
            return "no WFF possible";
        }

        wwfDeque.addLast(symbols[symbols.length - 1]);
        int variableIndex = symbols.length - 2;
        int operatorIndex = 0;
        while (variableIndex >= 0
                && operatorIndex < symbols.length
                && Character.isLowerCase(symbols[variableIndex])
                && Character.isUpperCase(symbols[operatorIndex])) {
            if (symbols[operatorIndex] == 'N') {
                operatorIndex++;
                continue;
            }

            wwfDeque.addLast(symbols[variableIndex]);
            wwfDeque.addFirst(symbols[operatorIndex]);
            variableIndex--;
            operatorIndex++;
        }
        return buildStringFromDeque(wwfDeque, numberOfNs);
    }

    private static int countNs(char[] symbols) {
        int numberOfNs = 0;
        for (char symbol : symbols) {
            if (symbol == 'N') {
                numberOfNs++;
            }
        }
        return numberOfNs;
    }

    private static String buildStringFromDeque(Deque<Character> wwfDeque, int numberOfNs) {
        StringBuilder wwf = new StringBuilder();
        for (int i = 0; i < numberOfNs; i++) {
            wwf.append("N");
        }

        for (char symbol : wwfDeque) {
            wwf.append(symbol);
        }
        return wwf.toString();
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
