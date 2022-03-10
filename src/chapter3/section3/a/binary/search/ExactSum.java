package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/03/22.
 */
public class ExactSum {

    private static class Books {
        int book1Price;
        int book2Price;

        public Books(int book1Price, int book2Price) {
            int minPrice = Math.min(book1Price, book2Price);
            int maxPrice = Math.max(book1Price, book2Price);
            this.book1Price = minPrice;
            this.book2Price = maxPrice;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int booksNumber = Integer.parseInt(line);
            int[] books = new int[booksNumber];
            for (int i = 0; i < books.length; i++) {
                books[i] = FastReader.nextInt();
            }
            int money = FastReader.nextInt();
            Books booksSelected = selectBooks(books, money);
            outputWriter.printLine(String.format("Peter should buy books whose prices are %d and %d.",
                    booksSelected.book1Price, booksSelected.book2Price));
            outputWriter.printLine();

            FastReader.getLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Books selectBooks(int[] books, int money) {
        int book1Price = 0;
        int book2Price = 0;
        int booksDistance = Integer.MAX_VALUE;

        Arrays.sort(books);
        for (int i = 0; i < books.length; i++) {
            int bookPrice = books[i];
            int targetPrice = money - bookPrice;
            int otherBookIndex = binarySearch(books, targetPrice);

            if (otherBookIndex != -1) {
                int currentDistance = Math.abs(i - otherBookIndex);
                if (currentDistance < booksDistance) {
                    booksDistance = currentDistance;
                    book1Price = bookPrice;
                    book2Price = books[otherBookIndex];
                }
            }
        }
        return new Books(book1Price, book2Price);
    }

    private static int binarySearch(int[] books, int target) {
        int low = 0;
        int high = books.length - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (books[middle] < target) {
                low = middle + 1;
            } else if (books[middle] > target) {
                high = middle - 1;
            } else {
                return middle;
            }
        }
        return -1;
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
