package chapter2.section4.a.graph.data.structures.problems;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/07/21.
 */
@SuppressWarnings("unchecked")
public class MatrixTranspose {

    private static class ElementData {
        int columnIndex;
        int value;

        public ElementData(int columnIndex, int value) {
            this.columnIndex = columnIndex;
            this.value = value;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int rows = Integer.parseInt(data[0]);
            int columns = Integer.parseInt(data[1]);

            List<ElementData>[] transposedRows = new List[columns + 1];
            for (int row = 0; row < rows; row++) {
                int elements = FastReader.nextInt();
                int[] columnIndexes = new int[elements];
                int[] values = new int[elements];

                for (int i = 0; i < elements; i++) {
                    columnIndexes[i] = FastReader.nextInt();
                }
                for (int i = 0; i < elements; i++) {
                    values[i] = FastReader.nextInt();
                }

                for (int i = 0; i < elements; i++) {
                    int transposedRow = columnIndexes[i];
                    if (transposedRows[transposedRow] == null) {
                        transposedRows[transposedRow] = new ArrayList<>();
                    }
                    ElementData elementData = new ElementData(row + 1, values[i]);
                    transposedRows[transposedRow].add(elementData);
                }
            }

            outputWriter.printLine(columns + " " + rows);
            printTransposedMatrix(transposedRows, outputWriter);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printTransposedMatrix(List<ElementData>[] transposedRows, OutputWriter outputWriter) {
        for (int row = 1; row < transposedRows.length; row++) {
            if (transposedRows[row] == null) {
                outputWriter.printLine(0);
                outputWriter.printLine();
                continue;
            }
            int elements = transposedRows[row].size();
            outputWriter.print(elements);

            for (int column = 0; column < elements; column++) {
                if (column == 0) {
                    outputWriter.print(" ");
                }

                ElementData elementData = transposedRows[row].get(column);
                outputWriter.print(elementData.columnIndex);

                if (column != elements - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();

            for (int column = 0; column < elements; column++) {
                ElementData elementData = transposedRows[row].get(column);
                outputWriter.print(elementData.value);

                if (column != elements - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
