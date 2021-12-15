package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/12/21.
 */
public class GradeCurving {

    private static class Result {
        int minimumK;
        int maximumK;

        public Result(int minimumK, int maximumK) {
            this.minimumK = minimumK;
            this.maximumK = maximumK;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numberX = FastReader.nextInt();
        int minY = FastReader.nextInt();
        int maxY = FastReader.nextInt();

        Result result = computeGradeCurve(numberX, minY, maxY);
        if (result.minimumK == -1) {
            outputWriter.printLine("impossible");
        } else {
            outputWriter.print(result.minimumK + " ");
            if (result.maximumK == Integer.MAX_VALUE) {
                outputWriter.printLine("inf");
            } else {
                outputWriter.printLine(result.maximumK);
            }
        }
        outputWriter.flush();
    }

    private static Result computeGradeCurve(int numberX, int minY, int maxY) {
        int minimumK = Integer.MAX_VALUE;
        int maximumK = -1;

        boolean possible = false;
        boolean infinite = false;
        double previousFinalResult = -1;

        double finalResult = numberX;
        double result = numberX;

        for (int k = 0; k <= 100; k++) {
            boolean isInsideRange = false;

            if (minY <= finalResult && finalResult <= maxY) {
                possible = true;
                isInsideRange = true;
                if (k < minimumK) {
                    minimumK = k;
                }
                if (k > maximumK) {
                    maximumK = k;
                }
            }

            result = 10 * Math.sqrt(result);
            finalResult = Math.ceil(result);

            if (finalResult == previousFinalResult && isInsideRange) {
                infinite = true;
            }
            previousFinalResult = finalResult;
        }

        if (!possible) {
            minimumK = -1;
        } else if (infinite) {
            maximumK = Integer.MAX_VALUE;
        }
        return new Result(minimumK, maximumK);
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
