package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/12/24.
 */
public class CatsWithOrWithoutHats {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int catsInHats = FastReader.nextInt();
        int catsWithoutHats = FastReader.nextInt();

        while (catsInHats != 0) {
            // Tree nodes = (degree * leaves - 1) / (degree - 1)
            double totalCatsDouble = (catsInHats * catsWithoutHats - 1) / (double) (catsInHats - 1);
            long totalCats = (long) totalCatsDouble;

            outputWriter.print(catsInHats + " " + catsWithoutHats + " ");
            if (catsInHats == 1 && catsWithoutHats == 1) {
                outputWriter.printLine("Multiple");
            } else if (totalCatsDouble == totalCats) {
                outputWriter.printLine(totalCats);
            } else {
                outputWriter.printLine("Impossible");
            }
            catsInHats = FastReader.nextInt();
            catsWithoutHats = FastReader.nextInt();
        }
        outputWriter.flush();
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
