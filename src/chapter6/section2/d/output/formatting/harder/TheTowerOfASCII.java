package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 25/06/2026.
 */
public class TheTowerOfASCII {

    private static class PieceSizes {
        List<Integer> leftPieces;
        List<Integer> rightPieces;

        public PieceSizes(List<Integer> leftPieces, List<Integer> rightPieces) {
            this.leftPieces = leftPieces;
            this.rightPieces = rightPieces;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int towerId = 1;
        while (line != null) {
            int height = Integer.parseInt(line);
            outputWriter.printLine(String.format("Tower #%d", towerId));
            printToa(height, outputWriter);

            towerId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printToa(int height, OutputWriter outputWriter) {
        PieceSizes pieceSizes = computePieceSizes(height);
        List<Integer> leftPieces = pieceSizes.leftPieces;
        List<Integer> rightPieces = pieceSizes.rightPieces;
        int leftSideIndex = 0;
        int rightSideIndex = 0;
        int leftSideCurrentPieceHeight = 0;
        int rightSideCurrentPieceHeight = 0;

        for (int i = 0; i < height; i++) {
            int leadingSpaces = (leftPieces.size() - leftSideIndex - 1) * 2;
            for (int s = 0; s < leadingSpaces; s++) {
                outputWriter.print(" ");
            }
            outputWriter.print("#");

            if (i == 0) {
                for (int p = 0; p < 4; p++) {
                    outputWriter.print("*");
                }
            } else {
                int internalDots = 4 + (leftSideIndex * 2) + (rightSideIndex * 2);
                if (leftSideCurrentPieceHeight == 0) {
                    outputWriter.print("**");
                    internalDots -= 2;
                }
                if (rightSideCurrentPieceHeight == 0) {
                    internalDots -= 2;
                }

                for (int d = 0; d < internalDots; d++) {
                    outputWriter.print(".");
                }
                if (rightSideCurrentPieceHeight == 0) {
                    outputWriter.print("**");
                }
            }
            outputWriter.printLine("#");

            leftSideCurrentPieceHeight++;
            rightSideCurrentPieceHeight++;
            if (leftSideCurrentPieceHeight == leftPieces.get(leftSideIndex)) {
                leftSideIndex++;
                leftSideCurrentPieceHeight = 0;
            }
            if (rightSideCurrentPieceHeight == rightPieces.get(rightSideIndex)) {
                rightSideIndex++;
                rightSideCurrentPieceHeight = 0;
            }
        }
        outputWriter.printLine();
    }

    private static PieceSizes computePieceSizes(int height) {
        int piecesRequiredLeftSide = computePiecesRequired(height);

        List<Integer> leftPieces = computePieceSizes(height, piecesRequiredLeftSide);
        List<Integer> rightPieces = computePieceSizes(height, piecesRequiredLeftSide - 1);
        return new PieceSizes(leftPieces, rightPieces);
    }

    private static int computePiecesRequired(int height) {
        int piecesRequired = 0;
        int total = 0;
        int increment = 1;

        while (total + increment <= height) {
            piecesRequired++;
            total += increment;
            increment++;
        }

        if (total > height) {
            piecesRequired--;
        }
        return piecesRequired;
    }

    private static List<Integer> computePieceSizes(int height, int piecesNumber) {
        List<Integer> pieceSizes = new ArrayList<>();
        int total = 0;
        int increment = 1;

        for (int i = 0; i < piecesNumber; i++) {
            pieceSizes.add(increment);
            total += increment;
            increment++;
        }

        if (total < height) {
            int valueToAdd = height - total;
            int lastIndex = pieceSizes.size() - 1;
            int lastValue = pieceSizes.get(lastIndex);
            pieceSizes.set(lastIndex, lastValue + valueToAdd);
        }
        return pieceSizes;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void flush() {
            writer.flush();
        }
    }
}