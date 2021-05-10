package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/05/21.
 */
public class Newspaper {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Map<Character, Integer> characterMap = new HashMap<>();

            int mappings = FastReader.nextInt();
            for (int i = 0; i < mappings; i++) {
                char character = FastReader.next().charAt(0);
                int value = FastReader.nextInt();
                characterMap.put(character, value);
            }

            double price = 0;
            int articleLines = FastReader.nextInt();
            for (int i = 0; i < articleLines; i++) {
                String line = FastReader.getLine();
                price += computeLinePrice(line, characterMap);
            }
            price /= 100;
            outputWriter.printLine(String.format("%.2f$", price));
        }
        outputWriter.flush();
    }

    private static long computeLinePrice(String line, Map<Character, Integer> characterMap) {
        long price = 0;

        for (char character : line.toCharArray()) {
            if (characterMap.containsKey(character)) {
                price += characterMap.get(character);
            }
        }
        return price;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
