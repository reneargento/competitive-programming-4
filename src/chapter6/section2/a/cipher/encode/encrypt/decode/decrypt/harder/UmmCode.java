package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;

/**
 * Created by Rene Argento on 28/04/26.
 */
public class UmmCode {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String message = FastReader.getLine();
        String deUmmCodedMessage = deUmmCodeMessage(message);
        outputWriter.printLine(deUmmCodedMessage);
        outputWriter.flush();
    }

    private static String deUmmCodeMessage(String message) {
        StringBuilder binaryCode = new StringBuilder();
        String[] words = message.split(" ");

        for (String word : words) {
            if (isPartOfUmmCode(word)) {
                for (char symbol : word.toCharArray()) {
                    if (symbol == 'u') {
                        binaryCode.append("1");
                    } else if (symbol == 'm') {
                        binaryCode.append("0");
                    }
                }
            }
        }
        return decodeBinary(binaryCode.toString());
    }

    private static boolean isPartOfUmmCode(String word) {
        for (char symbol : word.toCharArray()) {
            if (Character.isDigit(symbol)
                    || (Character.isLetter(symbol) && symbol != 'u' && symbol != 'm')) {
                return false;
            }
        }
        return true;
    }

    private static String decodeBinary(String bits) {
        StringBuilder deUmmCodedMessage = new StringBuilder();
        for (int i = 0; i < bits.length(); i += 7) {
            String bitSection = bits.substring(i, i + 7);
            char symbol = (char) Integer.parseInt(bitSection, 2);
            deUmmCodedMessage.append(symbol);
        }
        return deUmmCodedMessage.toString();
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
