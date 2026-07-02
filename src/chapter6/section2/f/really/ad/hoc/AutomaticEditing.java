package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/07/2026.
 */
public class AutomaticEditing {

    private static class Rule {
        String findString;
        String replaceByString;

        public Rule(String findString, String replaceByString) {
            this.findString = findString;
            this.replaceByString = replaceByString;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int rulesNumber = FastReader.nextInt();
        while (rulesNumber != 0) {
            Rule[] rules = new Rule[rulesNumber];
            for (int i = 0; i < rulesNumber; i++) {
                rules[i] = new Rule(FastReader.getLine(), FastReader.getLine());
            }
            String text = FastReader.getLine();
            String editedText = editText(rules, text);
            outputWriter.printLine(editedText);
            rulesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String editText(Rule[] rules, String text) {
        String editedText = text;
        for (Rule rule : rules) {
            boolean ruleFinished = false;

            while (!ruleFinished) {
                String newText = editedText.replaceFirst(rule.findString, rule.replaceByString);
                if (newText.equals(editedText)) {
                    ruleFinished  = true;
                }
                editedText = newText;
            }
        }
        return editedText;
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