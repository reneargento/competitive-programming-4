package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/05/21.
 */
public class ILovePizza {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] indexes = createIndexes();

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine();
            int pizzas = countPizzas(line, indexes);
            outputWriter.printLine(pizzas);
        }
        outputWriter.flush();
    }

    private static int countPizzas(String line, int[] indexes) {
        int[] frequency = new int[6];
        int[] ingredientsRequired = {1, 3, 2, 1, 1, 1};

        for (char character : line.toCharArray()) {
            int index = character - 'A';
            if (indexes[index] != -1) {
                frequency[indexes[index]]++;
            }
        }

        int pizzas = Integer.MAX_VALUE;
        for (int i = 0; i < frequency.length; i++) {
            int ingredients = frequency[i] / ingredientsRequired[i];
            pizzas = Math.min(pizzas, ingredients);
        }
        return pizzas;
    }

    private static int[] createIndexes() {
        int[] indexes = new int[26];
        Arrays.fill(indexes, -1);

        indexes['M' - 'A'] = 0;
        indexes[0] = 1;
        indexes['R' - 'A'] = 2;
        indexes['G' - 'A'] = 3;
        indexes['I' - 'A'] = 4;
        indexes['T' - 'A'] = 5;
        return indexes;
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
