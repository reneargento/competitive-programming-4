package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/12/24.
 */
public class ProblemWithARidiculouslyLongNameButWithARidiculouslyShortDescription {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] results = { 56, 96, 36, 76, 16 };

        int tests = FastReader.nextInt();
        for (int t = 0; t < tests; t++) {
            String n = FastReader.next();

            int result = computeResult(n, results);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static int computeResult(String n, int[] results) {
        if (n.equals("0")) {
            return 1;
        } else if (n.equals("1")) {
            return 66;
        }

        int lastDigit = n.charAt(n.length() - 1) - '0';
        int resultIndex = (lastDigit + 3) % 5;
        return results[resultIndex];
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
