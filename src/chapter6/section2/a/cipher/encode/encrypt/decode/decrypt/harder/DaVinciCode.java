package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/04/26.
 */
public class DaVinciCode {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        Map<Long, Integer> fibonacciMap = computeFibonacciMap();

        for (int t = 0; t < tests; t++) {
            long[] numbers = new long[FastReader.nextInt()];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = FastReader.nextInt();
            }

            String cipherText = FastReader.getLine();
            String decryptedText = decryptText(numbers, cipherText, fibonacciMap);
            outputWriter.printLine(decryptedText);
        }
        outputWriter.flush();
    }

    private static String decryptText(long[] numbers, String cipherText, Map<Long, Integer> fibonacciMap) {
        String updatedText = removeUnusedChars(cipherText);
        char[] decryptedText = new char[100];
        Arrays.fill(decryptedText, ' ');

        for (int i = 0; i < numbers.length; i++) {
            int index = fibonacciMap.get(numbers[i]);
            decryptedText[index] = updatedText.charAt(i);
        }
        int lastIndex = getLastIndex(decryptedText);
        return new String(decryptedText).substring(0, lastIndex + 1);
    }

    private static Map<Long, Integer> computeFibonacciMap() {
        Map<Long, Integer> fibonacciMap = new HashMap<>();
        long number1 = 1;
        long number2 = 2;
        fibonacciMap.put(number1, 0);
        fibonacciMap.put(number2, 1);
        int index = 2;

        while (number2 < Integer.MAX_VALUE) {
            long aux = number2;
            number2 = number1 + number2;
            number1 = aux;
            fibonacciMap.put(number2, index);
            index++;
        }
        return fibonacciMap;
    }

    private static String removeUnusedChars(String cypherText) {
        StringBuilder clearText = new StringBuilder();
        for (char character : cypherText.toCharArray()) {
            if (Character.isUpperCase(character)) {
                clearText.append(character);
            }
        }
        return clearText.toString();
    }

    private static int getLastIndex(char[] cypherText) {
        for (int i = cypherText.length - 1; i >= 0; i--) {
            if (cypherText[i] != ' ') {
                return i;
            }
        }
        return cypherText.length - 1;
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
