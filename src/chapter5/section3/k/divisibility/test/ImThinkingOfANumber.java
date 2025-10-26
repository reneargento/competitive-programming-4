package chapter5.section3.k.divisibility.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/10/25.
 */
public class ImThinkingOfANumber {

    private static class Rule {
        int type;
        int value;

        public Rule(int type, int value) {
            this.type = type;
            this.value = value;
        }
    }

    private static final int LESS = 1;
    private static final int GREATER = 2;
    private static final int DIVISIBLE = 3;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rulesNumber = FastReader.nextInt();

        while (rulesNumber != 0) {
            Rule[] rules = new Rule[rulesNumber];
            for (int i = 0; i < rules.length; i++) {
                String[] data = FastReader.getLine().split(" ");

                int type;
                if (data[0].equals("less")) {
                    type = LESS;
                } else if (data[0].equals("greater")) {
                    type = GREATER;
                } else {
                    type = DIVISIBLE;
                }
                rules[i] = new Rule(type, Integer.parseInt(data[2]));
            }

            List<Integer> values = computeConsistentValues(rules);
            if (values == null) {
                outputWriter.printLine("infinite");
            } else if (values.isEmpty()) {
                outputWriter.printLine("none");
            } else {
                outputWriter.print(values.get(0));
                for (int i = 1; i < values.size(); i++) {
                    outputWriter.print(" " + values.get(i));
                }
                outputWriter.printLine();
            }
            rulesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeConsistentValues(Rule[] rules) {
        List<Integer> values = new ArrayList<>();
        int lowerBound = 1;
        int upperBound = Integer.MAX_VALUE;
        List<Integer> divisible = new ArrayList<>();

        for (Rule rule : rules) {
            if (rule.type == LESS) {
                upperBound = Math.min(upperBound, rule.value - 1);
            } else if (rule.type == GREATER) {
                lowerBound = Math.max(lowerBound, rule.value + 1);
            } else {
                divisible.add(rule.value);
            }
        }

        if (upperBound == Integer.MAX_VALUE) {
            return null;
        }

        for (int numberCandidate = lowerBound; numberCandidate <= upperBound; numberCandidate++) {
            boolean divisibleByAll = true;
            for (int divisibleTest : divisible) {
                if (numberCandidate % divisibleTest != 0) {
                    divisibleByAll = false;
                    break;
                }
            }

            if (divisibleByAll) {
                values.add(numberCandidate);
            }
        }
        return values;
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
