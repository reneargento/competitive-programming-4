package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/07/22.
 */
public class ExamRedistribution {

    private static class Room implements Comparable<Room> {
        int index;
        int studentsNumber;

        public Room(int index, int studentsNumber) {
            this.index = index;
            this.studentsNumber = studentsNumber;
        }

        @Override
        public int compareTo(Room other) {
            return Integer.compare(other.studentsNumber, studentsNumber);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Room[] rooms = new Room[FastReader.nextInt()];

        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = new Room(i + 1, FastReader.nextInt());
        }
        int[] orderToVisit = computeOrderToVisit(rooms);
        if (orderToVisit == null) {
            outputWriter.printLine("impossible");
        } else {
            outputWriter.print(orderToVisit[0]);
            for (int i = 1; i < orderToVisit.length; i++) {
                outputWriter.print(" " + orderToVisit[i]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static int[] computeOrderToVisit(Room[] rooms) {
        Arrays.sort(rooms);
        int studentsOnLargestRoom = rooms[0].studentsNumber;
        int studentsOnOtherRooms = 0;
        for (int i = 1; i < rooms.length; i++) {
            studentsOnOtherRooms += rooms[i].studentsNumber;
        }
        if (studentsOnOtherRooms < studentsOnLargestRoom) {
            return null;
        }

        int[] orderToVisit = new int[rooms.length];
        for (int i = 0; i < orderToVisit.length; i++) {
            orderToVisit[i] = rooms[i].index;
        }
        return orderToVisit;
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
