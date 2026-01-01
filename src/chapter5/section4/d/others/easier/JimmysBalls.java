package chapter5.section4.d.others.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/12/25.
 */
public class JimmysBalls {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int balls = FastReader.nextInt();
        int caseId = 1;
        while (balls != 0) {
            long combinations = computeCombinations(balls);
            outputWriter.printLine(String.format("Case %d: %d", caseId, combinations));

            caseId++;
            balls = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeCombinations(int balls) {
        long combinations = 0;

        for (int redBalls = balls / 3; redBalls >= 1; redBalls--) {
            int blueBalls = redBalls + 1;
            int greenBalls = blueBalls + 1;

            if (redBalls + blueBalls + greenBalls <= balls) {
                greenBalls--;
                combinations += (balls - redBalls - blueBalls - greenBalls + 1) / 2;
            }
        }
        return combinations;
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
