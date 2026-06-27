package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/06/2026.
 */
public class MathWorksheet {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int problems = FastReader.nextInt();

        int testCaseId = 1;
        while (problems != 0) {
            if (testCaseId > 1) {
                outputWriter.printLine();
            }
            List<String> solutions = new ArrayList<>();

            for (int p = 0; p < problems; p++) {
                String line = FastReader.getLine();
                String[] data = line.split(" ");
                int number1 = Integer.parseInt(data[0]);
                int number2 = Integer.parseInt(data[2]);
                String operator = data[1];

                int solutionNumber = solveProblem(number1, operator, number2);
                solutions.add(String.valueOf(solutionNumber));
            }
            printTable(solutions, outputWriter);

            testCaseId++;
            problems = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int solveProblem(int number1, String operator, int number2) {
        if (operator.equals("+")) {
            return number1 + number2;
        } else if (operator.equals("-")) {
            return number1 - number2;
        } else {
            return number1 * number2;
        }
    }

    private static void printTable(List<String> solutions, OutputWriter outputWriter) {
        int fieldWidth = getFieldWidth(solutions);
        StringBuilder line = new StringBuilder();
        String format = "%" + fieldWidth + "s";

        for (String solution : solutions) {
            int separatorSpace = line.length() == 0 ? 0 : 1;
            if (line.length() + separatorSpace + fieldWidth <= 50) {
                if (separatorSpace != 0) {
                    line.append(" ");
                }
            } else {
                outputWriter.printLine(line);
                line = new StringBuilder();
            }
            line.append(String.format(format, solution));
        }
        if (line.length() != 0) {
            outputWriter.printLine(line);
        }
    }

    private static int getFieldWidth(List<String> solutions) {
        int fieldWidth = 0;
        for (String solution : solutions) {
            fieldWidth = Math.max(fieldWidth, solution.length());
        }
        return fieldWidth;
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