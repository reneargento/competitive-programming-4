package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/11/21.
 */
public class TheWedding {

    private static class Package {
        int travelAgency;
        int restaurant;
        int hotel;
        long totalPrice;

        public Package(int travelAgency, int restaurant, int hotel, long totalPrice) {
            this.travelAgency = travelAgency;
            this.restaurant = restaurant;
            this.hotel = hotel;
            this.totalPrice = totalPrice;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();

        while (line != null && !line.isEmpty()) {
            String[] data = line.split(" ");
            int travelAgencies = Integer.parseInt(data[0]);
            int restaurants = Integer.parseInt(data[1]);
            int hotels = Integer.parseInt(data[2]);

            int[] travelPrices = new int[travelAgencies];
            int[] restaurantPrices = new int[restaurants];
            int[] hotelPrices = new int[hotels];

            boolean[][] travelRestaurantMatches = new boolean[travelAgencies][restaurants];
            boolean[][] restaurantHotelMatches = new boolean[restaurants][hotels];
            boolean[][] hotelTravelMatches = new boolean[hotels][travelAgencies];

            readInput(travelAgencies, restaurants, travelPrices, travelRestaurantMatches);
            readInput(restaurants, hotels, restaurantPrices, restaurantHotelMatches);
            readInput(hotels, travelAgencies, hotelPrices, hotelTravelMatches);

            Package bestPackage = computeBestPackage(travelPrices, restaurantPrices, hotelPrices,
                    travelRestaurantMatches, restaurantHotelMatches, hotelTravelMatches);
            if (bestPackage == null) {
                outputWriter.printLine("Don't get married!");
            } else {
                outputWriter.printLine(String.format("%d %d %d:%d", bestPackage.travelAgency,
                        bestPackage.restaurant, bestPackage.hotel, bestPackage.totalPrice));
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Package computeBestPackage(int[] travelPrices, int[] restaurantPrices, int[] hotelPrices,
                                              boolean[][] travelRestaurantMatches,
                                              boolean[][] restaurantHotelMatches,
                                              boolean[][] hotelTravelMatches) {
        long bestPrice = Long.MAX_VALUE;
        int bestTravelAgency = -1;
        int bestRestaurant = -1;
        int bestHotel = -1;

        for (int i = 0; i < travelPrices.length; i++) {
            for (int j = 0; j < restaurantPrices.length; j++) {
                for (int k = 0; k < hotelPrices.length; k++) {
                    if (travelRestaurantMatches[i][j] && restaurantHotelMatches[j][k]
                            && hotelTravelMatches[k][i]) {
                        long price = travelPrices[i] + restaurantPrices[j] + hotelPrices[k];
                        if (price < bestPrice) {
                            bestPrice = price;
                            bestTravelAgency = i;
                            bestRestaurant = j;
                            bestHotel = k;
                        }
                    }
                }
            }
        }
        if (bestTravelAgency == -1) {
            return null;
        }
        return new Package(bestTravelAgency, bestRestaurant, bestHotel, bestPrice);
    }

    private static void readInput(int domain1, int domain2, int[] prices, boolean[][] matches) throws IOException {
        for (int i = 0; i < domain1; i++) {
            for (int j = 0; j < domain2 + 1; j++) {
                if (j == 0) {
                    prices[i] = FastReader.nextInt();
                } else {
                    int combination = FastReader.nextInt();
                    if (combination == 0) {
                        matches[i][j - 1] = true;
                    }
                }
            }
        }
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
