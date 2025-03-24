package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/03/25.
 */
public class TheCatInTheHat {

    private static class Result {
        int notWorkingCats;
        int stackHeight;

        public Result(int notWorkingCats, int stackHeight) {
            this.notWorkingCats = notWorkingCats;
            this.stackHeight = stackHeight;
        }
    }

    private static final double EPSILON = .000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int initialCatHeight = FastReader.nextInt();
        int workerCats = FastReader.nextInt();

        while (initialCatHeight != 0 || workerCats != 0) {
            Result result = computeCats(initialCatHeight, workerCats);
            outputWriter.printLine(result.notWorkingCats + " " + result.stackHeight);

            initialCatHeight = FastReader.nextInt();
            workerCats = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeCats(int initialCatHeight, int workerCats) {
        for (int power = 1; power <= workerCats; power++) {
            int totalCats = 1;
            double stackHeight = initialCatHeight;

            int currentCatsInLevel = 1;
            double currentHeight = initialCatHeight;
            while (currentHeight > 1) {
                int nextCatsInLevel = currentCatsInLevel * power;
                currentHeight = 1 / (power + 1.0) * currentHeight;

                stackHeight += currentHeight * nextCatsInLevel;
                totalCats += nextCatsInLevel;
                currentCatsInLevel = nextCatsInLevel;
            }

            if (1 - currentHeight <= EPSILON && currentCatsInLevel == workerCats) {
                int notWorkingCats = totalCats - workerCats;
                return new Result(notWorkingCats, (int) stackHeight);
            }
        }
        return null;
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
