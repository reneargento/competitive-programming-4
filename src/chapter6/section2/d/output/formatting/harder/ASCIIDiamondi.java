package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/06/2026.
 */
public class ASCIIDiamondi {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int sideLength = FastReader.nextInt();
        int row1 = FastReader.nextInt();
        int column1 = FastReader.nextInt();
        int row2 = FastReader.nextInt();
        int column2 = FastReader.nextInt();
        int caseId = 1;

        while (sideLength != 0) {
            outputWriter.printLine(String.format("Case %d:", caseId));
            printDiamond(sideLength, row1, column1, row2, column2, outputWriter);

            caseId++;
            sideLength = FastReader.nextInt();
            row1 = FastReader.nextInt();
            column1 = FastReader.nextInt();
            row2 = FastReader.nextInt();
            column2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void printDiamond(int sideLength, int row1, int column1, int row2, int column2,
                                     OutputWriter outputWriter) {
        int blockSize = 2 * sideLength - 1;

        for (int row = row1; row <= row2; row++) {
            for (int column = column1; column <= column2; column++) {
                int adjustedRow = row % blockSize;
                int adjustedColum = column % blockSize;
                char symbol = getChar(sideLength, adjustedRow, adjustedColum);
                outputWriter.print(symbol);
            }
            outputWriter.printLine();
        }
    }

    private static char getChar(int sideLength, int row, int column) {
        int diamondCenter = sideLength - 1;
        int distance = Math.abs(row - diamondCenter) + Math.abs(column - diamondCenter);
        if (distance >= sideLength) {
            return '.';
        }
        return (char) ('a' + (distance % 26));
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