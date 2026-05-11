package chapter6.section2.c.regular.expression;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rene Argento on 10/05/26.
 */
public class IdentifyingLegalPascalRealConstants {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String constantCandidate = FastReader.getLine();
        while (!constantCandidate.equals("*")) {
            String trimmedCandidate = constantCandidate.trim();
            boolean isLegal = isLegal(trimmedCandidate);
            outputWriter.print(trimmedCandidate + " is ");
            outputWriter.printLine(isLegal ? "legal." : "illegal.");
            constantCandidate = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isLegal(String constantCandidate) {
        String digits = "[+-]?\\d+";
        String exponent = "(([eE][+-]?)\\d+)";
        Pattern pattern = Pattern.compile(digits + "((\\.\\d+(" + exponent + ")?)|" + exponent + ")");
        Matcher matcher = pattern.matcher(constantCandidate);
        return matcher.matches();
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

        public void flush() {
            writer.flush();
        }
    }
}
