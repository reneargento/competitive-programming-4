package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/01/25.
 */
public class GrowingRectangularSpiral {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            int xCoordinate = FastReader.nextInt();
            int yCoordinate = FastReader.nextInt();

            outputWriter.print(dataSetNumber + " ");
            if ((xCoordinate == yCoordinate && xCoordinate <= 3)
                    || (xCoordinate > yCoordinate && (xCoordinate < 3 || yCoordinate < 4))) {
                outputWriter.printLine("NO PATH");
            } else if (xCoordinate < yCoordinate) {
                outputWriter.printLine(2 + " " + xCoordinate + " " + yCoordinate);
            } else {
                int rightMoveLength = xCoordinate + 2;
                int downMoveLength = Math.min(rightMoveLength - 1, rightMoveLength - yCoordinate + 2 + 1);
                int upMoveLength = Math.max(downMoveLength + 2, rightMoveLength + 1);
                outputWriter.printLine(6 + " 1 2 3 " + downMoveLength + " " + rightMoveLength +
                        " " + upMoveLength);
            }
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
