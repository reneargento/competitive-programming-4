package chapter2.section3.c.direct.addressing.table.dat.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/05/21.
 */
public class BookingARoom {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numberOfRooms = FastReader.nextInt();
        int numberOfBookedRooms = FastReader.nextInt();
        boolean[] bookedRooms = new boolean[numberOfRooms + 1];

        for (int i = 0; i < numberOfBookedRooms; i++) {
            int roomNumber = FastReader.nextInt();
            bookedRooms[roomNumber] = true;
        }

        if (numberOfRooms == numberOfBookedRooms) {
            outputWriter.printLine("too late");
        } else {
            int availableRoom = getAvailableRoom(bookedRooms);
            outputWriter.printLine(availableRoom);
        }
        outputWriter.flush();
    }

    private static int getAvailableRoom(boolean[] bookedRooms) {
        for (int i = 1; i < bookedRooms.length; i++) {
            if (!bookedRooms[i]) {
                return i;
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
