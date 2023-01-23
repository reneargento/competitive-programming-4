package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/01/23.
 */
public class TheBookShelversProblem {

    private static class Book {
        double height;
        double width;

        public Book(double height, double width) {
            this.height = height;
            this.width = width;
        }
    }

    private static final double EPSILON = 0.000000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int booksNumber = FastReader.nextInt();
        double totalWidth = FastReader.nextDouble();

        while (booksNumber != 0 || totalWidth != 0) {
            Book[] books = new Book[booksNumber];
            for (int i = 0; i < books.length; i++) {
                double height = FastReader.nextDouble();
                double width = FastReader.nextDouble();
                books[i] = new Book(height, width);
            }
            double minimumHeight = computeMinimumHeight(books, totalWidth);
            outputWriter.printLine(String.format("%.4f", minimumHeight));

            booksNumber = FastReader.nextInt();
            totalWidth = FastReader.nextDouble();
        }
        outputWriter.flush();
    }

    private static double computeMinimumHeight(Book[] books, double totalWidth) {
        // dp[bookID] = minimum height
        double[] dp = new double[books.length];
        Arrays.fill(dp, -1);
        return computeMinimumHeight(books, dp, totalWidth, 0);
    }

    private static double computeMinimumHeight(Book[] books, double[] dp, double totalWidth, int bookID) {
        if (bookID == dp.length) {
            return 0;
        }
        if (dp[bookID] != -1) {
            return dp[bookID];
        }

        double currentHeight = books[bookID].height;
        double currentWidth = books[bookID].width;
        double minimumHeight = Double.POSITIVE_INFINITY;

        for (int nextBookID = bookID + 1; nextBookID <= books.length; nextBookID++) {
            double height = computeMinimumHeight(books, dp, totalWidth, nextBookID) + currentHeight;
            minimumHeight = Math.min(minimumHeight, height);

            if (nextBookID == books.length) {
                break;
            }
            Book nextBook = books[nextBookID];
            currentWidth += nextBook.width;
            if ((currentWidth - totalWidth) <= EPSILON) {
                currentHeight = Math.max(currentHeight, nextBook.height);
            } else {
                break;
            }
        }
        dp[bookID] = minimumHeight;
        return dp[bookID];
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
