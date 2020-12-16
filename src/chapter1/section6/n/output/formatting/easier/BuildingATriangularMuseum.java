package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 13/12/20.
 */
public class BuildingATriangularMuseum {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int size = scanner.nextInt();
        int levels = scanner.nextInt();
        int setNumber = 1;

        while (size != 0 || levels != 0) {
            outputWriter.printLine("Triangular Museum " + setNumber);
            architectMuseum(size, levels, outputWriter);
            outputWriter.printLine();

            size = scanner.nextInt();
            levels = scanner.nextInt();
            setNumber++;
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void architectMuseum(int size, int levels, OutputWriter outputWriter) {
        int paddingSize = size * (levels - 1) + size - 1;
        StringBuilder padding = getPadding(paddingSize);

        for (int l = 1; l <= levels; l++) {
            for (int s = 0; s < size; s++) {
                outputWriter.print(padding);

                for (int triangle = 1; triangle <= l; triangle++) {
                    outputWriter.print("/");
                    char internalSymbol = s < size - 1 ? ' ' : '_';
                    int internalSymbolSize = 2 * s;

                    printSymbols(internalSymbolSize, internalSymbol, outputWriter);
                    outputWriter.print("\\");

                    if (triangle != l) {
                        int spacesBetweenTriangles = (size - s - 1) * 2;
                        printSymbols(spacesBetweenTriangles, ' ', outputWriter);
                    } else {
                        outputWriter.printLine();
                    }
                }
                if (padding.length() > 0) {
                    padding.deleteCharAt(padding.length() - 1);
                }
            }
        }
    }

    private static StringBuilder getPadding(int paddingSize) {
        StringBuilder padding = new StringBuilder();

        for (int i = 0; i < paddingSize; i++) {
            padding.append(" ");
        }
        return padding;
    }

    private static void printSymbols(int length, char symbol, OutputWriter outputWriter) {
        for (int i = 0; i < length; i++) {
            outputWriter.print(symbol);
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
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
