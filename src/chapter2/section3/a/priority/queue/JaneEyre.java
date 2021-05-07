package chapter2.section3.a.priority.queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/05/21.
 */
public class JaneEyre {

    private static class Book implements Comparable<Book> {
        String name;
        int pages;
        int timeReceived;
        boolean isJaneEyre;

        public Book(String name, int pages, int timeReceived, boolean isJaneEyre) {
            this.name = name;
            this.pages = pages;
            this.timeReceived = timeReceived;
            this.isJaneEyre = isJaneEyre;
        }

        @Override
        public int compareTo(Book other) {
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int unreadBooks = FastReader.nextInt();
        int friendBooks = FastReader.nextInt();
        int janeEyrePages = FastReader.nextInt();
        PriorityQueue<Book> books = new PriorityQueue<>();

        books.offer(new Book("Jane Eyre", janeEyrePages, 0, true));
        for (int i = 0; i < unreadBooks; i++) {
            String line = FastReader.getLine();
            String bookName = extractBookName(line);
            String[] values = line.split(" ");
            int pages = Integer.parseInt(values[values.length - 1]);

            Book book = new Book(bookName, pages, 0, false);
            books.offer(book);
        }

        Book[] receivedBooks = new Book[friendBooks];
        for (int i = 0; i < receivedBooks.length; i++) {
            String line = FastReader.getLine();
            String bookName = extractBookName(line);
            String[] values = line.split(" ");
            int timeReceived = Integer.parseInt(values[0]);
            int pages = Integer.parseInt(values[values.length - 1]);

            receivedBooks[i] = new Book(bookName, pages, timeReceived, false);
        }
        Arrays.sort(receivedBooks, new Comparator<Book>() {
            @Override
            public int compare(Book book1, Book book2) {
                return Integer.compare(book1.timeReceived, book2.timeReceived);
            }
        });

        long timeToFinishJaneEyre = computeTimeToFinishJaneEyre(books, receivedBooks);
        System.out.println(timeToFinishJaneEyre);
    }

    private static long computeTimeToFinishJaneEyre(PriorityQueue<Book> books, Book[] receivedBooks) {
        long time = 0;
        int receivedBooksIndex = 0;

        while (true) {
            while (receivedBooksIndex < receivedBooks.length
                    && receivedBooks[receivedBooksIndex].timeReceived <= time) {
                books.offer(receivedBooks[receivedBooksIndex]);
                receivedBooksIndex++;
            }

            Book book = books.poll();
            time += book.pages;
            if (book.isJaneEyre) {
                break;
            }
        }
        return time;
    }

    private static String extractBookName(String line) {
        int nameStartIndex = line.indexOf("\"") + 1;
        int nameEndIndex = line.indexOf("\"", nameStartIndex);
        return line.substring(nameStartIndex, nameEndIndex);
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
}
