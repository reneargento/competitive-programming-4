package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/05/26.
 */
public class Slurpys {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        outputWriter.printLine("SLURPYS OUTPUT");
        for (int t = 0; t < tests; t++) {
            String string = FastReader.getLine();
            String result = checkSlurpy(string);
            outputWriter.printLine(result);
        }
        outputWriter.printLine("END OF OUTPUT");
        outputWriter.flush();
    }

    private static String checkSlurpy(String string) {
        char[] tokens = string.toCharArray();
        int slimpResult = parseSlimp(tokens, 0);
        if (slimpResult == -1) {
            return "NO";
        }
        int slumpResult = parseSlump(tokens, true, slimpResult);
        return slumpResult == string.length() ? "YES" : "NO";
    }

    private static int parseSlump(char[] tokens, boolean isStart, int index) {
        if (index == tokens.length) {
            return -1;
        }
        char token = tokens[index];
        if (isStart) {
            if ((token != 'D' && token != 'E')
                    || index == tokens.length - 1) {
                return -1;
            }
            if (tokens[index + 1] != 'F') {
                return -1;
            }
            return parseSlump(tokens, false, index + 1);
        } else {
            if (tokens[index] == 'F') {
                if (index == tokens.length - 1) {
                    return -1;
                }
                if (tokens[index + 1] == 'F') {
                    return parseSlump(tokens, false, index + 1);
                } else if (tokens[index + 1] == 'G') {
                    return index + 2;
                } else if (tokens[index + 1] == 'D' || tokens[index + 1] == 'E') {
                    return parseSlump(tokens, true, index + 1);
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }
    }

    private static int parseSlimp(char[] tokens, int index) {
        if (index == tokens.length) {
            return -1;
        }
        char token = tokens[index];
        if (token != 'A' || index == tokens.length - 1) {
            return -1;
        } else if (tokens[index + 1] == 'H') {
            return index + 2;
        } else {
            if (tokens[index + 1] == 'B') {
                int slimpResult = parseSlimp(tokens, index + 2);
                if (slimpResult == -1
                        || slimpResult == tokens.length
                        || tokens[slimpResult] != 'C') {
                    return -1;
                }
                return slimpResult + 1;
            } else {
                int slumpResult = parseSlump(tokens, true, index + 1);
                if (slumpResult == -1
                        || slumpResult == tokens.length
                        || tokens[slumpResult] != 'C') {
                    return -1;
                }
                return slumpResult + 1;
            }
        }
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
