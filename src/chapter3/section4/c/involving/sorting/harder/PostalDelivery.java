package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/07/22.
 */
public class PostalDelivery {

    private static class Delivery implements Comparable<Delivery> {
        int location;
        int letters;

        public Delivery(int location, int letters) {
            this.location = location;
            this.letters = letters;
        }

        @Override
        public int compareTo(Delivery other) {
            return Integer.compare(Math.abs(other.location), Math.abs(location));
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] addresses = new int[FastReader.nextInt()];
        int capacity = FastReader.nextInt();

        List<Delivery> deliveriesToLeftSide = new ArrayList<>();
        List<Delivery> deliveriesToRightSide = new ArrayList<>();

        for (int i = 0; i < addresses.length; i++) {
            Delivery delivery = new Delivery(FastReader.nextInt(), FastReader.nextInt());
            if (delivery.location < 0) {
                deliveriesToLeftSide.add(delivery);
            } else {
                deliveriesToRightSide.add(delivery);
            }
        }
        long minimumTravelDistance = computeMinimumTravelDistance(deliveriesToLeftSide, deliveriesToRightSide, capacity);
        outputWriter.printLine(minimumTravelDistance);
        outputWriter.flush();
    }

    private static long computeMinimumTravelDistance(List<Delivery> deliveriesToLeftSide,
                                                    List<Delivery> deliveriesToRightSide, int capacity) {
        return computeDistance(deliveriesToLeftSide, capacity) +
                computeDistance(deliveriesToRightSide, capacity);
    }

    private static long computeDistance(List<Delivery> deliveries, int capacity) {
        long distance = 0;
        Collections.sort(deliveries);
        int currentLetters = capacity;
        int currentLocation = 0;

        for (Delivery delivery : deliveries) {
            distance += Math.abs(delivery.location - currentLocation);

            while (delivery.letters > currentLetters) {
                delivery.letters -= currentLetters;
                distance += Math.abs(delivery.location * 2);
                currentLetters = capacity;
            }
            currentLetters -= delivery.letters;
            currentLocation = delivery.location;
        }
        distance += Math.abs(currentLocation);
        return distance;
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
