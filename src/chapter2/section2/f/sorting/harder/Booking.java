package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Rene Argento on 26/02/21.
 */
public class Booking {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static class BookingData implements Comparable<BookingData> {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;

        public BookingData(LocalDateTime startDateTime, LocalDateTime endDateTime) {
            this.startDateTime = startDateTime;
            this.endDateTime = endDateTime;
        }

        @Override
        public int compareTo(BookingData other) {
            if (startDateTime.compareTo(other.startDateTime) != 0) {
                return startDateTime.compareTo(other.startDateTime);
            }
            return endDateTime.compareTo(other.endDateTime);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int bookingsNumber = FastReader.nextInt();
            int cleaningTime = FastReader.nextInt();

            BookingData[] bookings = new BookingData[bookingsNumber];
            for (int i = 0; i < bookings.length; i++) {
                String[] bookingLine = FastReader.getLine().split(" ");

                String startDateTimeString = bookingLine[1] + " " + bookingLine[2];
                LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString, dateTimeFormatter);
                String endDateTimeString = bookingLine[3] + " " + bookingLine[4];
                LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, dateTimeFormatter);
                endDateTime = endDateTime.plusMinutes(cleaningTime);

                bookings[i] = new BookingData(startDateTime, endDateTime);
            }
            Arrays.sort(bookings);

            int requiredRooms = computeRequiredRooms(bookings);
            outputWriter.printLine(requiredRooms);
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static int computeRequiredRooms(BookingData[] bookings) {
        int requiredRooms = 0;
        PriorityQueue<BookingData> priorityQueue = new PriorityQueue<>(new Comparator<BookingData>() {
            @Override
            public int compare(BookingData booking1, BookingData booking2) {
                return booking1.endDateTime.compareTo(booking2.endDateTime);
            }
        });

        for (BookingData booking : bookings) {
            while (!priorityQueue.isEmpty()
                    && priorityQueue.peek().endDateTime.compareTo(booking.startDateTime) <= 0) {
                priorityQueue.poll();
            }
            priorityQueue.offer(booking);

            requiredRooms = Math.max(requiredRooms, priorityQueue.size());
        }
        return requiredRooms;
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
