package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/02/22.
 */
public class TheDominoesSolitaire {

    private static class Piece {
        int left;
        int right;

        public Piece(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int spaces = FastReader.nextInt();

        while (spaces != 0) {
            int piecesNumber = FastReader.nextInt();
            Piece startPiece = new Piece(FastReader.nextInt(), FastReader.nextInt());
            Piece endPiece = new Piece(FastReader.nextInt(), FastReader.nextInt());
            Piece[] pieces = new Piece[piecesNumber];
            for (int i = 0; i < pieces.length; i++) {
                pieces[i] = new Piece(FastReader.nextInt(), FastReader.nextInt());
            }

            boolean hasSolution = hasSolution(spaces, pieces, endPiece, startPiece, 0, 0);
            outputWriter.printLine(hasSolution ? "YES" : "NO");
            spaces = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean hasSolution(int spaces, Piece[] pieces, Piece endPiece, Piece previousPiece,
                                       int spaceIndex, int piecesUsedMask) {
        if (spaceIndex == spaces) {
            return true;
        }

        for (int i = 0; i < pieces.length; i++) {
            if ((piecesUsedMask & (1 << i)) == 0) {
                Piece rotatedPiece = new Piece(pieces[i].right, pieces[i].left);

                boolean matchesRightSide = true;
                boolean matchesRightSideWithRotation = true;
                if (spaceIndex == spaces - 1) {
                    matchesRightSide = match(pieces[i], endPiece);
                    matchesRightSideWithRotation = match(rotatedPiece, endPiece);
                }

                int newMask = piecesUsedMask | (1 << i);
                if (match(previousPiece, pieces[i]) && matchesRightSide) {
                    boolean hasSolution = hasSolution(spaces, pieces, endPiece, pieces[i],
                            spaceIndex + 1, newMask);
                    if (hasSolution) {
                        return true;
                    }
                }
                if (match(previousPiece, rotatedPiece) && matchesRightSideWithRotation) {
                    boolean hasSolution = hasSolution(spaces, pieces, endPiece, rotatedPiece,
                            spaceIndex + 1, newMask);
                    if (hasSolution) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean match(Piece leftPiece, Piece rightPiece) {
        return leftPiece.right == rightPiece.left;
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
