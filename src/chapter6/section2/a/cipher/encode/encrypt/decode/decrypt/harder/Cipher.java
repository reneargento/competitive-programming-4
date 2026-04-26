package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/04/26.
 */
public class Cipher {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int n = FastReader.nextInt();
        while (n != 0) {
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                numbers[i] = FastReader.nextInt() - 1;
            }
            String line = FastReader.getLine();
            while (!line.equals("0")) {
                int separatorIndex = getSeparatorIndex(line);
                int k = Integer.parseInt(line.substring(0, separatorIndex));
                String message = line.substring(separatorIndex + 1);
                String encodedMessage = encode(message, numbers, k);

                outputWriter.printLine(encodedMessage);
                line = FastReader.getLine();
            }
            outputWriter.printLine();
            n = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String encode(String messageString, int[] numbers, int k) {
        char[] message = new char[numbers.length];
        System.arraycopy(messageString.toCharArray(), 0, message, 0, messageString.length());
        for (int i = messageString.length(); i < message.length; i++) {
            message[i] = ' ';
        }

        char[] result = new char[numbers.length];
        boolean[] visited = new boolean[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            if (visited[i]) {
                continue;
            }

            // Build cycle
            List<Integer> cycle = new ArrayList<>();
            int current = i;
            while (!visited[current]) {
                visited[current] = true;
                cycle.add(current);
                current = numbers[current];
            }

            int cycleSize = cycle.size();
            int shift = k % cycleSize;
            for (int j = 0; j < cycleSize; j++) {
                int fromIndex = cycle.get(j);
                int toIndex = cycle.get((j + shift) % cycleSize);
                result[toIndex] = message[fromIndex];
            }
        }
        return new String(result);
    }

    private static int getSeparatorIndex(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ') {
                return i;
            }
        }
        return 0;
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
