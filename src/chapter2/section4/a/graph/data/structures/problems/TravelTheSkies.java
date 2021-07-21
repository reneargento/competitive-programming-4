package chapter2.section4.a.graph.data.structures.problems;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/07/21.
 */
public class TravelTheSkies {

    private static class Flight implements Comparable<Flight> {
        int startLocation;
        int endDestination;
        int day;
        int capacity;

        public Flight(int startLocation, int endDestination, int day, int capacity) {
            this.startLocation = startLocation;
            this.endDestination = endDestination;
            this.day = day;
            this.capacity = capacity;
        }

        @Override
        public int compareTo(Flight other) {
            return Integer.compare(day, other.day);
        }
    }

    private static class Arrival implements Comparable<Arrival> {
        int airport;
        int day;
        int customers;

        public Arrival(int airport, int day, int customers) {
            this.airport = airport;
            this.day = day;
            this.customers = customers;
        }

        @Override
        public int compareTo(Arrival other) {
            return Integer.compare(day, other.day);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int airports = FastReader.nextInt();
        int days = FastReader.nextInt();
        int flightsNumber = FastReader.nextInt();
        Flight[] flights = new Flight[flightsNumber];

        for (int i = 0; i < flightsNumber; i++) {
            flights[i] = new Flight(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt(),
                    FastReader.nextInt());
        }

        int arrivalsNumber = airports * days;
        Arrival[] arrivals = new Arrival[arrivalsNumber];
        for (int i = 0; i < arrivals.length; i++) {
            arrivals[i] = new Arrival(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
        }

        Arrays.sort(flights);
        Arrays.sort(arrivals);

        boolean optimal = canBeOptimallyArranged(flights, arrivals, airports, days);
        outputWriter.printLine(optimal ? "optimal" : "suboptimal");
        outputWriter.flush();
    }

    private static boolean canBeOptimallyArranged(Flight[] flights, Arrival[] arrivals, int airports, int days) {
        int[] customersInAirports = new int[airports + 1];
        int[] customersArrivingFromFlights = new int[airports + 1];
        int arrivalIndex = 0;
        int flightsIndex = 0;

        for (int day = 1; day <= days; day++) {
            while (arrivalIndex < arrivals.length
                    && arrivals[arrivalIndex].day == day) {
                Arrival arrival = arrivals[arrivalIndex];
                customersInAirports[arrival.airport] += arrival.customers;
                arrivalIndex++;
            }

            while (flightsIndex < flights.length
                    && flights[flightsIndex].day == day) {
                Flight flight = flights[flightsIndex];
                if (flight.capacity > customersInAirports[flight.startLocation]) {
                    return false;
                }
                customersInAirports[flight.startLocation] -= flight.capacity;
                customersArrivingFromFlights[flight.endDestination] += flight.capacity;
                flightsIndex++;
            }

            for (int i = 1; i < customersArrivingFromFlights.length; i++) {
                customersInAirports[i] += customersArrivingFromFlights[i];
            }
        }
        return true;
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
