package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/07/2026.
 */
public class ToLower {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int problems = FastReader.nextInt();
        int tests = FastReader.nextInt();
        int passedProblems = 0;

        for (int p = 0; p < problems; p++) {
            boolean canPassProblem = true;

            for (int t = 0; t < tests; t++) {
                String string = FastReader.next();
                if (containsUppercase(string)) {
                    canPassProblem = false;
                }
            }

            if (canPassProblem) {
                passedProblems++;
            }
        }
        outputWriter.printLine(passedProblems);
        outputWriter.flush();
    }

    private static boolean containsUppercase(String string) {
        for (int i = 1; i < string.length(); i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                return true;
            }
        }
        return false;
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