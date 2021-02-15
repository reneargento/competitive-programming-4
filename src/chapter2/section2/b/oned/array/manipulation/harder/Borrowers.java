package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.*;

/**
 * Created by Rene Argento on 26/01/21.
 */
public class Borrowers {

    private static class Book implements Comparable<Book> {
        String title;
        String author;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        @Override
        public int compareTo(Book otherBook) {
            if (!author.equals(otherBook.author)) {
                return author.compareTo(otherBook.author);
            }
            return title.compareTo(otherBook.title);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String bookData = scanner.nextLine();

        Map<String, Integer> titleToPosition = new HashMap<>();
        Map<Integer, String> positionToTitle = new HashMap<>();
        Map<String, Book> titleToBook = new HashMap<>();
        List<Book> books = new ArrayList<>();

        while (!bookData.equals("END")) {
            int endIndex = bookData.indexOf("\"", 1);
            String title = bookData.substring(0, endIndex + 1);
            String author = bookData.substring(endIndex + 5);
            Book book = new Book(title, author);
            books.add(book);
            titleToBook.put(title, book);

            bookData = scanner.nextLine();
        }

        Collections.sort(books);
        for (int i = 0; i < books.size(); i++) {
            titleToPosition.put(books.get(i).title, i);
            positionToTitle.put(i, books.get(i).title);
        }

        Set<Book> booksToReturn = new HashSet<>();
        Set<String> booksBorrowed = new HashSet<>();

        String command = scanner.next();
        while (!command.equals("END")) {
            if (command.equals("SHELVE")) {
                List<Book> sortedBooksToReturn = new ArrayList<>(booksToReturn);
                Collections.sort(sortedBooksToReturn);

                for (Book book : sortedBooksToReturn) {
                    String position = getPositionToPutBook(book, titleToPosition, positionToTitle, booksBorrowed);
                    System.out.println(position);
                }
                System.out.println("END");
                booksToReturn = new HashSet<>();
            } else {
                String title = scanner.nextLine().substring(1);

                if (command.equals("BORROW")) {
                    booksBorrowed.add(title);
                } else if (command.equals("RETURN")) {
                    booksToReturn.add(titleToBook.get(title));
                    booksBorrowed.remove(title);
                }
            }
            command = scanner.next();
        }
    }

    private static String getPositionToPutBook(Book book, Map<String, Integer> titleToPosition,
                                               Map<Integer, String> positionToTitle, Set<String> booksBorrowed) {
        String title = book.title;
        int position = titleToPosition.get(title);

        for (int p = position - 1; p >= 0; p--) {
            String bookBeforeTitle = positionToTitle.get(p);
            if (!booksBorrowed.contains(bookBeforeTitle)) {
                return "Put " + title + " after " + bookBeforeTitle;
            }
        }
        return "Put " + title + " first";
    }
}
