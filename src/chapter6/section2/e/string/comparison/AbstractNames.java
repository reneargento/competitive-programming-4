package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/06/2026.
 */
public class AbstractNames {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String name1 =  FastReader.next();
            String name2 = FastReader.next();
            String result = areTheSame(name1, name2);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String areTheSame(String name1, String name2) {
        if (name1.length() != name2.length()) {
            return "No";
        }
        for (int i = 0; i < name1.length(); i++) {
            char character1 = name1.charAt(i);
            char character2 = name2.charAt(i);

            if (character1 != character2
                    && isConsonant(character1)) {
                return "No";
            }
        }
        return "Yes";
    }

    private static boolean isConsonant(char character) {
        return character != 'a' && character != 'e' && character != 'i' && character != 'o' && character != 'u';
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