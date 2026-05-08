package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/05/26.
 */
public class InstantComplexity {

    private static class Depth {
        int nPower;
        int constant;

        public Depth(int nPower, int constant) {
            this.nPower = nPower;
            this.constant = constant;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String[] program = readProgram();
            String complexity = computeComplexity(program);
            if (complexity.isEmpty()) {
                complexity = "0";
            }
            outputWriter.printLine(String.format("Program #%d", t));
            outputWriter.printLine("Runtime = " + complexity + "\n");
        }
        outputWriter.flush();
    }

    private static String computeComplexity(String[] program) {
        int[] coefficients = new int[11];
        computeComplexity(program, coefficients, 1, program.length - 1, new Depth(0, 1));

        StringBuilder complexity = new StringBuilder();
        boolean isFirstTerm = true;
        for (int i = 10; i >= 0; i--) {
            if (coefficients[i] >= 1) {
                if (!isFirstTerm) {
                    complexity.append("+");
                }
                if (coefficients[i] == 1 && i == 0) {
                    complexity.append(coefficients[i]);
                } else {
                    if (coefficients[i] > 1) {
                        complexity.append(coefficients[i]);
                        if (i > 0) {
                            complexity.append("*");
                        }
                    }
                    if (i > 0) {
                        complexity.append("n");
                        if (i > 1) {
                            complexity.append("^").append(i);
                        }
                    }
                }
                isFirstTerm = false;
            }
        }
        return complexity.toString();
    }

    private static int computeComplexity(String[] program, int[] coefficients, int startIndex, int endIndex,
                                         Depth depth) {
        for (int i = startIndex; i <= endIndex; i++) {
            String instruction = program[i];
            if (instruction.equals("OP")) {
                int number = Integer.parseInt(program[i + 1]) * depth.constant;
                coefficients[depth.nPower] += number;
                i++;
            } else if (instruction.equals("LOOP")) {
                int loopsOpened = 1;
                int nextEndIndex = i;
                for (int j = i + 2; j <= endIndex; j++) {
                    String nextInstruction = program[j];
                    if (nextInstruction.equals("LOOP")) {
                        loopsOpened++;
                    } else if (nextInstruction.equals("END")) {
                        loopsOpened--;
                        if (loopsOpened == 0) {
                            nextEndIndex = j - 1;
                            break;
                        }
                    }
                }

                Depth nextDepth;
                if (program[i + 1].equals("n")) {
                    nextDepth = new Depth(depth.nPower + 1, depth.constant);
                } else {
                    int constant = Integer.parseInt(program[i + 1]);
                    nextDepth = new Depth(depth.nPower, depth.constant * constant);
                }
                i = computeComplexity(program, coefficients, i + 2, nextEndIndex, nextDepth);
            }
        }
        return endIndex;
    }

    private static String[] readProgram() throws IOException {
        int depth = 1;
        List<String> keywords = new ArrayList<>();
        String currentWord = FastReader.next();
        while (true) {
            keywords.add(currentWord);
            if (currentWord.equals("LOOP")) {
                depth++;
            } else if (currentWord.equals("END")) {
                depth--;
                if (depth == 0) {
                    break;
                }
            }
            currentWord = FastReader.next();
        }
        String[] program = new String[keywords.size()];
        return keywords.toArray(program);
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

        public void flush() {
            writer.flush();
        }
    }
}
