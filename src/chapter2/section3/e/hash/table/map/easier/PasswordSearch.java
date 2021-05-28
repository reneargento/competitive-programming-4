package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 28/05/21.
 */
public class PasswordSearch {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int passwordLength = Integer.parseInt(data[0]);
            String encodedMessage;

            if (data.length == 2) {
                encodedMessage = data[1];
            } else {
                encodedMessage = FastReader.getLine();
            }

            String password = getPassword(passwordLength, encodedMessage);
            outputWriter.printLine(password);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String getPassword(int passwordLength, String encodedText) {
        if (passwordLength > encodedText.length()) {
            return "";
        }
        Map<String, Integer> wordFrequencies = new HashMap<>();

        StringBuilder passwordCandidates = new StringBuilder(encodedText.substring(0, passwordLength));
        wordFrequencies.put(passwordCandidates.toString(), 1);

        int highestFrequency = 1;
        String password = passwordCandidates.toString();

        for (int i = passwordLength; i < encodedText.length(); i++) {
            passwordCandidates.deleteCharAt(0);
            passwordCandidates.append(encodedText.charAt(i));

            String passwordCandidate = passwordCandidates.toString();
            int frequency = wordFrequencies.getOrDefault(passwordCandidate, 0);
            frequency++;
            wordFrequencies.put(passwordCandidate, frequency);

            if (frequency > highestFrequency) {
                highestFrequency = frequency;
                password = passwordCandidate;
            }
        }
        return password;
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
