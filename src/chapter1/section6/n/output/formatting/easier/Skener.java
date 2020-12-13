package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 12/12/20.
 */
public class Skener {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        int zoomedRows = scanner.nextInt();
        int zoomedColumns = scanner.nextInt();
        scanner.nextLine();

        char[][] article = new char[rows][columns];
        for (int row = 0; row < rows; row++) {
            article[row] = scanner.nextLine().toCharArray();
        }

        char[][] enlargedArticle = getEnlargedArticle(rows, columns, zoomedRows, zoomedColumns, article);
        printEnlargedArticle(enlargedArticle);
    }

    private static char[][] getEnlargedArticle(int rows, int columns, int zoomedRows, int zoomedColumns, char[][] article) {
        char[][] enlargedArticle = new char[rows * zoomedRows][columns * zoomedColumns];

        int enlargedRow;
        int enlargedColumn;

        for (int row = 0; row < article.length; row++) {
            enlargedRow = row * zoomedRows;
            enlargedColumn = 0;

            for (int column = 0; column < article[0].length; column++) {
                char symbol = article[row][column];

                for (int zr = enlargedRow; zr < enlargedRow + zoomedRows; zr++) {
                    for (int zc = enlargedColumn; zc < enlargedColumn + zoomedColumns; zc++) {
                        enlargedArticle[zr][zc] = symbol;
                    }
                }

                enlargedColumn += zoomedColumns;
            }
        }

        return enlargedArticle;
    }

    private static void printEnlargedArticle(char[][] enlargedArticle) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        for (int row = 0; row < enlargedArticle.length; row++) {
            for (int column = 0; column < enlargedArticle[0].length; column++) {
                outputWriter.print(enlargedArticle[row][column]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++)
            {
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
