package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/04/26.
 */
public class ProgressiveScramble {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine();
            char method = line.charAt(0);
            String message = line.substring(2);

            String result = processMessage(method, message);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String processMessage(char method, String message) {
        if (method == 'e') {
            return encrypt(message);
        } else {
            return decrypt(message);
        }
    }

    private static String encrypt(String message) {
        int[] values = new int[message.length()];
        for (int i = 0; i < message.length(); i++) {
            values[i] = getSymbolValue(message.charAt(i));
        }

        int[] uValues = new int[values.length];
        uValues[0] = values[0];
        for (int i = 1; i < uValues.length; i++) {
            uValues[i] = (values[i] + uValues[i - 1]) % 27;
        }

        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < uValues.length; i++) {
            char symbol = getSymbol(uValues[i]);
            encryptedMessage.append(symbol);
        }
        return encryptedMessage.toString();
    }

    private static String decrypt(String message) {
        int[] uValues = new int[message.length()];
        int[] values = new int[message.length()];
        values[0] = getSymbolValue(message.charAt(0));
        uValues[0] = values[0];

        for (int i = 1; i < values.length; i++) {
            int symbolValue = getSymbolValue(message.charAt(i));
            values[i] = mod(symbolValue - uValues[i - 1], 27);
            uValues[i] = uValues[i - 1] + values[i];
        }

        StringBuilder decryptedMessage = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            char symbol = getSymbol(values[i]);
            decryptedMessage.append(symbol);
        }
        return decryptedMessage.toString();
    }

    private static int getSymbolValue(char symbol) {
        if (symbol == ' ') {
            return 0;
        }
        return symbol - 'a' + 1;
    }

    private static char getSymbol(int value) {
        if (value == 0) {
            return ' ';
        }
        return (char) ('a' + value - 1);
    }

    private static int mod(int number, int mod) {
        return ((number % mod) + mod) % mod;
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
