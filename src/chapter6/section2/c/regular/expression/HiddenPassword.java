package chapter6.section2.c.regular.expression;

import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rene Argento on 11/05/26.
 */
public class HiddenPassword {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String password = FastReader.next();
        String message = FastReader.next();

        String result = checkMessage(password, message);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String checkMessage(String password, String message) {
        StringBuilder patternString = new StringBuilder();
        patternString.append("[^").append(password).append("]*");
        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);
            patternString.append(character);

            if (i != password.length() - 1) {
                String remainingCharacters = password.substring(i + 1);
                patternString.append("[^").append(remainingCharacters).append("]*");
            }
        }
        patternString.append(".*");

        Pattern pattern = Pattern.compile(patternString.toString());
        Matcher matcher = pattern.matcher(message);
        return matcher.matches() ? "PASS" : "FAIL";
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
