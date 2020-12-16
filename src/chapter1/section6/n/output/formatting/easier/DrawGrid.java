package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 13/12/20.
 */
public class DrawGrid {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int smallestSquares = scanner.nextInt();
        int drawingThickness = scanner.nextInt();
        int gridSize = scanner.nextInt();
        int caseNumber = 1;

        while (smallestSquares != 0 || drawingThickness != 0 || gridSize != 0) {
            outputWriter.printLine("Case " + caseNumber + ":");
            printGrid(smallestSquares, drawingThickness, gridSize, outputWriter);

            smallestSquares = scanner.nextInt();
            drawingThickness = scanner.nextInt();
            gridSize = scanner.nextInt();
            caseNumber++;
            outputWriter.printLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void printGrid(int smallestSquares, int drawingThickness, int gridSize, OutputWriter outputWriter) {
        int lineLength = (drawingThickness + smallestSquares) * gridSize + drawingThickness;
        printLines(lineLength, drawingThickness, outputWriter);

        for (int i = 0; i < gridSize; i++) {
            printLinesWithSquares(smallestSquares, drawingThickness, gridSize, outputWriter);
            printLines(lineLength, drawingThickness, outputWriter);
        }
    }

    private static void printLines(int lineLength, int drawingThickness, OutputWriter outputWriter) {
        for (int line = 0; line < drawingThickness; line++) {
            for (int i = 0; i < lineLength; i++) {
                outputWriter.print("*");
            }
            outputWriter.printLine();
        }
    }

    private static void printLinesWithSquares(int smallestSquares, int drawingThickness, int gridSize,
                                             OutputWriter outputWriter) {
        for (int i = 0; i < smallestSquares; i++) {
            for (int grid = 0; grid < gridSize; grid++) {
                for (int j = 0; j < drawingThickness; j++) {
                    outputWriter.print("*");
                }
                for (int j = 0; j < smallestSquares; j++) {
                    outputWriter.print(".");
                }
            }
            for (int j = 0; j < drawingThickness; j++) {
                outputWriter.print("*");
            }
            outputWriter.printLine();
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
