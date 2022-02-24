package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/02/22.
 */
public class FindThePermutedString {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        double[] factorial = factorial();

        for (int t = 0; t < tests; t++) {
            char[] characters = FastReader.getLine().toCharArray();
            int index = FastReader.nextInt();
            String permutedString = getPermutedString(characters, factorial, index);
            outputWriter.printLine(permutedString);
        }
        outputWriter.flush();
    }

    private static String getPermutedString(char[] characters, double[] factorial, double index) {
        StringBuilder permutedString = new StringBuilder();
        index--;
        double total = factorial[characters.length];

        for (int i = 0; i < characters.length; i++) {
            total /= (i + 1);
            int position = (int) (index / total);
            permutedString.insert(position, characters[i]);
            index %= total;
        }
        return permutedString.toString();
    }

    private static double[] factorial() {
        double[] factorial = new double[27];
        factorial[0] = 1;
        for (int i = 1; i < factorial.length; i++) {
            factorial[i] = factorial[i - 1] * i;
        }
        return factorial;
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
