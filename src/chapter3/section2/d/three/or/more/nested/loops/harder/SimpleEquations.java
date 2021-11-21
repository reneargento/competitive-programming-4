package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/11/21.
 */
public class SimpleEquations {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int a = FastReader.nextInt();
            int b = FastReader.nextInt();
            int c = FastReader.nextInt();

            int[] solution = solveEquation(a, b, c);
            if (solution == null) {
                outputWriter.printLine("No solution.");
            } else {
                outputWriter.printLine(solution[0] + " " + solution[1] + " " + solution[2]);
            }
        }
        outputWriter.flush();
    }

    private static int[] solveEquation(int a, int b, int c) {
        int maxNumber = 10000;

        for (int x = -100; x <= 100; x++) {
            int xSquared = x * x;
            if (x > 0 && xSquared * x > maxNumber) {
                break;
            }

            for (int y = x + 1; y <= 100; y++) {
                int ySquared = y * y;
                if (y > 0 && x * ySquared > maxNumber) {
                    break;
                }

                int z = a - x - y;
                if (z != x && z != y
                        && x * y * z == b
                        && xSquared + ySquared + (z * z) == c) {
                    return new int[]{ x , y , z };
                }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
