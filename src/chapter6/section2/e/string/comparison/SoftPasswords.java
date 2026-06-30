package chapter6.section2.e.string.comparison;

import java.io.*;

/**
 * Created by Rene Argento on 30/06/2026.
 */
public class SoftPasswords {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String storedPassword = FastReader.getLine();
        String enteredPassword = FastReader.getLine();
        String result = shouldAccept(storedPassword, enteredPassword);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String shouldAccept(String storedPassword, String enteredPassword) {
        if (storedPassword.equals(enteredPassword)) {
            return "Yes";
        }
        for (char digit = '0'; digit <= '9'; digit++) {
            String prependedPassword = digit + enteredPassword;
            String appendedPassword = enteredPassword + digit;
            if (prependedPassword.equals(storedPassword) || appendedPassword.equals(storedPassword)) {
                return "Yes";
            }
        }

        StringBuilder reverseCasePassword = new StringBuilder();
        for (char character : enteredPassword.toCharArray()) {
            char newCharacter;
            if (Character.isUpperCase(character)) {
                newCharacter = Character.toLowerCase(character);
            } else {
                newCharacter = Character.toUpperCase(character);
            }
            reverseCasePassword.append(newCharacter);
        }
        return reverseCasePassword.toString().equals(storedPassword) ? "Yes" : "No";
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